package com.example.alarmapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.alarmapp.Adapter.SelectClockAdapter;
import com.example.alarmapp.Base.Clock;
import com.example.alarmapp.R;

import java.util.ArrayList;
import java.util.List;

public class SelectClockActivity extends AppCompatActivity {
    private LinearLayout layoutBack;
    private SearchView searchView;
    private List<Clock> listClock;
    private SelectClockAdapter selectClockAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_clock);
        //find id
        layoutBack=findViewById(R.id.layout_back);
        searchView=findViewById(R.id.search);
        ListView listView = findViewById(R.id.lv_selectClock);
        //set adapter for ListView
        selectClockAdapter = new SelectClockAdapter(this,createListClock());
        setListenerForSearchView();
        listView.setAdapter(selectClockAdapter);
        //set event
        setListenerForBack();
    }
    public List<Clock> createListClock(){
        listClock = new ArrayList<>();
        listClock .add(new Clock("New York","14:25"));
        listClock .add(new Clock("Hà Nội","14:25"));
        listClock .add(new Clock("Trung Quốc","14:25"));
        listClock .add(new Clock("nhật bản","14:25"));
        listClock .add(new Clock("Hàn Quốc","14:25"));
        listClock .add(new Clock("united state","14:25"));
        listClock .add(new Clock("paris","14:25"));
        listClock .add(new Clock("lào","14:25"));
        return listClock;
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

}