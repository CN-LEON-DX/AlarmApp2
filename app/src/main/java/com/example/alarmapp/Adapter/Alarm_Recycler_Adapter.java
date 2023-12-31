package com.example.alarmapp.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Base.Alarm;
import com.example.alarmapp.Database.AlarmDataBase;
import com.example.alarmapp.Interface.OnItemAlarmClickListener;
import com.example.alarmapp.R;

import java.util.List;

public class Alarm_Recycler_Adapter extends RecyclerView.Adapter<Alarm_Recycler_Adapter.AlarmViewHolder>{
    private final List<Alarm> alarmList;
    private final Context context;
    private final AlarmDataBase alarmDataBase;
    private final OnItemAlarmClickListener listener;

    public Alarm_Recycler_Adapter(Context context, List<Alarm> alarmList, AlarmDataBase dataBase, OnItemAlarmClickListener listener) {
        this.context = context;
        this.alarmList = alarmList;
        this.alarmDataBase=dataBase;
        this.listener = listener;
    }
    public Alarm getAlarm(int position){
        return alarmList.get(position);
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
        holder.setEventSwitch(context,alarmList.get(position));
        setClickItem(holder,position);
        setLongClickItem(holder, position);
    }
    private void setClickItem(@NonNull AlarmViewHolder holder, int position){
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.setOnItemClickListener(position);
            }
        });
    }
    private void setLongClickItem(AlarmViewHolder holder,int position){
        holder.itemView.setOnLongClickListener(v -> {
            if(listener !=null){
                listener.setOnItemLongClickListener(position);
                return true;
            }
            return false;
        });
    }
    @Override
    public int getItemCount() {
        return alarmList.isEmpty() ? 0 : alarmList.size();
    }
    public void removeAlarm(int position) {
        if (position >= 0 && position < alarmList.size()) {
            Alarm removedAlarm = alarmList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, alarmList.size());
            removedAlarm.cancelAlarm(context);
        }
    }
    /*
    * ViewHolder cho Adapter
    **/

    class AlarmViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTime;
        private final TextView tvMessage;
        private final SwitchCompat switchCompat;
        private final TextView tvRepeat;
        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime_itemAlarm);
            tvMessage = itemView.findViewById(R.id.tvMessage_itemAlarm);
            switchCompat = itemView.findViewById(R.id.switch_item_alarm);
            tvRepeat = itemView.findViewById(R.id.tvRepeat_itemAlarm);
        }

        private void setVisibilityUI(Alarm alarm){
            if(!alarm.getMessage().equals("Báo thức"))
                tvMessage.setVisibility(View.VISIBLE);
        }
        public void setData(Alarm alarm) {
            setVisibilityUI(alarm);
            if (alarm.getTime_alarm() != null && alarm.getMessage() != null) {
                tvTime.setText(alarm.getTime_alarm());
                tvMessage.setText(alarm.getMessage());
                switchCompat.setChecked(alarm.getTurnOn());
                tvRepeat.setText(alarm.getRepeat());
                setColorTexView();
            }
        }

        private void setColorTexView() {
            if (switchCompat.isChecked()) {
                tvTime.setTextColor(ContextCompat.getColor(context, R.color.turnOnAlarm));
                tvMessage.setTextColor(ContextCompat.getColor(context, R.color.turnOnMessageAlarm));
                tvRepeat.setTextColor(ContextCompat.getColor(context,R.color.turnOnAlarm));
            } else {
                tvTime.setTextColor(ContextCompat.getColor(context, R.color.turnOffAlarm));
                tvMessage.setTextColor(ContextCompat.getColor(context, R.color.turnOffMessageAlarm));
                tvRepeat.setTextColor(ContextCompat.getColor(context,R.color.turnOffAlarm));
            }
        }

        private void setEventSwitch(Context context, Alarm alarm) {
            switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
                setColorTexView();
                if (isChecked) {
                    alarm.setTurnOn(true);
                    alarm.createAlarm(context);
                    Toast.makeText(context, "Báo thức vào lúc " + tvTime.getText() + " được bạn bật", Toast.LENGTH_SHORT).show();
                } else {
                    alarm.setTurnOn(false);
                    alarm.cancelAlarm(context);
                    Toast.makeText(context, "Báo thức vào lúc " + tvTime.getText() + " được bạn tắt", Toast.LENGTH_SHORT).show();
                }
                alarmDataBase.updateStatusSwitch(alarm);
            });
        }
    }
}


