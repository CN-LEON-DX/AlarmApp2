package com.example.alarmapp.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.alarmapp.R;
import com.example.alarmapp.alarm_pack.MainActivity_Alarm_Begin_Activity;
import com.example.alarmapp.clock.MainActivity_clock;

public class MainActivity_stopwatch extends AppCompatActivity {
    private TextView tv_clock,tv_alarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_stopwatch);

        //đi đến activity đồng hồ
        listenerClock();
        //đi đến activity báo thức
        listeneralarm();
    }
    private void listenerClock(){
        tv_clock = (TextView) findViewById(R.id.tv_clock_stopwatch);
        tv_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_stopwatch.this, MainActivity_clock.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void listeneralarm(){
        tv_alarm = (TextView) findViewById(R.id.tv_alarm_stopwatch);
        tv_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_stopwatch.this,MainActivity_Alarm_Begin_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}