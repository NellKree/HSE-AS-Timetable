package org.hse.android;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.hse.basetimetable.R;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends BaseActivity {

    private TextView timeLabel, timeValue, status, subject, cabinet, corp, teacher;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity);

        timeLabel = findViewById(R.id.timeNow);
        timeValue = findViewById(R.id.timeS);
        status = findViewById(R.id.status);
        subject = findViewById(R.id.subject);
        cabinet = findViewById(R.id.cabinet);
        corp = findViewById(R.id.corp);
        teacher = findViewById(R.id.teacher);
        spinner = findViewById(R.id.groupList);

        List<Group> groups = new ArrayList<>();
        initGroupList(groups, "ПИ", 21, 3);
        initGroupList(groups, "БИ", 20, 2);
        initGroupList(groups, "Ю", 21, 2);

        ArrayAdapter<?> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

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

        initTime(getString(R.string.studentType));
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
        showScheduleImpl(ScheduleMode.STUDENT, type, (Group) selectedItem);
    }

    private void initGroupList(List<Group> groups, String program, int admissionYear, int numberOfGroups) {
        for (int i = 1; i <= numberOfGroups; i++) {
            String groupName = program + "-" + admissionYear + "-" + i;
            groups.add(new Group(i, groupName));
        }
    }
}
