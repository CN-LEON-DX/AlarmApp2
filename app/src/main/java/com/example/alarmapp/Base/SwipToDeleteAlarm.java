package com.example.alarmapp.Base;

import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Adapter.Alarm_Recycler_Adapter;
import com.example.alarmapp.Database.AlarmDataBase;

import java.util.ArrayList;
import java.util.List;

public class SwipToDeleteAlarm extends ItemTouchHelper.SimpleCallback {
    private Alarm_Recycler_Adapter adapter;
    private Fragment fragment;
    private AlarmDataBase dataBase;
    public SwipToDeleteAlarm (Alarm_Recycler_Adapter adapter, Fragment fragment, AlarmDataBase dataBase ){
        super(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
        this.adapter = adapter;
        this.fragment = fragment;
        this.dataBase = dataBase;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position=viewHolder.getAdapterPosition();
        showDialog(position);
    }
    private void showDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
        builder.setTitle("Thông báo");
        builder.setMessage("bạn có chắc chắn muốn xóa không");
        builder.setPositiveButton("không", (dialog, which) -> {
            adapter.notifyItemChanged(position);
        });
        builder.setNegativeButton("xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Alarm alarm = adapter.getAlarm(position);
                adapter.removeAlarm(position);
                dataBase.deleteData(alarm);
            }
        });
        builder.show();
    }
}
