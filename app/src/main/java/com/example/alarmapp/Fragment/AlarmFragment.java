package com.example.alarmapp.Fragment;

import android.app.AlertDialog;
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
import android.widget.EditText;

import com.example.alarmapp.Activity.CreateNewAlarmActivity;
import com.example.alarmapp.Adapter.Alarm_Recycler_Adapter;
import com.example.alarmapp.Base.Alarm;
import com.example.alarmapp.Base.SwipToDeleteAlarm;
import com.example.alarmapp.Database.AlarmDataBase;
import com.example.alarmapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AlarmFragment extends Fragment  implements  Alarm_Recycler_Adapter.OnItemClickListener{
    private FloatingActionButton fabAdd_Alarm;
    private RecyclerView recyclerView_Alarm;
    private List<Alarm> alarmList;
    private Alarm_Recycler_Adapter adapter_Alarm;
    private AlarmDataBase dataBase;
    private int idAlarm=0;

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
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipToDeleteAlarm(adapter_Alarm,this,dataBase));
        itemTouchHelper.attachToRecyclerView(recyclerView_Alarm);
        return view;
    }

    public void initRecyclerViewWhenStart(){
        dataBase.getData(alarmList);
        idAlarm=alarmList.size()-1;
        adapter_Alarm.notifyDataSetChanged();
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
            Log.i("Tag t", time);
            String name = data.getStringExtra("nameAlarm");
            Log.i("tag", name);
            String sound = data.getStringExtra("sound");
            String repeat= data.getStringExtra("repeat");
            boolean isVibrate=data.getBooleanExtra("isVibrate",false);
            boolean isRepeat = data.getBooleanExtra("isRepeat",false);
            Alarm newAlarm = new Alarm(time,++idAlarm,true,name,sound,repeat,isVibrate,isRepeat);
            alarmList.add(0,newAlarm);
            adapter_Alarm.setAlarm(newAlarm);
            adapter_Alarm.notifyItemInserted(0);
            // Goi adapter them vào các alarm và thông báo của nó
            // Ihem database vao !
            dataBase.putData(newAlarm);

        }
    }
    @Override
    public void onItemClick(int position) {
        // Handle item click here
        Log.i("Tag edit", " editing");
        showEditAlarmDialog(position);
    }

    private void showEditAlarmDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Sửa báo thức");

        View view = LayoutInflater.from(requireContext()).inflate(R.layout.edit_alarm_dialog, null);
        builder.setView(view);


        // Để lấy thời gian của cái đồng hồ vừa nhấn
        // thiết lập cho thông báo rồi sau đó là thiết lập lại sau khi người dùng nhan
         EditText editHour = view.findViewById(R.id.edtHour);
         EditText editMinute = view.findViewById(R.id.edtMinute);
         EditText editName = view.findViewById(R.id.edtMessage);

        Alarm selectedAlarm = alarmList.get(position);
         String[] timearray = selectedAlarm.getTime_alarm().split(":");
        editHour.setText(timearray[0]);
        editMinute.setText(timearray[1]);
        editName.setText(selectedAlarm.getMessage());

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            int newHour = Integer.parseInt(editHour.getText().toString().trim());
            int newMinute = Integer.parseInt(editMinute.getText().toString().trim());
            String newName = editName.getText().toString().trim();

            String temptime = formatTime(newHour, newMinute);
            selectedAlarm.setTime_alarm(temptime);
            selectedAlarm.setMessage(newName);
            adapter_Alarm.notifyItemChanged(position);

            dataBase.updateDatabase(selectedAlarm);
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> {
            dialog.cancel();
        });

        builder.show();
    }
    private String formatTime(int hour, int minute) {
        return String.format("%02d:%02d", hour, minute);
    }


}
