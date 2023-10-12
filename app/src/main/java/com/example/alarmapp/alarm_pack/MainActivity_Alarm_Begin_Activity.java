package com.example.alarmapp.alarm_pack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.alarmapp.R;
import com.example.alarmapp.clock.MainActivity_clock;
import com.example.alarmapp.clock.SelectCountry_Activity;
import com.example.alarmapp.stopwatch.MainActivity_stopwatch;

import java.util.ArrayList;
import java.util.List;

// YÊU CẦU: SAU KHI ĐẶT GIỜ THÀNH CÔNG THÌ CHỮ CỦA ITEM => WHITE
public class MainActivity_Alarm_Begin_Activity extends AppCompatActivity {
    private RecyclerView recyclerView_Alarm;
    private List<Alarm>  alarmList;
    private AlarmAdapterRecycler adapter_Alarm;
    private TextView tv_stopwatch,tv_clock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_begin);

        //tạo danh sách recyclervew
        recyclerView_Alarm = findViewById(R.id.rcvAlarm_Data);
        adapter_Alarm = new AlarmAdapterRecycler(createAlarmList(), this);
        recyclerView_Alarm.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView_Alarm.setAdapter(adapter_Alarm);

        //set listener cho bấm giờ
        listenerStopWatch();
        //set listener cho đồng hồ
        listenerClock();
    }
    // Tạo một danh sách thông tin các báo thức
    private List<Alarm> createAlarmList(){
        alarmList = new ArrayList<>();
        alarmList.add(new Alarm("07:05", "Chào sáng vui vẻ !"));
        alarmList.add(new Alarm("12:03", "Buổi trưa !"));
        alarmList.add(new Alarm("08:11", "Làm bài tập !"));
        alarmList.add(new Alarm("06:08", "Nghe nhạc !"));
        alarmList.add(new Alarm("15:08", "Đọc truyện !"));
        alarmList.add(new Alarm("16:18", "Chạy bộ !"));
        alarmList.add(new Alarm("12:28", "Nấu ăn trưa !"));
        alarmList.add(new Alarm("07:08", "Đi học ở trường !"));
        alarmList.add(new Alarm("20:48", "Ăn tối !"));
        alarmList.add(new Alarm("09:18", "Làm vườn !"));
        return alarmList;
    }
    private void listenerStopWatch(){
        tv_stopwatch = (TextView) findViewById(R.id.tv_stopwatch_alarm);
        tv_stopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Alarm_Begin_Activity.this, MainActivity_stopwatch.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void listenerClock(){
        tv_clock = (TextView) findViewById(R.id.tv_clock_alarm);
        tv_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_Alarm_Begin_Activity.this, MainActivity_clock.class);
                startActivity(intent);
                finish();
            }
        });
    }

}