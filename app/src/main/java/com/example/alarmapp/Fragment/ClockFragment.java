package com.example.alarmapp.Fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Activity.DeleteActivity;
import com.example.alarmapp.Activity.SelectClockActivity;
import com.example.alarmapp.Adapter.ClockAdapter;
import com.example.alarmapp.Base.Clock;

import com.example.alarmapp.Base.SwipeToDeleteCLock;
import com.example.alarmapp.Database.WatchTimeCityDatabase;
import com.example.alarmapp.Interface.OnItemClockClickListener;
import com.example.alarmapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ClockFragment extends Fragment implements OnItemClockClickListener {

    private List<Clock> clockList;
    private FloatingActionButton fabAdd_Clock;
    private TextView tvDate;
    private WatchTimeCityDatabase database;
    private ClockAdapter clockAdapter;
    private RecyclerView recyclerView_Clock;
    private TextClock textClock;
    private SharedPreferences sharedPreferences;
    private static final int REQUEST_CODE_SELECT_NEW_CLOCK = 99;
    private static final int RESULT_CODE_SELECT_NEW_CLOCK = 99;
    private static final int REQUEST_CODE_DELETE_MANY_CLOCK = 98;
    private static final int RESULT_CODE_DELETE_MANY_CLOCK = 98;
    public ClockFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences = Objects.requireNonNull(requireContext()).getSharedPreferences("app",Context.MODE_PRIVATE);
        boolean isFormat = sharedPreferences.getBoolean("isFormatClock",false);
        if (isFormat) textClock.setFormat24Hour("HH:mm:ss");
        else textClock.setFormat24Hour("HH:mm");
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
        textClock= view.findViewById(R.id.textClock);
        //initialize object
        clockList = new ArrayList<>();
        database= new WatchTimeCityDatabase(getContext());
        //set Adapter
        clockAdapter= new ClockAdapter(clockList,getContext(),this);
        recyclerView_Clock.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView_Clock.setAdapter(clockAdapter);
        //set event
        setListenerForFabButton();
        //set text tvDate
        setDataForTvDate();
        initRecyclerViewWhenStart();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCLock(database,this,clockAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView_Clock);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        sharedPreferences = Objects.requireNonNull(requireContext()).getSharedPreferences("sharedPrefsClock",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putBoolean("isFormat",);
        editor.apply();
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
        }
    }
    public void setListenerForFabButton(){
        fabAdd_Clock.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SelectClockActivity.class);
            startActivityForResult(intent,99);
        });
    }
    private boolean isItemExists(String city){
        for(Clock clockFromList : clockList){
            if (clockFromList.getCity().equals(city)) {
                return  true;
            }
        }
        return false;
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_SELECT_NEW_CLOCK&&resultCode==RESULT_CODE_SELECT_NEW_CLOCK&&data!=null){
            addNewClock(data);
        }
        if(requestCode == REQUEST_CODE_DELETE_MANY_CLOCK&&resultCode==RESULT_CODE_DELETE_MANY_CLOCK&&data!=null){
            clockList.clear();
            ArrayList<Clock> resultList=data.getParcelableArrayListExtra("resultListClock");
            if(resultList!=null) clockList.addAll(resultList);
            clockAdapter.notifyDataSetChanged();
        }

    }
    private void addNewClock(Intent data){
        Clock clock = new Clock();
        String city=data.getStringExtra("city");
        String timeDifferences=data.getStringExtra("timeDifferences");
        String timeZone = data.getStringExtra("timeZone");
        boolean isSelected =isItemExists(city);

        if(!isSelected){
            database.putData(new Clock(city,timeDifferences,timeZone));
            clock.setCity(city);
            if(timeDifferences!=null)
                clock.setTimeDifferences(clock.getFormattedTime(Integer.parseInt(timeDifferences)));
            clock.setTimeZone(timeZone);
            clockList.add(0,clock);
            clockAdapter.notifyItemInserted(0);
        }else Toast.makeText(getContext(),R.string.notifyUserSelectedThisCity,Toast.LENGTH_LONG).show();
    }
    public void setDataForTvDate(){
        Date currentDate = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy");
        String date =dateFormat.format(currentDate);
        tvDate.setText(date);
    }
    @Override
    public void setOnLongClickItemClockListener(int position) {
        for(Clock clock:clockList) clock.setChecked(false);
        Clock clock = clockList.get(position);
        clock.setChecked(true);
        Intent intent = new Intent(getContext(), DeleteActivity.class);
        intent.putExtra("requestCode",REQUEST_CODE_DELETE_MANY_CLOCK);
        intent.putExtra("listClock",new ArrayList<>(clockList));
        startActivityForResult(intent,REQUEST_CODE_DELETE_MANY_CLOCK);
    }
}
