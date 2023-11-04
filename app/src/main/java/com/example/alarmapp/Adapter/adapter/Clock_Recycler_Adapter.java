package com.example.alarmapp.Adapter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Adapter.item.Clock;
import com.example.alarmapp.R;

import java.util.List;

public class Clock_Recycler_Adapter extends RecyclerView.Adapter<Clock_Recycler_Adapter.ClockViewHolder> {
    private List<Clock> clockList;
    private Context context;

    public Clock_Recycler_Adapter(Context context, List<Clock> clockList) {
        this.context = context;
        this.clockList = clockList;
    }

    @NonNull
    @Override
    public ClockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_clock_recycler, parent, false);
        return new ClockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClockViewHolder holder, int position) {
        holder.setData(clockList.get(position));
    }

    @Override
    public int getItemCount() {
        return clockList.isEmpty() ? 0 : clockList.size();
    }

    class ClockViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCity;
        private TextView tvTimeCurrent;

        public ClockViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeCurrent = itemView.findViewById(R.id.tvTimeCurrent);
            tvCity = itemView.findViewById(R.id.tvCity);
        }
        public void setData(Clock clock) {
            if (clock.getTime() != null && clock.getCity()!= null){
                tvTimeCurrent.setText(clock.getTime());
                tvCity.setText(clock.getCity());
            }
        }
    }
}