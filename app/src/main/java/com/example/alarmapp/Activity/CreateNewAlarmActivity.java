package com.example.alarmapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alarmapp.Fragment.AlarmFragment;
import com.example.alarmapp.R;

public class CreateNewAlarmActivity extends AppCompatActivity {
    private EditText edtHour, edtMinute;
    private RelativeLayout layoutNameAlarm,layoutrepeat,layoutSound;
    private TextView tvNameAlarm,tvrepeat,tvSound;
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
        layoutrepeat = findViewById(R.id.layout_repeat);
        layoutSound = findViewById(R.id.layout_selectSound);
        tvrepeat = findViewById(R.id.tv_repeat);
        tvNameAlarm=findViewById(R.id.tv_nameAlarm);
        tvSound=findViewById(R.id.tv_sound);
        imageViewBack= findViewById(R.id.img_cancel_activity);
        imgSave = findViewById(R.id.img_saveNewAlarm);
        switchVibrate=findViewById(R.id.switch_vibrate);
        switchRepeat = findViewById(R.id.switch_repeat);
        //
        setEventForLayoutNameAlarm();
        //
        setEventLayoutrepeat();
        //
        setEventForLayoutSelectSound();
        //
        backLayout();
        save();
    }
    private void setEventForLayoutNameAlarm(){
        layoutNameAlarm.setOnClickListener(v -> {
            showDialogInputNameAlarm();
        });
    }
    private void showDialogInputNameAlarm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tên báo thức");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) -> {
            String userInput = input.getText().toString();
            if(userInput.isEmpty()) userInput="Báo thức";
            tvNameAlarm.setText(userInput);
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());

        builder.show();
    }
    private void setEventLayoutrepeat(){
        layoutrepeat.setOnClickListener(v->{
            showDialogrepeat();
        });
    }
    private void showDialogrepeat() {
        final StringBuilder result = new StringBuilder();
        final boolean[] checkedItems = new boolean[]{false, false, false, false, false, false, false};
        String[] options = {"thứ 2", "thứ 3", "thứ 4", "thứ 5", "thứ 6", "thứ 7", "chủ nhật"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lặp lại");
        builder.setMultiChoiceItems(options, checkedItems, (dialog, which, isChecked) -> {
            // Cập nhật trạng thái chọn của mỗi mục
            checkedItems[which] = isChecked;
        });

        builder.setPositiveButton("OK", (dialog, which) -> {
            // Xử lý khi người dùng bấm nút OK
            result.setLength(0); // Xóa chuỗi kết quả trước khi cập nhật

            for (int i = 0; i < checkedItems.length; i++) {
                if (checkedItems[i]) {
                    result.append(options[i]).append(", ");
                }
            }

            // Kiểm tra xem có ít nhất một tùy chọn nào được chọn trước khi cập nhật TextView
            if (result.length() > 0) {
                // Loại bỏ dấu ',' cuối cùng và cập nhật TextView
                tvrepeat.setText(result.substring(0, result.length() - 2));
            } else {
                // Không có tùy chọn nào được chọn
                tvrepeat.setText("chỉ một lần");
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    private void setEventForLayoutSelectSound(){
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
    private void save() {
        imgSave.setOnClickListener(v -> {
            String hour = edtHour.getText().toString();
            String minute = edtMinute.getText().toString();
            String nameAlarm = tvNameAlarm.getText().toString();

            int intHour = parseValue(hour);
            int intMinute = parseValue(minute);

            if (!isValidTime(intHour, intMinute)) {
                Toast.makeText(CreateNewAlarmActivity.this, "Hãy nhập đúng giờ phút !", Toast.LENGTH_SHORT).show();
                return;
            }

            String formattedTime = formatTime(intHour, intMinute);
            result( formattedTime);
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

    private void result( String time) {
        Intent intent = new Intent(CreateNewAlarmActivity.this, AlarmFragment.class);
        intent.putExtra("sound",tvSound.getText());
        intent.putExtra("isRepeat",switchRepeat.isChecked());
        intent.putExtra("isVibrate",switchVibrate.isChecked());
        intent.putExtra("repeat",tvrepeat.getText());
        intent.putExtra("nameAlarm", tvNameAlarm.getText());
        intent.putExtra("time", time);
        setResult(98, intent);
        finish();
    }

}