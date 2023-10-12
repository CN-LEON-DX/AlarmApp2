package com.example.alarmapp.clock;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.alarmapp.R;
import com.example.alarmapp.alarm_pack.MainActivity_Alarm_Begin_Activity;
import com.example.alarmapp.stopwatch.MainActivity_stopwatch;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity_clock extends AppCompatActivity {
    private TextView tv_stopwatch,tv_alarm;
    private FloatingActionButton addclock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_clock);
        //đi đến báo thức
        listenerAlarm();
        //đi đến bấm giờ
        listenerStopWatch();
        //đi chọn thêm đồng hồ
        selectcountry();


    }
    private void listenerStopWatch(){
        tv_stopwatch = (TextView) findViewById(R.id.tv_stopwatch_clock);
        tv_stopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_clock.this, MainActivity_stopwatch.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void listenerAlarm(){
        tv_alarm = (TextView) findViewById(R.id.tv_alarm_clock);
        tv_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_clock.this,MainActivity_Alarm_Begin_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private  void selectcountry(){
        addclock=(FloatingActionButton) findViewById(R.id.fab_addclock);
        addclock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity_clock.this, SelectCountry_Activity.class);
                startActivity(intent);

            }
        });
    }
}