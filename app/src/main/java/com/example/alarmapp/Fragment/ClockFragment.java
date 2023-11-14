package com.example.alarmapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Activity.SelectClockActivity;
import com.example.alarmapp.Adapter.Clock_Recycler_Adapter;
import com.example.alarmapp.Base.Clock;

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
    private static final long TIME_UPDATE_INTERVAL=1000*60;
    private static final long TIME_UPDATE_TIME_DIFFERENCES=1000*60*60;
    private static final long TIME_UPDATE_DAY=1000*60*60*24;
    private RecyclerView recyclerView_Clock;
    private List<Clock> clockList;
    private FloatingActionButton fabAdd_Clock;
    private TextView tvDate;
    private Clock_Recycler_Adapter clockRecyclerAdapter;
    private WatchTimeCityDatabase database;
    private Handler handlerInternal,handlerTimeDifferences,handlerDay;
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
        recyclerView_Clock = view.findViewById(R.id.rcvList_Clock);
        fabAdd_Clock = view.findViewById(R.id.fabAddClock);
        tvDate =view.findViewById(R.id.tv_date);
        //initialization object
        clockList = new ArrayList<>();
        database= new WatchTimeCityDatabase(getContext());
        clockRecyclerAdapter = new Clock_Recycler_Adapter(getContext(), clockList);
        initRecyclerViewWhenStart();
        //set Adapter
        recyclerView_Clock.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView_Clock.setAdapter(clockRecyclerAdapter);
        //set event
        setListenerForFabButton();
        //set text tvDate
        setDataForTvDate();

        return view;
    }
    public void initRecyclerViewWhenStart(){
        List<Clock> list = new ArrayList<>();
        database.getData(list);
        for(Clock clock : list){
            clockRecyclerAdapter.addClock(clock);
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
            clock.setCity(data.getStringExtra("city"));
            clock.setTimeDifferences(data.getStringExtra("timeDifferences"));
            clock.setTimeZone(data.getStringExtra("timeZone"));
            clockRecyclerAdapter.addClock(clock);
            database.putData(clock);
        }
    }
    public void setDataForTvDate(){
        Date currentDate = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy");
        String date =dateFormat.format(currentDate);
        tvDate.setText(date);
    }
    public void autoUpdateTime(){
        handlerInternal.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },TIME_UPDATE_INTERVAL);
    }
    public void updateTime(){
        for(Clock clock : clockList){

        }
    }
}
