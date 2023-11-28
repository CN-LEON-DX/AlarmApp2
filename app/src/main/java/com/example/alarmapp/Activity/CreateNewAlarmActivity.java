package com.example.alarmapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alarmapp.R;

public class CreateNewAlarmActivity extends AppCompatActivity {
    private ScrollView scrollHour,scrollMinute;
    private RelativeLayout layoutNameAlarm,layoutlaplai,layoutSound;
    private TextView tvNameAlarm,tvlaplai,tvSound;
    private ImageView imageViewBack, imgSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_alarm);
        //find id
        scrollHour = findViewById(R.id.scroll_hour);
        scrollMinute = findViewById(R.id.scroll_minute);
        layoutNameAlarm = findViewById(R.id.layout_nameAlarm);
        layoutlaplai = findViewById(R.id.layout_lapLai);
        layoutSound = findViewById(R.id.layout_selectSound);
        tvlaplai = findViewById(R.id.tv_lapLai);
        tvNameAlarm=findViewById(R.id.tv_nameAlarm);
        tvSound=findViewById(R.id.tv_sound);
        imageViewBack= findViewById(R.id.img_cancel_activity);
        imgSave = findViewById(R.id.img_saveNewAlarm);
        //
        setEventForLayoutNameAlarm();
        //
        setEventLayoutlaplai();
        //
        setEventForLayoutSelectSound();
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
    private void setEventLayoutlaplai(){
        layoutlaplai.setOnClickListener(v->{
            showDialoglaplai();
        });
    }
    private void showDialoglaplai() {
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
                tvlaplai.setText(result.substring(0, result.length() - 2));
            } else {
                // Không có tùy chọn nào được chọn
                tvlaplai.setText("chỉ một lần");
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
}