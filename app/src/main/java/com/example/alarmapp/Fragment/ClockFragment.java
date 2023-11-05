package com.example.alarmapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Model.Activity.SelectClockActivity;
import com.example.alarmapp.Model.Adapter.Clock_Recycler_Adapter;
import com.example.alarmapp.Model.item.Clock;
import com.example.alarmapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
public class ClockFragment extends Fragment{
    private RecyclerView recyclerView_Clock;
    private List<Clock> clockList;
    private FloatingActionButton fabAdd_Clock;
    private Clock_Recycler_Adapter adapter_Clock;

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
        recyclerView_Clock = view.findViewById(R.id.rcvList_Clock);
        fabAdd_Clock = view.findViewById(R.id.fabAddClock);
        Clock_Recycler_Adapter clockRecyclerAdapter = new Clock_Recycler_Adapter(getContext(), createListLock());
        recyclerView_Clock.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView_Clock.setAdapter(clockRecyclerAdapter);
        //set event
        setListenerForFabButton();
        return view;
    }

    // Cần xử lý khi thêm các danh sách đòng hồ
    public List<Clock> createListLock() {
        clockList = new ArrayList<>();
        clockList.add(new Clock("New York", "gmt+7","14:25"));
        clockList.add(new Clock("New York", "gmt+7","14:25"));
        clockList.add(new Clock("New York", "gmt+7","14:25"));
        clockList.add(new Clock("New York", "gmt+7","14:25"));
        clockList.add(new Clock("New York", "gmt+7","14:25"));
        clockList.add(new Clock("New York", "gmt+7","14:25"));
        clockList.add(new Clock("New York", "gmt+7","14:25"));
        clockList.add(new Clock("New York", "gmt+7","14:25"));
        return clockList;
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
}
