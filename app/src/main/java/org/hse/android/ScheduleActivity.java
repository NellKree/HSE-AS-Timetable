package org.hse.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import org.hse.basetimetable.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScheduleActivity extends AppCompatActivity {

    public static String ARG_TYPE = "Type";
    public static String ARG_MODE = "Mode";
    public static String ARG_ID = "Id";
    public static String ARG_NAME = "Name";
    public static String ARG_DATE = "Time";
    public static int DEFAULT_ID = 0;
    private ScheduleType type;
    private String name;
    private Date time;
    private ItemAdapter adapter;
    private TextView scheduleTitle, serverTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        type = (ScheduleType) getIntent().getSerializableExtra(ARG_TYPE);
        ScheduleMode mode = (ScheduleMode) getIntent().getSerializableExtra(ARG_MODE);
        int id = getIntent().getIntExtra(ARG_ID, DEFAULT_ID);
        name = getIntent().getStringExtra(ARG_NAME);
        time = (Date) getIntent().getSerializableExtra(ScheduleActivity.ARG_DATE);

        RecyclerView recyclerView = findViewById(R.id.timetable_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new ItemAdapter();
        recyclerView.setAdapter(adapter);
        initData();

        scheduleTitle = findViewById(R.id.schTitle);
        setTitle();
        serverTime = findViewById(R.id.schTitle2);
        setServerTime();
        initData();

        List list = new ArrayList<>();
        list.add(new Object());
    }

    private void setTitle() {
        scheduleTitle.setText(name);
    }

    private void setServerTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, d MMMM",  Locale.getDefault());
        String[] formattedDay  = simpleDateFormat.format(time).split(" ");
        String timeText = formattedDay[0].substring(0,1).toUpperCase() + formattedDay[0].substring(1) + " " + formattedDay[1] + " " +  formattedDay[2];
        serverTime.setText(timeText);
    }

    private void initData() {
        if (type == ScheduleType.DAY) {
            setDaySchedule();
        } else if (type == ScheduleType.WEEK){
            setWeekSchedule();
        }
    }

    private void setDaySchedule() {
        List<ScheduleItem> list = new ArrayList<>();
        ScheduleItemHeader header = new ScheduleItemHeader();
        header.setTitle("Понедельник, 25 марта");
        list.add(header);
        ScheduleItem item = new ScheduleItem();
        item.setStart("13:10");
        item.setEnd("14:30");
        item.setType("ПРАКТИЧЕСКОЕ ЗАНЯТИЕ");
        item.setName("Анализ данных (анг)");
        item.setPlace("Ауд. 503, Кочновский пр-д, д.3");
        item.setTeacher("Преп. Гущим Михаил Иванович");
        list.add(item);
        adapter.setDataList(list);
    }

    private void setWeekSchedule() {
        List<ScheduleItem> list = new ArrayList<>();

        ScheduleItemHeader header = new ScheduleItemHeader();
        header.setTitle("Понедельник, 25 марта");
        list.add(header);

        ScheduleItem item = new ScheduleItem();
        item.setStart("16:40");
        item.setEnd("18:00");
        item.setType("НАУЧНО-ИССЛЕДОВАТЕЛЬСКИЙ СЕМИНАР");
        item.setName("Научно-исследовательский семинар \"Разработка облачных и мобильныхприложений на платформе Apple IOS\" (анг)");
        item.setPlace("Ауд. 432, Кочновский пр-д, д.3");
        item.setTeacher("Проф. Александров Дмитрий Владимирович");
        list.add(item);

        header = new ScheduleItemHeader();
        header.setTitle("Вторник, 26 марта");
        list.add(header);

        item = new ScheduleItem();
        item.setStart("16:40");
        item.setEnd("18:00");
        item.setType("НАУЧНО-ИССЛЕДОВАТЕЛЬСКИЙ СЕМИНАР");
        item.setName("Научно-исследовательский семинар \"Разработка облачных и мобильныхприложений на платформе Apple IOS\" (анг)");
        item.setPlace("Ауд. 432, Кочновский пр-д, д.3");
        item.setTeacher("Проф. Александров Дмитрий Владимирович");
        list.add(item);

        header = new ScheduleItemHeader();
        header.setTitle("Среда, 27 марта");
        list.add(header);

        item = new ScheduleItem();
        item.setStart("16:40");
        item.setEnd("18:00");
        item.setType("НАУЧНО-ИССЛЕДОВАТЕЛЬСКИЙ СЕМИНАР");
        item.setName("Научно-исследовательский семинар \"Разработка облачных и мобильныхприложений на платформе Apple IOS\" (анг)");
        item.setPlace("Ауд. 432, Кочновский пр-д, д.3");
        item.setTeacher("Проф. Александров Дмитрий Владимирович");
        list.add(item);

        header = new ScheduleItemHeader();
        header.setTitle("Четверг, 28 марта");
        list.add(header);

        item = new ScheduleItem();
        item.setStart("16:40");
        item.setEnd("18:00");
        item.setType("НАУЧНО-ИССЛЕДОВАТЕЛЬСКИЙ СЕМИНАР");
        item.setName("Научно-исследовательский семинар \"Разработка облачных и мобильныхприложений на платформе Apple IOS\" (анг)");
        item.setPlace("Ауд. 432, Кочновский пр-д, д.3");
        item.setTeacher("Проф. Александров Дмитрий Владимирович");
        list.add(item);


        header = new ScheduleItemHeader();
        header.setTitle("Пятница, 29 марта");
        list.add(header);

        item = new ScheduleItem();
        item.setStart("16:40");
        item.setEnd("18:00");
        item.setType("НАУЧНО-ИССЛЕДОВАТЕЛЬСКИЙ СЕМИНАР");
        item.setName("Научно-исследовательский семинар \"Разработка облачных и мобильныхприложений на платформе Apple IOS\" (анг)");
        item.setPlace("Ауд. 432, Кочновский пр-д, д.3");
        item.setTeacher("Проф. Александров Дмитрий Владимирович");
        list.add(item);

        header = new ScheduleItemHeader();
        header.setTitle("Суббота, 30 марта");
        list.add(header);

        item = new ScheduleItem();
        item.setStart("16:40");
        item.setEnd("18:00");
        item.setType("НАУЧНО-ИССЛЕДОВАТЕЛЬСКИЙ СЕМИНАР");
        item.setName("Научно-исследовательский семинар \"Разработка облачных и мобильныхприложений на платформе Apple IOS\" (анг)");
        item.setPlace("Ауд. 432, Кочновский пр-д, д.3");
        item.setTeacher("Проф. Александров Дмитрий Владимирович");
        list.add(item);

        header = new ScheduleItemHeader();
        header.setTitle("Воскресенье, 31 марта");
        list.add(header);

        item = new ScheduleItem();
        item.setStart("16:40");
        item.setEnd("18:00");
        item.setType("НАУЧНО-ИССЛЕДОВАТЕЛЬСКИЙ СЕМИНАР");
        item.setName("Научно-исследовательский семинар \"Разработка облачных и мобильныхприложений на платформе Apple IOS\" (анг)");
        item.setPlace("Ауд. 432, Кочновский пр-д, д.3");
        item.setTeacher("Проф. Александров Дмитрий Владимирович");
        list.add(item);

        adapter.setDataList(list);
    }

}