package com.example.alarmapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.alarmapp.Activity.CreateNewAlarmActivity;
import com.example.alarmapp.Adapter.Alarm_Recycler_Adapter;
import com.example.alarmapp.Base.Alarm;
import com.example.alarmapp.Base.Clock;
import com.example.alarmapp.Base.SwipToDeleteAlarm;
import com.example.alarmapp.Base.SwipeToDeleteClock;
import com.example.alarmapp.Database.AlarmDataBase;
import com.example.alarmapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AlarmFragment extends Fragment {
    private FloatingActionButton fabAdd_Alarm;
    private RecyclerView recyclerView_Alarm;
    private List<Alarm> alarmList;
    private Alarm_Recycler_Adapter adapter_Alarm;
    private AlarmDataBase dataBase;


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

        // find id
        recyclerView_Alarm = view.findViewById(R.id.rcvList_Alarm);
        fabAdd_Alarm = view.findViewById(R.id.fabAddAlarm);
        //init object
        alarmList = new ArrayList<>();
        dataBase = new AlarmDataBase(getContext());
        // Tạo và cấu hình RecyclerView
        adapter_Alarm = new Alarm_Recycler_Adapter(getContext(), alarmList, dataBase);
        recyclerView_Alarm.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView_Alarm.setAdapter(adapter_Alarm);

        // Them vao database khi thoat ra
        initRecyclerViewWhenStart();
        adapter_Alarm.notifyDataSetChanged();

        setEventFab();
        // Tạo và thiết lập ItemTouchHelper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipToDeleteAlarm(adapter_Alarm,this,dataBase));
        itemTouchHelper.attachToRecyclerView(recyclerView_Alarm);
        return view;
    }

    public void initRecyclerViewWhenStart(){
        List<Alarm> list = new ArrayList<>();
        dataBase.getData(list);
        for(Alarm alarm : list){
            String id = alarm.getId();
            String time = alarm.getTime_alarm();
            Boolean status = alarm.getTurnOn();
            String message = alarm.getMessage();
            Alarm alarmAdapter = new Alarm();
            alarmAdapter.setId(id);
            alarmAdapter.setTime_alarm(time);
            alarmAdapter.setMessage(message);
            alarmAdapter.setTurnOn(status);
            alarmList.add(0,alarmAdapter);
            adapter_Alarm.notifyItemInserted(0);
            Log.i("TAG", String.valueOf(alarm.getTurnOn()));
        }
    }
    private void setEventFab(){
        fabAdd_Alarm.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CreateNewAlarmActivity.class);
            startActivityForResult(intent,99);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==99&&resultCode==98){
            String time = data.getStringExtra("time");
            String name = data.getStringExtra("nameAlarm");
            Alarm newAlarm = new Alarm(alarmList.size()+"", time , true, name);
            alarmList.add(newAlarm);
            adapter_Alarm.notifyItemInserted(alarmList.size());
            // Ihem database vao !
            dataBase.putData(newAlarm);
        }
    }

}
