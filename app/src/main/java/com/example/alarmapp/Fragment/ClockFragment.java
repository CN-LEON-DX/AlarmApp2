package com.example.alarmapp.Fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Activity.SelectClockActivity;
import com.example.alarmapp.Adapter.Clock_Recycler_Adapter;
import com.example.alarmapp.Base.Clock;

import com.example.alarmapp.Base.SwipeToDeleteClock;
import com.example.alarmapp.Database.WatchTimeCityDatabase;
import com.example.alarmapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClockFragment extends Fragment{

    private List<Clock> clockList;
    private FloatingActionButton fabAdd_Clock;
    private TextView tvDate;
    private WatchTimeCityDatabase database;
    private Clock_Recycler_Adapter clockRecyclerAdapter;
    public ClockFragment() {
        // Required empty public constructor
    }

    public static ClockFragment newInstance(String param1, String param2) {
        ClockFragment fragment = new ClockFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clock, container, false);
        //find id
        RecyclerView recyclerView_Clock = view.findViewById(R.id.rcvList_Clock);
        fabAdd_Clock = view.findViewById(R.id.fabAddClock);
        tvDate =view.findViewById(R.id.tv_date);
        //initialization object
        clockList = new ArrayList<>();
        database= new WatchTimeCityDatabase(getContext());
        clockRecyclerAdapter = Clock_Recycler_Adapter.createInstance();
        //set Adapter
        clockRecyclerAdapter.submitList(clockList);
        recyclerView_Clock.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView_Clock.setAdapter(clockRecyclerAdapter);
        //set event
        setListenerForFabButton();
        // Tạo và thiết lập ItemTouchHelper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteClock( clockRecyclerAdapter,database,this));
        itemTouchHelper.attachToRecyclerView(recyclerView_Clock);
        //set text tvDate
        setDataForTvDate();
        initRecyclerViewWhenStart();
        return view;
    }

    public void initRecyclerViewWhenStart(){
        List<Clock> list = new ArrayList<>();
        database.getData(list);
        for(Clock clock : list){
            String city = clock.getCity();
            String timeDifferences=clock.getTimeDifferences();
            String timeZone= clock.getTimeZone();
            Clock clockAdapter = new Clock();
            clockAdapter.setCity(city);
            clockAdapter.setTimeDifferences(clockAdapter.getFormattedTime(Integer.parseInt(timeDifferences)));
            clockAdapter.setTimeZone(timeZone);
            clockList.add(clockAdapter);
            clockRecyclerAdapter.notifyItemInserted(clockList.size());

        }
    }
    public void setListenerForFabButton(){
        fabAdd_Clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectClockActivity.class);
                startActivityForResult(intent,99);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==99&&resultCode==99&&data!=null){
            Clock clock = new Clock();
            String city=data.getStringExtra("city");
            String timeDifferences=data.getStringExtra("timeDifferences");
            String timeZone = data.getStringExtra("timeZone");
            boolean isSelected =false;
            for(Clock clockFromList : clockList){
                if(clockFromList.getCity().equals(city)) isSelected = true;
            }
            if(!isSelected){
                database.putData(new Clock(city,timeDifferences,timeZone));
                clock.setCity(city);
                clock.setTimeDifferences(clock.getFormattedTime(Integer.parseInt(timeDifferences)));
                clock.setTimeZone(timeZone);
                clockList.add(0,clock);
                clockRecyclerAdapter.notifyItemInserted(0);
            }else Toast.makeText(getContext(),"bạn đã chọn thành phố này",Toast.LENGTH_LONG).show();
        }
    }
    public void setDataForTvDate(){
        Date currentDate = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy");
        String date =dateFormat.format(currentDate);
        tvDate.setText(date);
    }
    private void setAutoUpdateTime(){

    }
}
