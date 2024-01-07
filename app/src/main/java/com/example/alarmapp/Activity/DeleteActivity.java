package com.example.alarmapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alarmapp.Adapter.DeleteManyAlarmAdapter;
import com.example.alarmapp.Adapter.DeleteManyClockAdapter;
import com.example.alarmapp.Base.Alarm;
import com.example.alarmapp.Base.Clock;
import com.example.alarmapp.Interface.OnItemRclVDeleteClickListener;
import com.example.alarmapp.R;

import java.util.ArrayList;
import java.util.List;

public class DeleteActivity extends AppCompatActivity implements OnItemRclVDeleteClickListener {
    private DeleteManyAlarmAdapter adapterAlarm;
    private DeleteManyClockAdapter adapterClock;
    private RecyclerView recyclerView;
    private List<Alarm> alarmList;
    private List<Clock> clockList;
    private TextView tv_title;
    private TextView tv_delete;
    private ImageView imageView;
    private CheckBox checkBox;
    private RelativeLayout layoutDelete;
    private static final int REQUEST_CODE_FROM_ALARM_FRAGMENT=97;
    private static final int RESULT_CODE_TO_ALARM_FRAGMENT = 97;
    private static final int REQUEST_CODE_FROM_CLOCK_FRAGMENT=98;
    private static final int RESULT_CODE_TO_CLOCK_FRAGMENT=98;
    private int requestCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        //find id
        recyclerView=findViewById(R.id.rcv_listItem);
        tv_title = findViewById(R.id.tv_titleOfDeleteAcitvity);
        imageView = findViewById(R.id.img_back);
        tv_delete = findViewById(R.id.tv_delete);
        checkBox = findViewById(R.id.checkBox_setAll);
        layoutDelete = findViewById(R.id.layout_delete);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        setEvents();

    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void setEvents(){
        Intent intent = getIntent();
        requestCode =intent.getIntExtra("requestCode",-1);
        if(requestCode == REQUEST_CODE_FROM_ALARM_FRAGMENT){
            tv_title.setText("Xóa báo thức");
            setAdapterForAlarm(intent);
            setEventCheckBoxAlarm();
        }
        if(requestCode == REQUEST_CODE_FROM_CLOCK_FRAGMENT){
            tv_title.setText("Xóa đồng hồ");
            setAdapterClock(intent);
            setEventCheckBoxClock();
        }
        setEventIgmBack();
        setEventDelete();
    }
    private void setAdapterForAlarm(Intent intent){
        alarmList = intent.getParcelableArrayListExtra("listAlarm");
        adapterAlarm = new DeleteManyAlarmAdapter(DeleteActivity.this,alarmList,this);
        recyclerView.setAdapter(adapterAlarm);
    }
    private  void setAdapterClock(Intent intent){
        clockList = intent.getParcelableArrayListExtra("listClock");
        adapterClock = new DeleteManyClockAdapter(DeleteActivity.this,clockList,this);
        recyclerView.setAdapter(adapterClock);
    }
    private void setEventIgmBack(){
        imageView.setOnClickListener(v -> {
            if(alarmList != null) setCheckAllFalseAlarm();
            if(clockList != null) setCheckAllFalseClock();
            finish();
        });
    }
    private void setCheckAllTrueAlarm(){
        for(Alarm alarm:alarmList) alarm.setChecked(true);
    }
    private void setCheckAllFalseAlarm(){
        for(Alarm alarm:alarmList) {
            Log.i("tagcheck1 "+alarm.getId(),""+alarm.isChecked());
            alarm.setChecked(false);
            Log.i("tagcheck2 "+alarm.getId(),""+alarm.isChecked());
        }
    }
    private void setCheckAllTrueClock(){
        for(Clock clock:clockList) clock.setChecked(true);
    }
    private void setCheckAllFalseClock(){
        for(Clock clock:clockList) clock.setChecked(false);
    }
    private boolean isHasClockSelected(){
        for(Clock clock : clockList){
            if(clock.isChecked()) return true;
        }
        return false;
    }
    private boolean isHasAlarmSelected(){
        for(Alarm alarm : alarmList)
            if(alarm.isChecked()) return true;
        return false;
    }
    @SuppressLint("NotifyDataSetChanged")
    private void setEventCheckBoxAlarm(){
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                setCheckAllTrueAlarm();
                adapterAlarm.notifyDataSetChanged();
                tv_delete.setTextColor(ContextCompat.getColor(DeleteActivity.this,R.color.white));
            }
            else {
                setCheckAllFalseAlarm();
                adapterAlarm.notifyDataSetChanged();
                tv_delete.setTextColor(ContextCompat.getColor(DeleteActivity.this,R.color.grey));
            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    private void setEventCheckBoxClock(){
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                setCheckAllTrueClock();
                adapterClock.notifyDataSetChanged();
                tv_delete.setTextColor(ContextCompat.getColor(DeleteActivity.this,R.color.white));
            }
            else {
                setCheckAllFalseClock();
                adapterClock.notifyDataSetChanged();
                tv_delete.setTextColor(ContextCompat.getColor(DeleteActivity.this,R.color.grey));
            }
        });
    }
    private void setEventChangeColorTextViewDelete(int requestCode){
        if(requestCode == REQUEST_CODE_FROM_ALARM_FRAGMENT){
            if (isHasAlarmSelected()) tv_delete.setTextColor(ContextCompat.getColor(DeleteActivity.this,R.color.white));
            else tv_delete.setTextColor(ContextCompat.getColor(DeleteActivity.this,R.color.grey));
        }
        if(requestCode == REQUEST_CODE_FROM_CLOCK_FRAGMENT){
            if(isHasClockSelected()) tv_delete.setTextColor(ContextCompat.getColor(DeleteActivity.this,R.color.white));
            else tv_delete.setTextColor(ContextCompat.getColor(DeleteActivity.this,R.color.grey));
        }
    }
    private View.OnClickListener setEventDelete(){
        View.OnClickListener onClickListener = v -> showDialogDelete();
        return onClickListener;
    }
    private void sendResultAlarmFragment(){
        Intent intent = new Intent();
        intent.putExtra("resultListAlarm",new ArrayList<>(alarmList));
        setResult(RESULT_CODE_TO_ALARM_FRAGMENT,intent);
    }
    private void sendResultClockFragment(){
        Intent intent = new Intent();
        intent.putExtra("resultListClock",new ArrayList<>(clockList));
        setResult(RESULT_CODE_TO_CLOCK_FRAGMENT,intent);
    }
    private void showDialogDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteActivity.this);
        builder.setTitle(R.string.titleAlertDialog);
        builder.setMessage(R.string.messageDeleteItem);
        builder.setPositiveButton(R.string.No,(dialog, which) -> {
            dialog.cancel();
        });
        builder.setNegativeButton(R.string.accept,(dialog, which) -> {
            if(requestCode==REQUEST_CODE_FROM_ALARM_FRAGMENT){
                alarmList.removeIf(Alarm::isChecked);
                sendResultAlarmFragment();
            }
            if(requestCode == REQUEST_CODE_FROM_CLOCK_FRAGMENT){
                clockList.removeIf(Clock::isChecked);
                sendResultClockFragment();
            }
            dialog.cancel();
            finish();
        });
        builder.setOnCancelListener(DialogInterface::cancel);
        builder.show();
    }
    @Override
    public void setOnClickAlarmListener(int position) {
        if(alarmList!=null) {
            Alarm alarm = alarmList.get(position);
            alarm.setChecked(!alarm.isChecked());
            adapterAlarm.notifyItemChanged(position);
            setEventChangeColorTextViewDelete(requestCode);
            if(isHasAlarmSelected()) layoutDelete.setOnClickListener(setEventDelete());
            else layoutDelete.setOnClickListener(null);
        }
    }

    @Override
    public void setOnClickClockListener(int position) {
        if(clockList!=null) {
            Clock clock = clockList.get(position);
            clock.setChecked(!clock.isChecked());
            adapterClock.notifyItemChanged(position);
            setEventChangeColorTextViewDelete(requestCode);
            if(isHasClockSelected()) layoutDelete.setOnClickListener(setEventDelete());
            else layoutDelete.setOnClickListener(null);
        }
    }

}