package com.example.alarmapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Base.Alarm;
import com.example.alarmapp.Interface.OnItemRclVDeleteClickListener;
import com.example.alarmapp.R;

import java.util.List;

public class DeleteManyAlarmAdapter extends RecyclerView.Adapter<DeleteManyAlarmAdapter.DeleteAlarmViewHolder> {
    private final Context context;
    private final List<Alarm> alarmList;
    private final OnItemRclVDeleteClickListener listener;
    public DeleteManyAlarmAdapter(Context context,List<Alarm> alarmList,OnItemRclVDeleteClickListener listener){
        this.context = context;
        this.alarmList=alarmList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public DeleteAlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_choice_alarm,parent,false);
        return new DeleteAlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteAlarmViewHolder holder, int position) {
        holder.setData(alarmList.get(position));
        holder.setEventCheckBox(alarmList.get(position));
        setEventItemRclVClick(holder,position);
    }

    @Override
    public int getItemCount() {
        return alarmList.isEmpty()? 0:alarmList.size();
    }
    private void setEventItemRclVClick(DeleteAlarmViewHolder holder,int position){
        holder.itemView.setOnClickListener(v -> listener.setOnClickAlarmListener(position));
    }
    static class DeleteAlarmViewHolder extends RecyclerView.ViewHolder{
        private final CheckBox checkBox;
        private final TextView tv_hour;
        private final TextView tv_timeRepeat;
        private final TextView tv_message;
        public DeleteAlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkBox);
            tv_hour=itemView.findViewById(R.id.tvTime_itemDelete);
            tv_timeRepeat=itemView.findViewById(R.id.tvRepeat_itemDelete);
            tv_message=itemView.findViewById(R.id.tvMessage_itemDelete);
        }

        public void setData(Alarm alarm){
            checkBox.setChecked(alarm.isChecked());
            tv_hour.setText(alarm.getTime_alarm());
            tv_timeRepeat.setText(alarm.getRepeat());
            if(!alarm.getMessage().equals("Báo thức")){
                tv_message.setText(alarm.getMessage());
                tv_message.setVisibility(View.VISIBLE);
            }
        }

        public void setEventCheckBox(Alarm alarm){
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> alarm.setChecked(isChecked));
        }
    }
}
