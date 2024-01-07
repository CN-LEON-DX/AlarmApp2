package com.example.alarmapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alarmapp.Fragment.AlarmFragment;
import com.example.alarmapp.R;

public class CreateNewAlarmActivity extends AppCompatActivity {
    private EditText edtHour, edtMinute;
    private RelativeLayout layoutNameAlarm,layoutRepeat,layoutSound;
    private TextView tvNameAlarm,tvRepeat,tvSound;
    private ImageView imageViewBack, imgSave;
    private SwitchCompat switchVibrate,switchRepeat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_alarm);
        //find id
        edtMinute = findViewById(R.id.tvMinute);
        edtHour = findViewById(R.id.tvHour);
        layoutNameAlarm = findViewById(R.id.layout_nameAlarm);
        layoutRepeat = findViewById(R.id.layout_repeat);
        layoutSound = findViewById(R.id.layout_selectSound);
        tvRepeat = findViewById(R.id.tvRepeat_itemAlarm);
        tvNameAlarm=findViewById(R.id.tv_nameAlarm);
        tvSound=findViewById(R.id.tv_sound);
        imageViewBack= findViewById(R.id.img_cancel_activity);
        imgSave = findViewById(R.id.img_saveNewAlarm);
        switchVibrate=findViewById(R.id.switch_vibrate);
        switchRepeat = findViewById(R.id.switch_repeat);

        Intent intent = getIntent();
        int requestCode=intent.getIntExtra("requestCode",-1);
        updateUIWhenRequestCode98(requestCode,intent);
        //
        setEventForLayoutNameAlarm(requestCode,intent);
        //
        setEventLayoutRepeat(requestCode,intent);
        //
        setEventForLayoutSelectSound(requestCode,intent);
        //
        backLayout();
        save(requestCode);
        deniedEnterEditText(edtHour);
        deniedEnterEditText(edtMinute);
    }
    private void updateUIWhenRequestCode98(int requestCode,Intent intent){
        if (requestCode==98){
            String[] time = intent.getStringExtra("time").split(":");
            edtHour.setText(String.valueOf(time[0]));
            edtMinute.setText(String.valueOf(time[1]));
            switchRepeat.setChecked(intent.getBooleanExtra("isRepeat",false));
            switchVibrate.setChecked(intent.getBooleanExtra("isVibrate",false));
        }
    }
    //set event name Alarm
    private void setEventForLayoutNameAlarm(int requestCode,Intent intent){
        final EditText input = new EditText(this);
        deniedEnterEditText(input);
        if(requestCode==98) {
            String nameAlarm = intent.getStringExtra("nameAlarm");
            tvNameAlarm.setText(nameAlarm);
        }
        layoutNameAlarm.setOnClickListener(v -> {
            showDialogInputNameAlarm(input);
        });
    }
    private void showDialogInputNameAlarm(@NonNull EditText input){
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
        builder.setTitle("Tên báo thức");
        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) -> {
            String userInput = input.getText().toString();
            if(userInput.isEmpty()) userInput="Báo thức";
            tvNameAlarm.setText(userInput);
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());
        builder.setOnCancelListener(dialog -> {
            input.setText("");
            ((ViewGroup) input.getParent()).removeView(input);

        });
        builder.show();
    }
    private void checkRepeat(String day,boolean[] checkedItem){
        switch (day){
            case "thứ 2":
                checkedItem[0]=true;
                break;
            case "thứ 3":
                checkedItem[1]=true;
                break;
            case "thứ 4":
                checkedItem[2]=true;
                break;
            case "thứ 5":
                checkedItem[3]=true;
                break;
            case "thứ 6":
                checkedItem[4]=true;
                break;
            case "thứ 7":
                checkedItem[5]=true;
                break;
            case "chủ nhật":
                checkedItem[6]=true;
                break;
            case "mỗi ngày":
                checkedItem[0]=true;
                checkedItem[1]=true;
                checkedItem[2]=true;
                checkedItem[3]=true;
                checkedItem[4]=true;
                checkedItem[5]=true;
                checkedItem[6]=true;
                break;
        }
    }
    private boolean[] initCheckedItem(int requestCode,Intent intent){
        boolean[] checkedItems = new boolean[]{false, false, false, false, false, false, false};
        if(requestCode==98){
            String repeatAlarm = intent.getStringExtra("repeat");
            tvRepeat.setText(repeatAlarm);
            String[] listRepeatItem = repeatAlarm.split(", ");
            for (String s : listRepeatItem) {
                checkRepeat(s, checkedItems);
            }
        }
        return checkedItems;
    }
    private void setEventLayoutRepeat(int requestCode,Intent intent){
        boolean[] checkedItems = initCheckedItem(requestCode,intent);
        layoutRepeat.setOnClickListener(v->{
            showDialogRepeat(checkedItems);
        });
    }
    private void showDialogRepeat(boolean[] checkedItems) {
        final StringBuilder result = new StringBuilder();
        String[] options = {"thứ 2", "thứ 3", "thứ 4", "thứ 5", "thứ 6", "thứ 7", "chủ nhật"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lặp lại");
        builder.setMultiChoiceItems(options, checkedItems, (dialog, which, isChecked) -> {
            checkedItems[which] = isChecked;
        });

        builder.setPositiveButton("OK", (dialog, which) -> {
            result.setLength(0);
            for (int i = 0; i < checkedItems.length; i++)
                if (checkedItems[i])
                    result.append(options[i]).append(", ");

            if(isAllItemTrue(checkedItems)) {
                tvRepeat.setText("mỗi ngày");
                return;
            }

            if (result.length() > 0) {
                tvRepeat.setText(result.substring(0, result.length() - 2));
            } else {
                tvRepeat.setText("chỉ một lần");
            }
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());

        builder.show();
    }
    private boolean isAllItemTrue(boolean[] checkedItem){
        for (boolean b : checkedItem) {
            if (!b) return false;
        }
        return true;
    }
    private void setEventForLayoutSelectSound(int requestCode,Intent intent){
        if(requestCode==98){
            tvSound.setText(intent.getStringExtra("sound"));
        }
        layoutSound.setOnClickListener(v -> {
            showDialogSelectSound();
        });
    }
    private void showDialogSelectSound(){
        String[] options={"mặc định","nhạc mặc định của điện thoại samsung","nhạc gà kêu"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,options);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nhạc báo thức");
        builder.setAdapter(adapter, (dialog, which) -> {
            String selectItem= options[which];
            tvSound.setText(selectItem);
        });
        builder.setPositiveButton("Hủy", (dialog, which) -> {dialog.cancel();});
        builder.show();

    }
    void backLayout(){
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void save(int requestCode) {
        imgSave.setOnClickListener(v -> {
            String hour = edtHour.getText().toString();
            String minute = edtMinute.getText().toString();

            int intHour = parseValue(hour);
            int intMinute = parseValue(minute);

            if (!isValidTime(intHour, intMinute)) {
                Toast.makeText(CreateNewAlarmActivity.this, "Hãy nhập đúng giờ phút !", Toast.LENGTH_SHORT).show();
                return;
            }

            String formattedTime = formatTime(intHour, intMinute);
            if(requestCode==99) result( formattedTime,99);
            else if(requestCode==98) result( formattedTime,98);
        });
    }

    private int parseValue(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private boolean isValidTime(int hour, int minute) {
        return hour >= 0 && hour < 24 && minute >= 0 && minute < 60;
    }

    private String formatTime(int hour, int minute) {
        return String.format("%02d:%02d", hour, minute);
    }

    private void result( String time,int resultCode) {
        Intent intentChange = getIntent();
        Intent intent = new Intent(CreateNewAlarmActivity.this, AlarmFragment.class);
        if(resultCode==98) intent.putExtra("position",intentChange.getIntExtra("position",-1));
        intent.putExtra("soundCreate",tvSound.getText());
        intent.putExtra("isRepeatCreate",switchRepeat.isChecked());
        intent.putExtra("isVibrateCreate",switchVibrate.isChecked());
        intent.putExtra("RepeatCreate",tvRepeat.getText());
        intent.putExtra("nameAlarmCreate", tvNameAlarm.getText());
        intent.putExtra("timeCreate", time);
        setResult(resultCode, intent);
        finish();
    }
    private void deniedEnterEditText(TextView textView){
        textView.setSingleLine(true);
        textView.setOnKeyListener((v, keyCode, event) -> keyCode==KeyEvent.KEYCODE_ENTER);
    }
}