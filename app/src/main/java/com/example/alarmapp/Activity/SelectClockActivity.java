package com.example.alarmapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        addCity(1,"Amsterdam",-6,"Europe/Amsterdam");
        addCity(2,"Ankara",-4,"Europe/Istanbul");
        addCity(3,"Athens",-5,"Europe/Athens");
        addCity(4,"Băng cốc",-1,"Asia/Bangkok");
        addCity(5,"Beijing(Thượng Hải)",+1,"Asia/Shanghai");
        addCity(6,"Berlin",-6,"Europe/Berlin");
        addCity(7,"Bogota",-12,"America/Bogota");
        addCity(8,"Brasilia",-10,"America/Sao_Paulo");
        addCity(9,"Buenos Aires",-10,"America/Argentina/Buenos_Aires");
        addCity(10,"Cairo",-5,"Africa/Cairo");
        addCity(11,"Canberra",+4,"Australia/Sydney");
        addCity(12,"Cape Town",-5,"Africa/Johannesburg");
        addCity(13,"chiang Mai",+0,"Asia/Bangkok");
        addCity(14,"Copenhagen",-6,"Europe/Copenhagen");
        addCity(15,"Dubai",-3,"Asia/Dubai");
        addCity(16,"Hà nội",+0,"Asia/Ho_Chi_Minh");
        addCity(17,"Havana",-12,"America/Havana");
        addCity(18,"Helsinki",-5,"Europe/Helsinki");
        addCity(19,"Hồng Kông",+1,"Asia/Hong_Kong");
        addCity(20,"Istanbul",-4,"Europe/Istanbul");
        addCity(21,"Jakarta",+0,"Asia/Jakarta");
        addCity(22,"Jerusalem",-5,"Asia/Jerusalem");
        addCity(23,"Kuala Lumpur",+1,"Asia/Kuala_Lumpur");
        addCity(24,"Tokyo",+2,"Asia/Tokyo");
        addCity(25,"Lima",-12,"America/Lima");
        addCity(26,"Lisbon",-7,"Europe/London");
        addCity(27,"Los angles",-15,"America/Los_Angeles");
        addCity(28,"Madrid",-6,"Europe/Madrid");
        addCity(29,"Manila",+1,"Asia/Manila");
        addCity(30,"Mexico City",-13,"America/Mexico_City");
        addCity(31,"Moscow",-4,"Europe/Moscow");
        addCity(32,"Nairobi",-4,"Africa/Nairobi");
        addCity(33,"New York",-12,"America/New_York");
        addCity(34,"Oslo",-6,"Europe/Oslo");
        addCity(35,"Paris",-6,"Europe/Paris");
        addCity(36,"Prague",-6,"Europe/Prague");
        addCity(37,"Rio de Janeiro",-10,"America/Sao_Paulo");
        addCity(38,"Rome",-6,"Europe/Rome");
        addCity(39,"San Francisco",-15,"America/Los_Angeles");
        addCity(40,"Santiago",-10,"America/Santiago");
        addCity(41,"seoul",+2,"Asia/Seoul");
        addCity(42,"Singapore",+1,"Asia/Singapore");
        addCity(43,"Stockholm",-6,"Europe/Stockholm");
        addCity(44,"Sydney",+4,"Australia/Sydney");
        addCity(45,"Toronto",-12,"America/Toronto");
        return listClock;
    }
    public String calculateTime(String gmt){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(gmt));
        Date currentDate =calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setTimeZone(calendar.getTimeZone());
        Log.i("TAG_TEST",gmt+":"+calendar.getTimeZone());
        return  dateFormat.format(currentDate);
    }
    public void addCity(int id,String nameCity,int timeDifferences,String timeZone){
        listClock.add(new Clock(id,nameCity,getFormattedTime(timeDifferences),calculateTime(timeZone)));
    }
    public String getFormattedTime(int timDifferences){
        String time = calculateTime("Asia/Ho_Chi_Minh");
        int hour = Integer.parseInt(time.substring(0,2));
        if(hour+timDifferences>=24)
            return "hôm sau, "+ String.valueOf(timDifferences)+" giờ";
        else if (hour+timDifferences<=0) {
            return "hôm qua, " + String.valueOf(timDifferences)+" giờ";
        }else return "hôm nay, "+ String.valueOf(timDifferences)+" giờ";
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
                intentResult.putExtra("time",selectClock.getTimeZone());
                intentResult.putExtra("gmt",selectClock.getTimeDifferences());
                setResult(99,intentResult);
                finish();
            }
        });
    }
}