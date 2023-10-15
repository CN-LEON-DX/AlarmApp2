package com.example.alarmapp.clock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.alarmapp.R;

import java.util.ArrayList;
import java.util.List;

public class SelectClock_Activity extends AppCompatActivity {
    private RecyclerView recyclerViewclock;
    private List<SelectClock> clockList;
    private SelectClockAdapterRecycler selectClockAdapterRecycler;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_clock);
        recyclerViewclock=(RecyclerView) findViewById(R.id.rcv_country);
        selectClockAdapterRecycler = new SelectClockAdapterRecycler(creatClockList(),this);
        recyclerViewclock.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerViewclock.setAdapter(selectClockAdapterRecycler);

    }
    private List<SelectClock> creatClockList(){
        clockList = new ArrayList<>();
        clockList.add(new SelectClock("Hà Nội","15","30","gmt+7:00"));
        clockList.add(new SelectClock("Hà Nội","15","30","gmt+7:00"));
        clockList.add(new SelectClock("Hà Nội","15","30","gmt+7:00"));
        clockList.add(new SelectClock("Hà Nội","15","30","gmt+7:00"));
        clockList.add(new SelectClock("Hà Nội","15","30","gmt+7:00"));
        clockList.add(new SelectClock("Hà Nội","15","30","gmt+7:00"));
        clockList.add(new SelectClock("Hà Nội","15","30","gmt+7:00"));
        clockList.add(new SelectClock("Hà Nội","15","30","gmt+7:00"));
        clockList.add(new SelectClock("Hà Nội","15","30","gmt+7:00"));
        clockList.add(new SelectClock("Hà Nội","15","30","gmt+7:00"));
        clockList.add(new SelectClock("Hà Nội","15","30","gmt+7:00"));
        return clockList;
    }
    private void goback(){
        back =(ImageView) findViewById(R.id.imgv_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectClock_Activity.this, MainActivity_clock.class);
                startActivity(intent);
                finish();
            }
        });
    }
}