package com.example.alarmapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alarmapp.Model.adapter.item.Alarm;
import com.example.alarmapp.Model.adapter.Adapter.Alarm_Recycler_Adapter;
import com.example.alarmapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AlarmFragment extends Fragment {
    private FloatingActionButton fabAdd_Alarm;
    private RecyclerView recyclerView_Alarm;
    private List<Alarm> alarmList;
    private Alarm_Recycler_Adapter adapter_Alarm;

    public AlarmFragment() {
        // Required empty public constructor
    }

    public static AlarmFragment newInstance(String param1, String param2) {
        AlarmFragment fragment = new AlarmFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        // Tìm các phần tử trong View
        recyclerView_Alarm = view.findViewById(R.id.rcvList_Alarm);
        fabAdd_Alarm = view.findViewById(R.id.fabAddAlarm);




        // Tạo và cấu hình RecyclerView
        adapter_Alarm = new Alarm_Recycler_Adapter(getContext(), createAlarmList());
        recyclerView_Alarm.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView_Alarm.setAdapter(adapter_Alarm);
        adapter_Alarm.notifyDataSetChanged();
        return view;
    }

    // Tạo một danh sách thông tin các báo thức
    private List<Alarm> createAlarmList() {
        alarmList = new ArrayList<>();
        alarmList.add(new Alarm("07:05", 1,"Chào sáng vui vẻ !"));
        alarmList.add(new Alarm("12:03", 2,"Buổi trưa !"));
        alarmList.add(new Alarm("08:11", 3,"Làm bài tập !"));
        alarmList.add(new Alarm("06:08", 4,"Nghe nhạc !"));
        alarmList.add(new Alarm("15:54", 5,"Đọc truyện !"));
        alarmList.add(new Alarm("14:49", 6,"Chạy bộ !"));
        alarmList.add(new Alarm("12:28", 8,"Nấu ăn trưa !"));
        alarmList.add(new Alarm("08:55", 9,"Đi học ở trường !"));
        alarmList.add(new Alarm("20:55", 10,"Ăn tối !"));
        alarmList.add(new Alarm("09:18", 11,"Làm vườn !"));
        return alarmList;
    }
}
