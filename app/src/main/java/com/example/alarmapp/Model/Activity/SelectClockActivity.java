package com.example.alarmapp.Model.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.alarmapp.Fragment.ClockFragment;
import com.example.alarmapp.Model.Adapter.SelectClockAdapter;
import com.example.alarmapp.Model.item.Clock;
import com.example.alarmapp.R;

import java.util.ArrayList;
import java.util.List;

public class SelectClockActivity extends AppCompatActivity {
    private LinearLayout layout;
    private SearchView searchView;
    private List<Clock> listClock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_clock);
        //find id
        layout=findViewById(R.id.layout_back);
        searchView=findViewById(R.id.search);
        RecyclerView recyclerView = findViewById(R.id.rcv_selectClock);
        //set adapter for recyclerView
        SelectClockAdapter selectClockAdapter = new SelectClockAdapter(createListClock(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(selectClockAdapter);
        //set event
        setListenerForBack();
    }
    public List<Clock> createListClock(){
        listClock = new ArrayList<>();
        listClock .add(new Clock("New York", "gmt+7","14:25"));
        listClock .add(new Clock("New York", "gmt+7","14:25"));
        listClock .add(new Clock("New York", "gmt+7","14:25"));
        listClock .add(new Clock("New York", "gmt+7","14:25"));
        listClock .add(new Clock("New York", "gmt+7","14:25"));
        listClock .add(new Clock("New York", "gmt+7","14:25"));
        listClock .add(new Clock("New York", "gmt+7","14:25"));
        listClock .add(new Clock("New York", "gmt+7","14:25"));
        return listClock;
    }
    public void setListenerForBack(){
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}