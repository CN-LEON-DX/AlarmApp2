package com.example.alarmapp.alarm_pack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.alarmapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

// YÊU CẦU: SAU KHI ĐẶT GIỜ THÀNH CÔNG THÌ CHỮ CỦA ITEM => WHITE
public class MainActivity_Alarm_Begin_Activity extends AppCompatActivity {
    private TextView tvEditting, tvMenuAlarm, tvMenuCalendar, tvMenuClock;
    private FloatingActionButton fabAdd_Alarm;
    private RecyclerView recyclerView_Alarm;
    private List<Alarm> alarmList;
    private AlarmAdapterRecycler adapter_Alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_begin);

        recyclerView_Alarm = findViewById(R.id.rcvAlarm_Data);
        adapter_Alarm = new AlarmAdapterRecycler(createAlarmList(), this);
        recyclerView_Alarm.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView_Alarm.setAdapter(adapter_Alarm);
        // Find id
        tvEditting = findViewById(R.id.tvEditting);
        tvMenuAlarm = findViewById(R.id.tvMenuAlarm);
        tvMenuCalendar = findViewById(R.id.tvMenuCalendar);
        tvMenuClock = findViewById(R.id.tvMenuClock);
        fabAdd_Alarm = findViewById(R.id.fabAddAlarm);

    }

    // Tạo một danh sách thông tin các báo thức
    private List<Alarm> createAlarmList() {
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

    // Xu ly su kien !
}