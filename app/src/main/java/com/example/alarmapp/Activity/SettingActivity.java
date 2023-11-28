package com.example.alarmapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.alarmapp.R;

public class SettingActivity extends AppCompatActivity {
    private RelativeLayout layoutTimeRing, layoutTimeRepeat, layoutNumberOfIteration, layout_back;
    private SwitchCompat switchNotify,switchFormatClock;
    private TextView tv_timeRing, tv_timeRepeat,tv_NumberOfIteration;
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
}