package com.example.alarmapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alarmapp.R;


public class choiceAlarmFragment extends Fragment {



    public choiceAlarmFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static choiceAlarmFragment newInstance(String param1, String param2) {
        choiceAlarmFragment fragment = new choiceAlarmFragment();
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
        return inflater.inflate(R.layout.fragment_choice_alarm, container, false);
    }
}