package org.hse.android;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Spinner;
import android.widget.TextView;

import org.hse.basetimetable.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeacherActivity extends BaseActivity {

    private TextView timeLabel, timeValue, status, subject, cabinet, corp, teacher;
    private Date currentTime;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity);

        timeLabel = findViewById(R.id.timeNow);
        timeValue = findViewById(R.id.time);
        status = findViewById(R.id.status);
        subject = findViewById(R.id.subject);
        cabinet = findViewById(R.id.cabinet);
        corp = findViewById(R.id.corp);
        teacher = findViewById(R.id.teacher);

        spinner = findViewById(R.id.groupList);

        List<Group> groups = new ArrayList<>();
        initGroupList(groups);

        ArrayAdapter<?> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initTime(getString(R.string.teacherType));
        initData();
        View scheduleDay = findViewById(R.id.button_day);
        scheduleDay.setOnClickListener(v -> showSchedule(ScheduleType.DAY));
        View scheduleWeek = findViewById(R.id.button_week);
        scheduleWeek.setOnClickListener(v -> showSchedule(ScheduleType.WEEK));
    }

    private void showSchedule(ScheduleType type) {
        Object selectedItem = spinner.getSelectedItem();
        if (!(selectedItem instanceof Group)) {
            return;
        }
        showScheduleImpl(ScheduleMode.TEACHER, type, (Group) selectedItem);
    }

    private void initGroupList(List<Group> groups){
        groups.add(new Group(1,"Гуляева Ольга Николаевна"));
        groups.add(new Group(2,"Акаева Кавсарат Исламовна"));
        groups.add(new Group(3,"Андропова Татьяна Владимировна"));
        groups.add(new Group(4,"Болдырев Алексей Сергеевич"));
        groups.add(new Group(5,"Брюхина Евгения Рафиковна"));
    }

}
