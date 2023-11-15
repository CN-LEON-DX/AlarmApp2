package com.example.alarmapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Base.Clock;
import com.example.alarmapp.R;

public class Clock_Recycler_Adapter extends ListAdapter<Clock, Clock_Recycler_Adapter.ClockViewHolder> {

    protected Clock_Recycler_Adapter() {
        super(new DiffUtil.ItemCallback<Clock>() {
            @Override
            public boolean areItemsTheSame(@NonNull Clock oldItem, @NonNull Clock newItem) {
                String timeOld = oldItem.calculateTime(oldItem.getTimeZone());
                String timeNew = newItem.calculateTime(newItem.getTimeZone());
                String timeDifferencesOld= oldItem.getFormattedTime(Integer.parseInt(oldItem.getTimeDifferences()));
                String timeDifferencesNew= oldItem.getFormattedTime(Integer.parseInt(newItem.getTimeDifferences()));
                return timeNew.equals(timeOld)||timeDifferencesOld.equals(timeDifferencesNew);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Clock oldItem, @NonNull Clock newItem) {
                String timeOld = oldItem.calculateTime(oldItem.getTimeZone());
                String timeNew = newItem.calculateTime(newItem.getTimeZone());
                String timeDifferencesOld= oldItem.getFormattedTime(Integer.parseInt(oldItem.getTimeDifferences()));
                String timeDifferencesNew= oldItem.getFormattedTime(Integer.parseInt(newItem.getTimeDifferences()));
                return timeNew.equals(timeOld)||timeDifferencesOld.equals(timeDifferencesNew);
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

    static class ClockViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCity;
        private final TextView tvTimeCurrent;
        private final TextView tvGMT;

        public ClockViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeCurrent = itemView.findViewById(R.id.tvTimeCurrent);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvGMT = itemView.findViewById(R.id.tvGMT);
        }

        public void setData(Clock clock) {
            if (clock.getTimeZone() != null && clock.getCity() != null && clock.getTimeDifferences() != null) {
                tvTimeCurrent.setText(clock.getTimeZone());
                tvCity.setText(clock.getCity());
                tvGMT.setText(clock.getTimeDifferences());
            }
        }
    }
}
