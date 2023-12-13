package com.example.alarmapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.alarmapp.Fragment.ClockFragment;
import com.example.alarmapp.R;

public class SettingActivity extends AppCompatActivity {
    private RelativeLayout layoutTimeRing, layoutTimeRepeat, layoutNumberOfIteration, layout_back;
    private SwitchCompat switchNotify,switchFormatClock;
    private TextView tv_timeRing, tv_timeRepeat,tv_NumberOfIteration;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //find id
        layoutTimeRing = findViewById(R.id.layout_timeRing);
        layoutTimeRepeat=findViewById(R.id.layout_timeRepeat);
        layoutNumberOfIteration = findViewById(R.id.layout_numberOfIteration);
        layout_back=findViewById(R.id.layout_back_alarm);
        switchNotify = findViewById(R.id.switchNotify);
        switchFormatClock = findViewById(R.id.switchFormatClock);
        tv_timeRing=findViewById(R.id.tv_timeRing);
        tv_timeRepeat=findViewById(R.id.tv_TimeRepeat);
        tv_NumberOfIteration=findViewById(R.id.tv_numberOfIteration);
        //set event for layout back
        setEventForLayoutBack();
        //set event for textView time ring
        setEventForLayoutTimeRing();
        //
        setEventForLayoutRepeat();
        //
        setEventForLayoutNumberOfIteration();
        //
        readDataFromSharedPreferences();
        //
        setEventSwitchFormatClock();
    }
    private void readDataFromSharedPreferences(){
        sharedPreferences = getSharedPreferences("sharedPrefsSetting", Context.MODE_PRIVATE);
        tv_timeRepeat.setText(sharedPreferences.getString("timeRepeat","3 phút"));
        tv_timeRing.setText(sharedPreferences.getString("timeRing","5 phút"));
        tv_NumberOfIteration.setText(sharedPreferences.getString("number","3 lần"));
        switchNotify.setChecked(sharedPreferences.getBoolean("notifySwitch",false));
        switchFormatClock.setChecked(sharedPreferences.getBoolean("formatSwitch",false));
    }

    @Override
    protected void onStop() {
        super.onStop();
        sharedPreferences = getSharedPreferences("sharedPrefsSetting",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("timeRepeat",tv_timeRepeat.getText().toString());
        editor.putString("timeRing",tv_timeRing.getText().toString());
        editor.putString("number",tv_NumberOfIteration.getText().toString());
        editor.putBoolean("notifySwitch",switchNotify.isChecked());
        editor.putBoolean("formatSwitch",switchFormatClock.isChecked());
        editor.apply();
    }

    private void setEventForLayoutBack(){
        layout_back.setOnClickListener(v->{
            finish();
        });
    }
    private void setEventForLayoutTimeRing(){
        layoutTimeRing.setOnClickListener(v -> {
            String[] listItem = {"1 phút","2 phút","3 phút","4 phút","5 phút","6 phút","7 phút","8 phút","9 phút","10 phút"};
            showAlertDialog(listItem,"Thời gian đổ chuông",tv_timeRing);
        });
    }
    private void setEventForLayoutRepeat(){
        layoutTimeRepeat.setOnClickListener(v->{
            String[] listItem ={"1 phút","2 phút","3 phút","4 phút","5 phút","6 phút","7 phút","8 phút","9 phút","10 phút"};
            showAlertDialog(listItem,"Lặp lại sau",tv_timeRepeat);
        });
    }
    private void setEventForLayoutNumberOfIteration(){
        layoutNumberOfIteration.setOnClickListener(v -> {
            String[] listItem = {"1 lần","2 lần","3 lần","4 lần","5 lần"};
            showAlertDialog(listItem,"Số lần lặp",tv_NumberOfIteration);
        });
    }
    private void showAlertDialog(String[] listItem,String title,TextView textView){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listItem);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setAdapter(adapter, (dialog, which) -> {
            String selectItem= listItem[which];
            textView.setText(selectItem);
        });
        builder.setPositiveButton("Hủy", (dialog, which) -> {dialog.cancel();});
        builder.show();
    }
    private void setEventSwitchFormatClock(){
        switchFormatClock.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Intent intent = new Intent("com.example.ACTION_SEND_DATA");
            intent.putExtra("isFormat", switchFormatClock.isChecked());
            Log.i("Tag_Format_clock",String.valueOf(isChecked));
            sendBroadcast(intent);
        });
    }
}