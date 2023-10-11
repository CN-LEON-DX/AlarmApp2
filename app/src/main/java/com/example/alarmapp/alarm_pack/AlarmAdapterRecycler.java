package com.example.alarmapp.alarm_pack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.R;

import java.util.List;

public class AlarmAdapterRecycler extends RecyclerView.Adapter<AlarmAdapterRecycler.AlarmViewHolder> {
    private List<Alarm> alarmList;
    private Context context;

    public AlarmAdapterRecycler(List<Alarm> alarmList, Context context) {
        this.alarmList = alarmList;
        this.context = context;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_alarm, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        holder.setData(alarmList.get(position));

        // Để sau sử lý các sự kiện ở đây !
        // Nhấn tv -> vv
    }

    @Override
    public int getItemCount() {
        return alarmList.isEmpty() ? 0 : alarmList.size();
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private TextView tvMessage;
        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
        // SET DATA CHO CÁC THÀNH PHẦN CỦA ITEM
        public void setData(Alarm alarm){
            if (alarm.getTime_alarm() != null && alarm.getMessage()!=null){
                tvTime.setText(alarm.getTime_alarm());
                tvMessage.setText(alarm.getMessage());
            }
        }
    }
}
