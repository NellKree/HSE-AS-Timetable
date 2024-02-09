package org.hse.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.hse.basetimetable.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View buttonStudent = findViewById(R.id.buttonStudent);
        View buttonTeacher = findViewById(R.id.buttonTeacher);

        buttonStudent.setOnClickListener(v -> clickButtonStudent());

        buttonTeacher.setOnClickListener(v -> clickButtonTeacher());
    }

    private void clickButtonStudent(){
        //Toast.makeText(this, "Расписание для студентов", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }

    private void clickButtonTeacher(){
        //Toast.makeText(this, "Расписание для преподавателя", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, TeacherActivity.class);
        startActivity(intent);
    }
}