package com.example.alarmapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        //set adapter for ListView
        setListenerForSearchView();
        listView.setAdapter(selectClockAdapter);
        //set event
        setListenerForBack();
        setListenerForListView();
    }
    public List<Clock> createListClock(){
        listClock = new ArrayList<>();
        addCity("Amsterdam",-6,"Europe/Amsterdam");
        addCity("Ankara",-4,"Europe/Istanbul");
        addCity("Athens",-5,"Europe/Athens");
        addCity("Berlin",-6,"Europe/Berlin");
        addCity("Bogota",-12,"America/Bogota");
        addCity("Brasilia",-10,"America/Sao_Paulo");
        addCity("Buenos Aires",-10,"America/Argentina/Buenos_Aires");
        addCity("Cairo",-5,"Africa/Cairo");
        addCity("Canberra",+4,"Australia/Sydney");
        addCity("Cape Town",-5,"Africa/Johannesburg");
        addCity("chiang Mai",+0,"Asia/Bangkok");
        addCity("Copenhagen",-6,"Europe/Copenhagen");
        addCity("Dubai",-3,"Asia/Dubai");
        addCity("Hà nội",+0,"Asia/Ho_Chi_Minh");
        addCity("Havana",-12,"America/Havana");
        addCity("Helsinki",-5,"Europe/Helsinki");
        addCity("Hồng Kông",+1,"Asia/Hong_Kong");
        addCity("Istanbul",-4,"Europe/Istanbul");
        addCity("Jakarta",+0,"Asia/Jakarta");
        addCity("Jerusalem",-5,"Asia/Jerusalem");
        addCity("Kuala Lumpur",+1,"Asia/Kuala_Lumpur");
        addCity("Tokyo",+2,"Asia/Tokyo");
        addCity("Lima",-12,"America/Lima");
        addCity("Lisbon",-7,"Europe/London");
        addCity("Los angles",-15,"America/Los_Angeles");
        addCity("Madrid",-6,"Europe/Madrid");
        addCity("Manila",+1,"Asia/Manila");
        addCity("Mexico City",-13,"America/Mexico_City");
        addCity("Moscow",-4,"Europe/Moscow");
        addCity("Nairobi",-4,"Africa/Nairobi");
        addCity("New York",-12,"America/New_York");
        addCity("Oslo",-6,"Europe/Oslo");
        addCity("Paris",-6,"Europe/Paris");
        addCity("Prague",-6,"Europe/Prague");
        addCity("Rio de Janeiro",-10,"America/Sao_Paulo");
        addCity("Rome",-6,"Europe/Rome");
        addCity("San Francisco",-15,"America/Los_Angeles");
        addCity("Santiago",-10,"America/Santiago");
        addCity("seoul",+2,"Asia/Seoul");
        addCity("Singapore",+1,"Asia/Singapore");
        addCity("Stockholm",-6,"Europe/Stockholm");
        addCity("Sydney",+4,"Australia/Sydney");
        addCity("Toronto",-12,"America/Toronto");
        addCity("Thượng Hải",+1,"Asia/Shanghai");
        return listClock;
    }

    public void addCity(String nameCity,int timeDifferences,String timeZone){
        Clock clock = new Clock();
        clock.setCity(nameCity);
        clock.setTimeDifferences(clock.getFormattedTime(timeDifferences));
        clock.setTimeZone(clock.calculateTime(timeZone));
        listClock.add(clock);
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
            }
        });
    }
}