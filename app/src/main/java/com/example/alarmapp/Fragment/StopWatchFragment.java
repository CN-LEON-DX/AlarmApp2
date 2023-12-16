package com.example.alarmapp.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alarmapp.Adapter.StopWatchAdapter;
import com.example.alarmapp.Base.StopWatch;
import com.example.alarmapp.Database.StopWatchDatabase;
import com.example.alarmapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StopWatchFragment extends Fragment {
    private StopWatchAdapter stopWatchAdapter;
    private List<StopWatch> stopWatchList;
    private TextView tvTimer;
    private Button btnStartAndStop,btnResetAndMark;
    private Handler handler;
    private String nextStatus="start";
    private Runnable runnable;
    private boolean isRunning =false,isFirstReadFromSharedPrefs=true;
    private long elapsedTime;
    private long elapsedTimeMark;
    private LinearLayout layout;
    private TextView tv_id,tv_timeRecord,tv_timeAdd;
    private SharedPreferences sharedPreferences;
    private StopWatchDatabase stopWatchDatabase;
    private long startTime,startTimeMark;
    public StopWatchFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("UseRequireInsteadOfGet")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        //find id
        tvTimer = view.findViewById(R.id.tvTimer);
        btnStartAndStop=view.findViewById(R.id.btn_start_stop);
        btnResetAndMark=view.findViewById(R.id.btn_reset_mark);
        layout=view.findViewById(R.id.layout_timeMark);
        tv_id=view.findViewById(R.id.tv_index);
        tv_timeRecord=view.findViewById(R.id.tv_timeRecordFragment);
        tv_timeAdd=view.findViewById(R.id.tv_timeAddedFragment);
        RecyclerView rcvStopWatch = view.findViewById(R.id.rcvListStopWatch);
        //initialize object
        stopWatchDatabase = new StopWatchDatabase(getContext());
        stopWatchList = new ArrayList<>();
        stopWatchAdapter = new StopWatchAdapter(stopWatchList, getContext());
        //setup for recyclerView
        rcvStopWatch.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvStopWatch.setAdapter(stopWatchAdapter);
        //set listener for button
        setListenerForBtnStartAndStop();
        setListenerForBtnResetAndMark();
        //read from shared Preferences
        readDataFromSharedPreferences();
        return view;
    }
    //write data to sharedPreferences when fragment stop
    @SuppressLint("UseRequireInsteadOfGet")
    @Override
    public void onStop() {
        super.onStop();
        sharedPreferences= Objects.requireNonNull(getContext()).getSharedPreferences("sharedPrefsStopWatch",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("nextStatus",nextStatus);
        editor.putBoolean("isRunning",isRunning);
        editor.putLong("elapsedTime",elapsedTime);
        editor.putLong("elapsedTimeMark",elapsedTimeMark);
        editor.putBoolean("isFirstPut",isFirstReadFromSharedPrefs);
        editor.apply();
    }
    //read Data from SharedPreferences when fragment create and update UI

    @SuppressLint("UseRequireInsteadOfGet")
    private void readDataFromSharedPreferences(){
        sharedPreferences= Objects.requireNonNull(getContext()).getSharedPreferences("sharedPrefsStopWatch",Context.MODE_PRIVATE);
        isFirstReadFromSharedPrefs=sharedPreferences.getBoolean("isFirstPut",true);
        if(!isFirstReadFromSharedPrefs) {
            isRunning=sharedPreferences.getBoolean("isRunning",false);
            nextStatus=sharedPreferences.getString("nextStatus","test");
            elapsedTime=sharedPreferences.getLong("elapsedTime",0);
            elapsedTimeMark=sharedPreferences.getLong("elapsedTimeMark",0);
            updateUI();
        }
        else isFirstReadFromSharedPrefs=false;
    }
    //update when open fragment
    @SuppressLint("NotifyDataSetChanged")
    public void updateUI(){
        switch (nextStatus) {
            case "start":
                btnResetAndMark.setVisibility(View.GONE);
                btnStartAndStop.setText("Bắt đầu");
                break;
            case "continue":
                btnResetAndMark.setVisibility(View.VISIBLE);
                btnStartAndStop.setText(R.string.continueTextButton);
                btnResetAndMark.setText("Đặt Lại");
                tvTimer.setText(calculateTime(elapsedTime));
                stopWatchDatabase.getData(stopWatchList);
                if (stopWatchList.size() != 0) {
                    stopWatchAdapter.notifyDataSetChanged();
                    layout.setVisibility(View.VISIBLE);
                    tv_id.setText(String.valueOf(stopWatchList.size() + 1));
                    tv_timeRecord.setText(calculateTime(elapsedTime));
                    tv_timeAdd.setText(calculateTime(elapsedTimeMark));
                }
                break;
            case "stop":
                btnResetAndMark.setVisibility(View.VISIBLE);
                btnStartAndStop.setText(R.string.stop);
                btnResetAndMark.setText(R.string.Mark);
                if (handler != null && runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                stopWatchDatabase.getData(stopWatchList);
                if (stopWatchList.size() != 0) {
                    stopWatchAdapter.notifyDataSetChanged();
                    layout.setVisibility(View.VISIBLE);
                    tv_id.setText(String.valueOf(stopWatchList.size() + 1));
                }
                startTime();
                break;
        }
    }
    @SuppressLint("DefaultLocale")
    public String calculateTime(long elapsedTime){

        int milliseconds = (int) ((elapsedTime / 10) % 100);
        int seconds = (int) ((elapsedTime / 1000) % 60);
        int minutes = (int) (elapsedTime / 60000);
        if(minutes>=60) {
            minutes=(int) (elapsedTime/60000)%60;
            int hour = (int) (elapsedTime/3600000);
            return  String.format("%02d:%02d:%02d,%02d",hour, minutes, seconds, milliseconds);
        } else return String.format("%02d:%02d,%02d", minutes, seconds, milliseconds);
    }
    public void startTime() {
        handler = new Handler();
        if (isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime;
        } else {
            startTime = System.currentTimeMillis();
        }
        startTimeMark = System.currentTimeMillis();
        if(nextStatus.equals("continue"))
            startTimeMark=System.currentTimeMillis()-elapsedTimeMark;
        runnable = new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                long currentTimeMark=System.currentTimeMillis();

                elapsedTime = currentTime - startTime;
                elapsedTimeMark = currentTimeMark-startTimeMark;

                String timeMark=calculateTime(elapsedTimeMark);
                String time = calculateTime(elapsedTime);
                tv_timeAdd.setText(timeMark);
                tvTimer.setText(time);
                tv_timeRecord.setText(time);
                handler.postDelayed(this, 1);
            }
        };
        handler.post(runnable);
    }
    public void setListenerForBtnStartAndStop() {
        btnStartAndStop.setOnClickListener(v -> {
            switch (nextStatus) {
                case "start":
                    eventForStart();
                    break;
                case "stop":
                    eventForStop();
                    break;
                case "continue":
                    eventForContinue();
                    break;
            }
        });
    }
    public void setListenerForBtnResetAndMark() {
        btnResetAndMark.setOnClickListener(v -> {
            if (nextStatus.equals("stop")) {//event mark
                eventForMark();
            } else if (nextStatus.equals("continue")) {
               eventForReset();
            }
        });
    }
    private void eventForStart(){
        startTime();
        nextStatus = "stop";
        btnStartAndStop.setText(R.string.stop);
        btnResetAndMark.setText(R.string.Mark);
        btnResetAndMark.setVisibility(View.VISIBLE);
        isRunning=true;
    }
    private void eventForStop(){
        if(handler!=null&&runnable!=null) handler.removeCallbacks(runnable);
        nextStatus = "continue";
        btnStartAndStop.setText(R.string.continueTextButton);
        btnResetAndMark.setText("đặt lại");
        btnResetAndMark.setVisibility(View.VISIBLE);
    }
    private void eventForContinue(){
        isRunning = true;
        startTime();
        nextStatus = "stop";
        btnResetAndMark.setText(R.string.Mark);
        btnStartAndStop.setText(R.string.stop);
        btnResetAndMark.setVisibility(View.VISIBLE);
    }
    private void eventForMark(){
        isRunning=true;
        layout.setVisibility(View.VISIBLE);
        tv_id.setText(String.valueOf(stopWatchList.size()+2));
        if(handler!=null&&runnable!=null)
            handler.removeCallbacks(runnable);

        startTime();
        StopWatch stopWatch = new StopWatch();
        stopWatch.setIndexOf(String.valueOf(stopWatchList.size() + 1));
        stopWatch.setTimeRecord(calculateTime(elapsedTime));
        stopWatch.setTimeAdd(calculateTime(elapsedTimeMark));
        stopWatchList.add(0,stopWatch);
        stopWatchDatabase.putData(stopWatch);
        stopWatchAdapter.notifyItemInserted(0);
    }
    private void eventForReset(){
        isRunning = false;
        nextStatus = "start";
        elapsedTime=0;
        elapsedTimeMark=0;
        btnResetAndMark.setVisibility(View.GONE);
        stopWatchDatabase.clearData();
        if(handler!=null&&runnable!=null)
            handler.removeCallbacks(runnable);
        tvTimer.setText(R.string.defaultStopWatchTime);
        btnStartAndStop.setText("bắt đầu");
        tv_timeRecord.setText(R.string.defaultStopWatchTime);
        tv_timeAdd.setText(R.string.defaultStopWatchTime);
        layout.setVisibility(View.GONE);
        if(!stopWatchList.isEmpty()) stopWatchList.clear();
    }
}