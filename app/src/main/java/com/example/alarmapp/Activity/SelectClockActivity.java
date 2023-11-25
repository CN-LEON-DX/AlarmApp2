package com.example.alarmapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.example.alarmapp.Adapter.SelectClockAdapter;
import com.example.alarmapp.Base.Clock;
import com.example.alarmapp.Base.NewThreadToInitRecyclerSelectClock;
import com.example.alarmapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        //initialization object
        selectClockAdapter = new SelectClockAdapter(this,createListClock());
        Log.i("tag",String.valueOf(listClock.size()));
        //set adapter for ListView
        setListenerForSearchView();
        listView.setAdapter(selectClockAdapter);
        //set event
        setListenerForBack();
        setListenerForListView();
    }
    public List<Clock> createListClock(){
        NewThreadToInitRecyclerSelectClock thread = new NewThreadToInitRecyclerSelectClock();
        thread.start();
        try {
            thread.join();
        }catch (Exception e){
            Log.e("LOG_THREAD_ERROR", Objects.requireNonNull(e.getMessage()));
        }
        listClock = new ArrayList<>(thread.list());
        return listClock;
    }

    public void setListenerForBack(){
        layoutBack.setOnClickListener(v -> finish());
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
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Clock selectClock = (Clock) listView.getItemAtPosition(position);
            Intent intentResult = new Intent();
            String city = selectClock.getCity();
            for(Clock clock:listClock){
                if(clock.getCity().equals(city)){
                    intentResult.putExtra("city",clock.getCity());
                    intentResult.putExtra("timeDifferences",clock.getTimeDifferences());
                    intentResult.putExtra("timeZone",clock.getTimeZone());
                    setResult(99,intentResult);
                    finish();
                }
            }
        });
    }
}