package com.example.alarmapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.R;

import java.util.List;

public class Alarm_Recycler_Adapter extends RecyclerView.Adapter<Alarm_Recycler_Adapter.AlarmViewHolder> {
    private List<Alarm> alarmList;
    private Context context;

    public Alarm_Recycler_Adapter(Context context, List<Alarm> alarmList) {
        this.context = context;
        this.alarmList = alarmList;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_alarm_recycler, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        holder.setData(alarmList.get(position));
    }

    @Override
    public int getItemCount() {
        return alarmList.isEmpty() ? 0 : alarmList.size();
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private TextView tvMessage;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }

        public void setData(Alarm alarm) {
            if (alarm.getTime_alarm() != null && alarm.getMessage() != null) {
                tvTime.setText(alarm.getTime_alarm());
                tvMessage.setText(alarm.getMessage());
            }
        }
    }
}
