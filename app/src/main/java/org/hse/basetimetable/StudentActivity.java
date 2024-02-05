package org.hse.basetimetable;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StudentActivity extends AppCompatActivity {

    protected TextView time, status, subject, cabinet, corp, teacher;
    protected Button btnDay;
    protected Button btnWeek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity);

        final Spinner spinner = findViewById(R.id.groupList);
        time = findViewById(R.id.time);
        status = findViewById(R.id.status);
        subject = findViewById(R.id.subject);
        cabinet = findViewById(R.id.cabinet);
        corp = findViewById(R.id.corp);
        teacher = findViewById(R.id.teacher);
        btnDay = findViewById(R.id.button_day);
        btnWeek = findViewById(R.id.button_week);
        List<Group> groups = new ArrayList<>();

        initGroupList(groups, "ПИ", 21, 3);
        initGroupList(groups, "БИ", 20, 2);
        initGroupList(groups, "Ю", 21, 2);

        ArrayAdapter<?> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                Object item = adapter.getItem(selectedItemPosition);
                ((TextView) parent.getChildAt(0)).setTextColor(getColor(R.color.black));
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                Log.d("TAG", "selectedItem" + item);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        initTime(time);
        initData();

    }
    private void initGroupList(List<Group> groups, String program, int admissionYear, int numberOfGroups) {
        for (int i = 1; i <= numberOfGroups; i++) {
            String groupName = program + "-" + admissionYear + "-" + i;
            groups.add(new Group(i, groupName));
        }
    }

    public static void initTime(TextView time) {
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String formattedTime = timeFormat.format(currentTime);

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", new Locale("ru"));
        String formattedDay = dayFormat.format(currentTime);

        String result = formattedTime + ", " + formattedDay;

        time.setText(result);
    }

    private void initData(){
        status.setText("Нет пар");
        subject.setText("Дисциплина");
        cabinet.setText("Кабинет");
        corp.setText("Корпус");
        teacher.setText("Преподаватель");
    }
}
