package com.example.alarmapp.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Base.Clock;
import com.example.alarmapp.R;

import java.util.ArrayList;
import java.util.List;

public class Clock_Recycler_Adapter extends ListAdapter<Clock, Clock_Recycler_Adapter.ClockViewHolder> {

    protected Clock_Recycler_Adapter() {
        super(new DiffUtil.ItemCallback<Clock>() {
            @Override
            public boolean areItemsTheSame(@NonNull Clock oldItem, @NonNull Clock newItem) {
                String timeOld = oldItem.calculateTime(oldItem.getTimeZone());
                String timeNew = newItem.calculateTime(newItem.getTimeZone());
                return timeNew.equals(timeOld);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Clock oldItem, @NonNull Clock newItem) {
                String timeOld = oldItem.calculateTime(oldItem.getTimeZone());
                String timeNew = newItem.calculateTime(newItem.getTimeZone());
                return timeNew.equals(timeOld);
            }
        });
    }
    public static Clock_Recycler_Adapter createInstance(){
        return  new Clock_Recycler_Adapter();
    }
    @NonNull
    @Override
    public ClockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clock_recycler, parent, false);
        return new ClockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClockViewHolder holder, int position) {
        holder.setData(getItem(position));
    }
    public void deleteItem(int position){
        ArrayList<Clock> currentList = new ArrayList<>(getCurrentList());
        currentList.remove(position);
        notifyDataSetChanged();
    }
    public ArrayList<Clock> getList(){
        return new ArrayList<>(getCurrentList());
    }
    static class ClockViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCity;
        private final TextClock tvTimeCurrent;
        private final TextView tvGMT;

        public ClockViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeCurrent = itemView.findViewById(R.id.tvTimeCurrent);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvGMT = itemView.findViewById(R.id.tvGMT);
        }
        public void setData(Clock clock) {
            if (clock.getTimeZone() != null && clock.getCity() != null && clock.getTimeDifferences() != null) {
                tvTimeCurrent.setTimeZone(clock.getTimeZone());
                tvCity.setText(clock.getCity());
                tvGMT.setText(clock.getTimeDifferences());
            }
        }
    }
}
