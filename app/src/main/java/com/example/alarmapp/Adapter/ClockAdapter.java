package com.example.alarmapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Base.Clock;
import com.example.alarmapp.Interface.OnItemClockClickListener;
import com.example.alarmapp.R;

import java.util.List;

public class ClockAdapter extends RecyclerView.Adapter<ClockAdapter.ClockViewHolder> {
    private List<Clock> clockList;
    private Context context;
    private OnItemClockClickListener listener;

    public ClockAdapter(List<Clock> clockList, Context context,OnItemClockClickListener listener){
        this.clockList= clockList;
        this.context=context;
        this.listener=listener;
    }
    @NonNull
    @Override
    public ClockAdapter.ClockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_clock_recycler,parent,false);
        return new ClockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClockAdapter.ClockViewHolder holder, int position) {
        holder.setData(clockList.get(position));
        setLongClickListener(holder,position);
    }
    private void setLongClickListener(ClockAdapter.ClockViewHolder holder,int position){
        holder.itemView.setOnLongClickListener(v -> {
            if(listener!=null){
                listener.setOnLongClickItemClockListener(position);
                return true;
            }
            return  false;
        });

    }
    @Override
    public int getItemCount() {
        return clockList.size();
    }
    public  Clock getClock(int position){
        return clockList.get(position);
    }
    public void deleteCLock(int position){
        clockList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,clockList.size());
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
