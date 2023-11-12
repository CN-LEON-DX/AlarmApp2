package com.example.alarmapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Activity.SelectClockActivity;
import com.example.alarmapp.Adapter.Clock_Recycler_Adapter;
import com.example.alarmapp.Base.Clock;
import com.example.alarmapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ClockFragment extends Fragment{
    private RecyclerView recyclerView_Clock;
    private List<Clock> clockList;
    private FloatingActionButton fabAdd_Clock;
    private TextView tvDate;
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
        recyclerView_Clock = view.findViewById(R.id.rcvList_Clock);
        fabAdd_Clock = view.findViewById(R.id.fabAddClock);
        tvDate =view.findViewById(R.id.tv_date);

        //set Adapter
        clockList = new ArrayList<>();
        clockRecyclerAdapter = new Clock_Recycler_Adapter(getContext(), clockList);
        recyclerView_Clock.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView_Clock.setAdapter(clockRecyclerAdapter);
        //set event
        setListenerForFabButton();
        //set text tvDate
        setDataForTvDate();
        TextClock textClock = view.findViewById(R.id.textClock);
        Log.i("Tag_Test",String.valueOf(textClock.getText()));
        return view;
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
            clock.setTime(data.getStringExtra("time"));
            clock.setGmt(data.getStringExtra("gmt"));
            clockRecyclerAdapter.addClock(clock);
        }
    }
    public void setDataForTvDate(){
        Date currentDate = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy");
        String date =dateFormat.format(currentDate);
        tvDate.setText(date);
    }
}
