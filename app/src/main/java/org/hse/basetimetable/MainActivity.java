package org.hse.basetimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        View button1 = findViewById(R.id.button1);
        View button2 = findViewById(R.id.button2);

        button1.setOnClickListener(v -> clickButton1());

        button2.setOnClickListener(v -> clickButton2());
    }

    private void clickButton1(){
        Toast.makeText(this, "Расписание для студентов", Toast.LENGTH_SHORT).show();
    }

    private void clickButton2(){
        Toast.makeText(this, "Расписание для преподавателя", Toast.LENGTH_SHORT).show();
    }
}