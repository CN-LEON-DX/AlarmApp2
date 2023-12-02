package com.example.alarmapp.Base;

import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Adapter.Clock_Recycler_Adapter;
import com.example.alarmapp.Database.WatchTimeCityDatabase;
import com.example.alarmapp.R;

import java.util.ArrayList;
import java.util.List;

public class SwipeToDeleteClock extends ItemTouchHelper.SimpleCallback {
    private Clock_Recycler_Adapter adapter;
    private Fragment fragment;
    private WatchTimeCityDatabase database;
    public SwipeToDeleteClock(Clock_Recycler_Adapter clockRecyclerAdapter, WatchTimeCityDatabase database, Fragment fragment){
        super(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
        this.adapter=clockRecyclerAdapter;
        this.fragment=fragment;
        this.database=database;
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
    private void showDialog(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
        builder.setMessage(R.string.messageDeleteItem)
                .setTitle(R.string.titleAlertDialog);
        builder.setPositiveButton("không",(dialog, which) ->{adapter.notifyItemChanged(position);});
        builder.setNegativeButton("xác nhận",(dialog, which) ->
        {
            adapter.deleteItem(position);
            List<Clock> listClock = new ArrayList<>(adapter.getCurrentList());
            Clock clock = listClock.get(position);
            database.deleteData(clock);
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

