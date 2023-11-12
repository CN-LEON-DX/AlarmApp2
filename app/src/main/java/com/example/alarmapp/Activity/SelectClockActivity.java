package com.example.alarmapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.alarmapp.Adapter.SelectClockAdapter;
import com.example.alarmapp.Base.Clock;
import com.example.alarmapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class SelectClockActivity extends AppCompatActivity {
    private LinearLayout layoutBack;
    private SearchView searchView;
    private List<Clock> listClock;
    private ListView listView;
    private SelectClockAdapter selectClockAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_clock);
        //find id
        layoutBack=findViewById(R.id.layout_back);
        searchView=findViewById(R.id.search);
        listView = findViewById(R.id.lv_selectClock);
        //set adapter for ListView
        selectClockAdapter = new SelectClockAdapter(this,createListClock());
        setListenerForSearchView();
        listView.setAdapter(selectClockAdapter);
        //set event
        setListenerForBack();
        setListenerForListView();
    }
    public List<Clock> createListClock(){
        listClock = new ArrayList<>();
        listClock.add(new Clock("Hà Nội","hôm nay,+0 giờ",calculateTime("GMT+7")));
        listClock.add(new Clock("New York","hôm qua,-12 giờ ",calculateTime("GMT-5")));
        return listClock;
    }
    public String calculateTime(String gmt){
        Date currentDate = Calendar.getInstance().getTime();

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        dateFormat.format(TimeZone.getTimeZone(gmt));
        return String.valueOf(dateFormat.format(currentDate));
    }
    public void setListenerForBack(){
        layoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void setListenerForSearchView(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                selectClockAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
    public void setListenerForListView(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Clock selectClock = (Clock) listView.getItemAtPosition(position);
                Intent intentResult = new Intent();
                intentResult.putExtra("city",selectClock.getCity());
                intentResult.putExtra("time",selectClock.getTime());
                intentResult.putExtra("gmt",selectClock.getGmt());
                setResult(99,intentResult);
                finish();
            }
        });
    }
}