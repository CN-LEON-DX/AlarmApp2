package com.example.alarmapp.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Model.Adapter.StopWatchAdapter;
import com.example.alarmapp.Model.item.StopWatch;
import com.example.alarmapp.R;
import java.util.ArrayList;
import java.util.List;

public class StopWatchFragment extends Fragment {
    private StopWatchAdapter stopWatchAdapter;
    private List<StopWatch> stopWatchList;
    private TextView tvTimer;
    private Button btnStartAndStop,btnResetAndMark;
    private Handler handler,handlerTimeAdd,handlerTimeRecord;
    private String nextStatus="start";
    private Runnable runnable,runnableTimeAdd,runnableTimeRecord;
    private boolean isRunning =false;
    private boolean isMarking=false;
    private long elapsedTime;
    private long elapsedTimeMark;
    private LinearLayout layout;
    private TextView tv_id,tv_timeRecord,tv_timeAdd;
    private SharedPreferences sharedPreferences;
    public StopWatchFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
        //setup for recyclerView
        RecyclerView rcvStopWatch = view.findViewById(R.id.rcvListStopWatch);
        stopWatchList = new ArrayList<>();
         stopWatchAdapter = new StopWatchAdapter(stopWatchList, getContext());
        rcvStopWatch.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvStopWatch.setAdapter(stopWatchAdapter);
        //set listener for button
        setListenerForBtnStartAndStop();
        setListenerForBtnResetAndMark();
        //setup read from sharedPreferences
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences= getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String nextStatus=sharedPreferences.getString("status","start");
        Long elapsedTime=sharedPreferences.getLong("elapsedTime",0);
        Boolean isRunning=sharedPreferences.getBoolean("isRunning",false);
        Log.i("TAG_CREATE","status:"+nextStatus+" isRunning:"+isRunning+" elapsedTime:"+elapsedTime);

        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        sharedPreferences= getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("status", nextStatus);
        editor.putBoolean("isRunning", isRunning);
        editor.putLong("elapsedTime", elapsedTime);
        Log.i("TAG_DESTROY","status:"+nextStatus+ " isRunning:"+isRunning+ " elapsedTime:"+elapsedTime);
        editor.apply();
    }
