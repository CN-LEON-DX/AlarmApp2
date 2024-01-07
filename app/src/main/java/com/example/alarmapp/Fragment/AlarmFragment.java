package com.example.alarmapp.Fragment;

import android.annotation.SuppressLint;
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

import com.example.alarmapp.Activity.CreateNewAlarmActivity;
import com.example.alarmapp.Activity.DeleteActivity;
import com.example.alarmapp.Adapter.Alarm_Recycler_Adapter;
import com.example.alarmapp.Base.Alarm;
import com.example.alarmapp.Base.SwipeToDeleteAlarm;
import com.example.alarmapp.Database.AlarmDataBase;
import com.example.alarmapp.Interface.OnItemAlarmClickListener;
import com.example.alarmapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AlarmFragment extends Fragment  implements   OnItemAlarmClickListener {
    private FloatingActionButton fabAdd_Alarm;
    private RecyclerView recyclerView_Alarm;
    private List<Alarm> alarmList;
    private Alarm_Recycler_Adapter adapter_Alarm;
    private AlarmDataBase dataBase;
    private int idAlarm=0;
    private static final int REQUEST_CODE_NEW_ALARM = 99;
    private static final int REQUEST_CODE_CHANGE_ALARM = 98;
    private static final int REQUEST_CODE_REMOVE_MANY_ALARM=97;
    private static final int RESULT_CODE_NEW_ALARM=99;
    private static final int RESULT_CODE_CHANGE_ALARM = 98;
    private static final int RESULT_CODE_REMOVE_MANY_ALARM=97;

    public AlarmFragment() {
        // Required empty public constructor
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
        adapter_Alarm = new Alarm_Recycler_Adapter(getContext(), alarmList, dataBase, this);
        recyclerView_Alarm.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView_Alarm.setAdapter(adapter_Alarm);
        // Them vao database khi thoat ra
        initRecyclerViewWhenStart();
        //set event
        setEventFab();
        // Tạo và thiết lập ItemTouchHelper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteAlarm(adapter_Alarm,this,dataBase));
        itemTouchHelper.attachToRecyclerView(recyclerView_Alarm);
        return view;
    }
    private int maxIdAlarm(List<Alarm> list){
        int maxId= list.get(0).getId();
        for(int i=1;i<list.size();++i){
            int idAlarm= list.get(i).getId();
            if(idAlarm>maxId) maxId=idAlarm;
        }
        return maxId;
    }
    public void initRecyclerViewWhenStart(){
        dataBase.getData(alarmList);
        for(Alarm alarm : alarmList){
            if(alarm.getTurnOn()) alarm.createAlarm(getContext());
        }
        if(alarmList.size()>1)
            idAlarm=maxIdAlarm(alarmList);
        adapter_Alarm.notifyDataSetChanged();
    }
    private void setEventFab(){
        fabAdd_Alarm.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CreateNewAlarmActivity.class);
            intent.putExtra("requestCode",REQUEST_CODE_NEW_ALARM);
            startActivityForResult(intent,REQUEST_CODE_NEW_ALARM);
        });
    }
    private Alarm getDataFromIntentResult(Intent data){
        String time = data.getStringExtra("timeCreate");
        String name = data.getStringExtra("nameAlarmCreate");
        String sound = data.getStringExtra("soundCreate");
        String repeat= data.getStringExtra("RepeatCreate");
        boolean isVibrate=data.getBooleanExtra("isVibrateCreate",false);
        boolean isRepeat = data.getBooleanExtra("isRepeatCreate",false);
        return new Alarm(time,++idAlarm,true,name,sound,repeat,isVibrate,isRepeat);
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_NEW_ALARM&&resultCode==RESULT_CODE_NEW_ALARM&&data!=null){
            Alarm newAlarm = getDataFromIntentResult(data);
            newAlarm.createAlarm(getContext());
            alarmList.add(0,newAlarm);
            adapter_Alarm.notifyItemInserted(0);
            dataBase.putData(newAlarm);
        }
        if(requestCode==REQUEST_CODE_CHANGE_ALARM&&resultCode==RESULT_CODE_CHANGE_ALARM&&data!=null){
            int position =data.getIntExtra("position",-1);
            if(position!=-1){
                Alarm newAlarm = getDataFromIntentResult(data);
                Alarm oldAlarm = alarmList.get(position);
                oldAlarm.cancelAlarm(getContext());
                alarmList.get(position).setId(newAlarm.getId());
                alarmList.get(position).setMessage(newAlarm.getMessage());
                alarmList.get(position).setTime_alarm(newAlarm.getTime_alarm());
                alarmList.get(position).setRepeat(newAlarm.getRepeat());
                alarmList.get(position).setSound(newAlarm.getSound());
                alarmList.get(position).setTurnOn(newAlarm.getTurnOn());
                alarmList.get(position).setRepeat(newAlarm.isRepeat());
                alarmList.get(position).setVibrate(newAlarm.isVibrate());
                adapter_Alarm.notifyItemChanged(position);
                newAlarm.createAlarm(getContext());
                dataBase.updateAlarm(oldAlarm,newAlarm);
            }
        }
        if(requestCode==REQUEST_CODE_REMOVE_MANY_ALARM&&resultCode==RESULT_CODE_REMOVE_MANY_ALARM&&data!=null){
            alarmList.clear();
            dataBase.deleteAllData();
            ArrayList<Alarm> resultList = data.getParcelableArrayListExtra("resultListAlarm");
            if(resultList!=null) alarmList.addAll(resultList);
            adapter_Alarm.notifyDataSetChanged();
            for(Alarm alarm:alarmList) {
                dataBase.putData((alarm));
            }

        }
    }

    @Override
    public void setOnItemClickListener(int position) {
        Alarm alarm = alarmList.get(position);
        // Handle item click here
        Intent intent = new Intent(getContext(),CreateNewAlarmActivity.class);
        intent.putExtra("requestCode",REQUEST_CODE_CHANGE_ALARM);
        intent.putExtra("position",position);
        intent.putExtra("sound",alarm.getSound());
        intent.putExtra("isRepeat",alarm.isRepeat());
        intent.putExtra("isVibrate",alarm.isVibrate());
        intent.putExtra("repeat",alarm.getRepeat());
        intent.putExtra("nameAlarm", alarm.getMessage());
        intent.putExtra("time", alarm.getTime_alarm());
        startActivityForResult(intent,REQUEST_CODE_CHANGE_ALARM);
    }

    @Override
    public void setOnItemLongClickListener( int position) {
        for(Alarm alarm: alarmList) alarm.setChecked(false);
        Alarm alarm = alarmList.get(position);
        alarm.setChecked(true);
        Intent intent = new Intent(getContext(), DeleteActivity.class);
        intent.putExtra("listAlarm",new ArrayList<>(alarmList));
        intent.putExtra("position",position);
        intent.putExtra("requestCode",REQUEST_CODE_REMOVE_MANY_ALARM);
        startActivityForResult(intent,REQUEST_CODE_REMOVE_MANY_ALARM);
    }
}
