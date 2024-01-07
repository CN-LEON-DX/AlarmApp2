package com.example.alarmapp.Base;

import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Adapter.ClockAdapter;
import com.example.alarmapp.Database.WatchTimeCityDatabase;
import com.example.alarmapp.R;

public class SwipeToDeleteCLock extends ItemTouchHelper.SimpleCallback {
    private WatchTimeCityDatabase database;
    private Fragment fragment;
    private ClockAdapter adapter;

    public SwipeToDeleteCLock (WatchTimeCityDatabase database,Fragment fragment,ClockAdapter adapter){
        super(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
        this.adapter=adapter;
        this.fragment=fragment;
        this.database=database;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        showDialogDeleteItem(position);
    }
    private void showDialogDeleteItem(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
        builder.setMessage(R.string.messageDeleteItem)
                .setTitle(R.string.titleAlertDialog);
        builder.setPositiveButton(R.string.No,(dialog, which) ->{adapter.notifyItemChanged(position);});
        builder.setNegativeButton(R.string.accept,(dialog, which) ->
        {
            Clock clock = adapter.getClock(position);
            adapter.deleteCLock(position);
            database.deleteData(clock);
        });
        builder.setOnCancelListener(dialog -> {
            adapter.notifyItemChanged(position);
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