//    private void updateUI() {
//        // Cập nhật giao diện dựa trên giá trị đọc được
//        if (nextStatus.equals("start")) {
//            // Cài đặt giao diện cho trạng thái "start"
//            btnStartAndStop.setText("Bắt đầu");
//            btnResetAndMark.setVisibility(View.GONE);
//            tvTimer.setText("00:00,00");
//        } else if (nextStatus.equals("stop")) {
//            // Cài đặt giao diện cho trạng thái "stop"
//            btnStartAndStop.setText("Tạm dừng");
//            btnResetAndMark.setText("Ghi");
//            btnResetAndMark.setVisibility(View.VISIBLE);
//            startTime(); // Bắt đầu tính thời gian lại nếu cần
//        } else if (nextStatus.equals("continue")) {
//            // Cài đặt giao diện cho trạng thái "continue"
//            btnStartAndStop.setText("Tạm dừng");
//            btnResetAndMark.setText("Ghi");
//            btnResetAndMark.setVisibility(View.VISIBLE);
//            startTime();
//        }
//    }

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
        long startTime;
        if (isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime;
        } else {
            startTime = System.currentTimeMillis();
        }
        runnable = new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                elapsedTime = currentTime - startTime;
                String time = calculateTime(elapsedTime);
                tvTimer.setText(time);
                tv_timeRecord.setText(time);
                handler.postDelayed(this, 1);
            }
        };
        handler.post(runnable);
    }
    public void startTimeAdd(){
        handlerTimeAdd = new Handler();
        long startTime = System.currentTimeMillis();
        runnableTimeAdd=new Runnable() {
            @Override
            public void run() {
                long currentTime=System.currentTimeMillis();
                elapsedTimeMark = currentTime-startTime;
                String time=calculateTime(elapsedTimeMark);
                tv_timeAdd.setText(time);
                handlerTimeAdd.postDelayed(this,1);
            }
        };
        handlerTimeAdd.post(runnableTimeAdd);
    }
    public void startTimeRecord(){
        handlerTimeRecord = new Handler();
        long startTime = System.currentTimeMillis()-elapsedTime;
        runnableTimeRecord=new Runnable() {
            @Override
            public void run() {
                long currentTime=System.currentTimeMillis();
                elapsedTime = currentTime-startTime;
                String time=calculateTime(elapsedTime);
                tv_timeRecord.setText(time);
                handlerTimeRecord.postDelayed(this,1);
            }
        };
        handlerTimeRecord.post(runnableTimeRecord);
    }
    public void setListenerForBtnStartAndStop() {
        btnStartAndStop.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (nextStatus.equals("start")) {
                    startTime();
                    startTimeAdd();
                    nextStatus = "stop";
                    btnStartAndStop.setText("Tạm dừng");
                    btnResetAndMark.setText("ghi");
                    btnResetAndMark.setVisibility(View.VISIBLE);
                    Log.i("status",nextStatus);
                } else if (nextStatus.equals("stop")) {
                    handler.removeCallbacks(runnable);
                    if(isMarking){
                        handlerTimeAdd.removeCallbacks(runnableTimeAdd);
                        handlerTimeRecord.removeCallbacks(runnableTimeRecord);


                    }
                    nextStatus = "continue";
                    btnStartAndStop.setText("Tiếp tục");
                    btnResetAndMark.setText("đặt lại");
                    btnResetAndMark.setVisibility(View.VISIBLE);
                    Log.i("status","elapsed:"+elapsedTime+" elapsedMark:"+elapsedTimeMark);
                } else if (nextStatus.equals("continue")) {
                    isRunning = true;
                    startTime();
                    if(isMarking){
                        startTimeRecord();
                        startTimeAdd();
                        Log.i("status","elapsed:"+elapsedTime+" elapsedMark: "+elapsedTimeMark);
                    }
                    nextStatus = "stop";
                    btnResetAndMark.setText("ghi");
                    btnStartAndStop.setText("Tạm dừng");
                    btnResetAndMark.setVisibility(View.VISIBLE);
                    Log.i("status",nextStatus);
                }
            }
        });
    }
    public void setListenerForBtnResetAndMark() {
        btnResetAndMark.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (nextStatus.equals("stop")) {
                    layout.setVisibility(View.VISIBLE);
                    tv_id.setText(String.valueOf(stopWatchList.size()+2));
                    if (handlerTimeAdd != null && runnableTimeAdd != null) {
                        handlerTimeAdd.removeCallbacks(runnableTimeAdd);
                    }
                    if (handlerTimeRecord != null && runnableTimeRecord != null) {
                        handlerTimeRecord.removeCallbacks(runnableTimeRecord);
                    }

                    startTimeRecord();
                    startTimeAdd();
                    StopWatch stopWatch = new StopWatch();
                    stopWatch.setIndexOf(String.valueOf(stopWatchList.size() + 1));
                    stopWatch.setTimeRecord(calculateTime(elapsedTime));
                    stopWatch.setTimeAdd(calculateTime(elapsedTimeMark));
                    stopWatchList.add(0,stopWatch);
                    stopWatchAdapter.notifyItemInserted(0);
                    isMarking=true;
                } else if (nextStatus.equals("continue")) {
                    isRunning = false;
                    nextStatus = "start";
                    elapsedTime=0;
                    elapsedTimeMark=0;
                    btnResetAndMark.setVisibility(View.GONE);
                    handler.removeCallbacks(runnable);
                    handlerTimeAdd.removeCallbacks(runnableTimeAdd);
                    handlerTimeRecord.removeCallbacks(runnableTimeRecord);
                    tvTimer.setText("00:00,00");
                    btnStartAndStop.setText("Bắt đầu");
                    tv_timeRecord.setText("00:00,00");
                    tv_timeAdd.setText("00:00,00");
                    layout.setVisibility(View.GONE);
                    if(!stopWatchList.isEmpty()) stopWatchList.clear();
                    Log.i("status",nextStatus);
                }
            }
        });
    }
}