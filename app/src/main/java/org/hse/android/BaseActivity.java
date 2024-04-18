package org.hse.android;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.hse.basetimetable.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class BaseActivity extends AppCompatActivity {
    private final static String TAG = "BaseActivity";
    public static final String URL = "https://api.ipgeolocation.io/ipgeo?apiKey=b03018f75ed94023a005637878ec0977";
    protected TextView time;
    protected Date currentTime;
    private final OkHttpClient client = new OkHttpClient();
    protected void getTime() {
        Request request = new Request.Builder().url(URL).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                parseResponse(response);
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "getTime", e);
            }
        });
    }

    protected void initTime(String type){
        if (Objects.equals(type, getString(R.string.studentType))) {
            time = findViewById(R.id.timeS);
        } else if (Objects.equals(type, getString(R.string.teacherType))){
            time = findViewById(R.id.time);
        }
        getTime();
    }


    private void showTime(Date dateTime) {
        if (dateTime == null) {
            return;
        }
        currentTime = dateTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm, EEEE",
                Locale.getDefault());
        String[] dateSplit = simpleDateFormat.format(currentTime).split(" ");
        String timeText = dateSplit[0] + " " +
                dateSplit[1].substring(0,1).toUpperCase() +
                dateSplit[1].substring(1);
        time.setText(timeText);
    }

    private void parseResponse(Response response) {
        Gson gson = new Gson();
        ResponseBody body = response.body();
        try {
            if (body == null) {
                return;
            }
            String string = body.string();
            TimeResponse timeResponse = gson.fromJson(string, TimeResponse.class);
            String currentTimeVal = timeResponse.getTimeZone().getCurrentTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ", Locale.getDefault());
            Date dateTime = simpleDateFormat.parse(currentTimeVal);
            runOnUiThread(()->showTime(dateTime));
        } catch (Exception e) {
            Log.e(TAG,"",e);
        }
    }


    protected void showScheduleImpl(ScheduleMode mode, ScheduleType type, Group group) {
        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra(ScheduleActivity.ARG_ID, group.getId());
        intent.putExtra(ScheduleActivity.ARG_TYPE, type);
        intent.putExtra(ScheduleActivity.ARG_MODE, mode);
        intent.putExtra(ScheduleActivity.ARG_NAME, group.getName());
        intent.putExtra(ScheduleActivity.ARG_DATE, currentTime);
        startActivity(intent);
    }

    public void initData() {
        TextView timeLabel, status, subject, cabinet,corp,  teacher;
        timeLabel = findViewById(R.id.timeNow);
        status = findViewById(R.id.status);
        subject = findViewById(R.id.subject);
        cabinet = findViewById(R.id.cabinet);
        corp = findViewById(R.id.corp);
        teacher = findViewById(R.id.teacher);
        timeLabel.setText(R.string.now);
        status.setText(R.string.status);
        subject.setText(R.string.subject);
        cabinet.setText(R.string.cabinet);
        corp.setText(R.string.corp);
        teacher.setText(R.string.teacher);
    }
}
