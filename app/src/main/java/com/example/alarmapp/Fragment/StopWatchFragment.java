package com.example.alarmapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Adapter.StopWatch;
import com.example.alarmapp.Adapter.StopWatchAdapter;
import com.example.alarmapp.R;

import java.util.ArrayList;
import java.util.List;

public class StopWatchFragment extends Fragment {
    private RecyclerView rcvStopWatch;
    private List<StopWatch> stopWatchList;
    private TextView tvTimer;
    private Button btnReset, btnContinue;
    public StopWatchFragment() {
        // Required empty public constructor
    }
    public static StopWatchFragment newInstance(String param1, String param2) {
        StopWatchFragment fragment = new StopWatchFragment();
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
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        tvTimer = view.findViewById(R.id.tvTimer);
        btnReset = view.findViewById(R.id.btnReset);
        btnContinue = view.findViewById(R.id.btnContinue);

        rcvStopWatch = view.findViewById(R.id.rcvListStopWatch);
        StopWatchAdapter  stopWatchAdapter = new StopWatchAdapter(createListStopWatch(), getContext());

        rcvStopWatch.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvStopWatch.setAdapter(stopWatchAdapter);
        return view;
    }

    public List<StopWatch> createListStopWatch(){
        stopWatchList = new ArrayList<>();
        stopWatchList.add(new StopWatch("01", "19:34,09", "19:34,09"));
        stopWatchList.add(new StopWatch("02", "19:40,09", "00:06,09"));
        stopWatchList.add(new StopWatch("03", "20:44,09", "01:04,00"));
        stopWatchList.add(new StopWatch("04", "21:34,09", "00:54,00"));
        stopWatchList.add(new StopWatch("05", "25:14,09", "04:40,00"));
        stopWatchList.add(new StopWatch("06", "27:14,09", "06:40,00"));
        stopWatchList.add(new StopWatch("07", "27:16,09", "00:02,00"));
        stopWatchList.add(new StopWatch("08", "28:14,09", "01:40,00"));
        return stopWatchList;
    }
}