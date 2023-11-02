package com.example.alarmapp.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alarmapp.Adapter.StopWatch;
import com.example.alarmapp.Adapter.StopWatchAdapter;
import com.example.alarmapp.R;
import java.util.ArrayList;
import java.util.List;

public class StopWatchFragment extends Fragment {
    private List<StopWatch> stopWatchList;
    private TextView tvTimer;
    private Button btnStartAndStop,btnResetAndMark;
    private Handler handler;
    private String nextStatus="start";
    private Runnable runnable;
    private boolean isRunning =false;
    private long elapsedTime;
    private SharedPreferences sharedPreferencesPut,sharedPreferencesRead;
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
        //setup for recyclerView
        RecyclerView rcvStopWatch = view.findViewById(R.id.rcvListStopWatch);
        stopWatchList = new ArrayList<>();
        StopWatchAdapter stopWatchAdapter = new StopWatchAdapter(stopWatchList, getContext());
        rcvStopWatch.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvStopWatch.setAdapter(stopWatchAdapter);
        //set listener for button
        setListenerForBtnStartAndStop();
        setListenerForBtnResetAndMark();
        //setup read from sharedPreferences
        sharedPreferencesRead= PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferencesRead= getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        nextStatus=sharedPreferencesRead.getString("status","start");
        elapsedTime=sharedPreferencesRead.getLong("elapsedTime",0);
        isRunning=sharedPreferencesRead.getBoolean("isRunning",false);
        updateUI();
        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedPreferencesPut.edit();
        editor.putString("status", nextStatus);
        editor.putBoolean("isRunning", isRunning);
        editor.putLong("elapsedTime", elapsedTime);
        editor.apply();
    }
    private void updateUI() {
        // Cập nhật giao diện dựa trên giá trị đọc được
        if (nextStatus.equals("start")) {
            // Cài đặt giao diện cho trạng thái "start"
            btnStartAndStop.setText("Bắt đầu");
            btnResetAndMark.setVisibility(View.GONE);
            tvTimer.setText("00:00,00");
        } else if (nextStatus.equals("stop")) {
            // Cài đặt giao diện cho trạng thái "stop"
            btnStartAndStop.setText("Tạm dừng");
            btnResetAndMark.setText("Ghi");
            btnResetAndMark.setVisibility(View.VISIBLE);
            startTime(); // Bắt đầu tính thời gian lại nếu cần
        } else if (nextStatus.equals("continue")) {
            // Cài đặt giao diện cho trạng thái "continue"
            btnStartAndStop.setText("Tạm dừng");
            btnResetAndMark.setText("Ghi");
            btnResetAndMark.setVisibility(View.VISIBLE);
            startTime();
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
        long startTime;
        if (isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime;
        } else{
            startTime = System.currentTimeMillis();
        }
        runnable = new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                elapsedTime = currentTime - startTime;

                String time = calculateTime(elapsedTime);
                tvTimer.setText(time);

                handler.postDelayed(this, 1);
            }
        };

        handler.post(runnable);
    }
    public void setListenerForBtnStartAndStop() {
        btnStartAndStop.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (nextStatus.equals("start")) {
                    startTime();
                    nextStatus = "stop";
                    btnStartAndStop.setText("Tạm dừng");
                    btnResetAndMark.setText("ghi");
                    btnResetAndMark.setVisibility(View.VISIBLE);
                } else if (nextStatus.equals("stop")) {
                    handler.removeCallbacks(runnable);
                    nextStatus = "continue";
                    btnStartAndStop.setText("Tiếp tục");
                    btnResetAndMark.setText("đặt lại");
                    btnResetAndMark.setVisibility(View.VISIBLE);
                } else if (nextStatus.equals("continue")) {
                    isRunning = true;
                    startTime();
                    nextStatus = "stop";
                    btnResetAndMark.setText("ghi");
                    btnStartAndStop.setText("Tạm dừng");
                    btnResetAndMark.setVisibility(View.VISIBLE);
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
                    // Reset logic here if needed
                } else if (nextStatus.equals("continue")) {
                    isRunning = false;
                    nextStatus = "start";
                    elapsedTime=0;
                    btnResetAndMark.setVisibility(View.GONE);
                    handler.removeCallbacks(runnable);
                    tvTimer.setText("00:00,00");
                    btnStartAndStop.setText("Bắt đầu");
                }
            }
        });
    }
}