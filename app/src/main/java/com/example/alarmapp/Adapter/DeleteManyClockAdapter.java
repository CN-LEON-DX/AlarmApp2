package com.example.alarmapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Base.Clock;
import com.example.alarmapp.Interface.OnItemRclVDeleteClickListener;
import com.example.alarmapp.R;

import java.util.List;

public class DeleteManyClockAdapter extends RecyclerView.Adapter<DeleteManyClockAdapter.DeleteManyClockViewHolder> {
    private List<Clock> clockList;
    private Context context;
    private OnItemRclVDeleteClickListener listener;
    public DeleteManyClockAdapter(Context context,List<Clock> clockList,OnItemRclVDeleteClickListener listener){
        this.clockList=clockList;
        this.context=context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public DeleteManyClockAdapter.DeleteManyClockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_choice_clock,parent,false);
        return new DeleteManyClockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteManyClockAdapter.DeleteManyClockViewHolder holder, int position) {
        holder.setData(clockList.get(position));
        holder.setEventCheckBox(clockList.get(position));
        setEventItemRclVClick(holder,position);
    }

    @Override
    public int getItemCount() {
        return clockList.size();
    }
    private void setEventItemRclVClick(DeleteManyClockAdapter.DeleteManyClockViewHolder holder,int position){
        holder.itemView.setOnClickListener(v -> {
            listener.setOnClickClockListener(position);
        });
    }

    class DeleteManyClockViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView tv_city;
        private TextView tv_timeDifferences;
        public DeleteManyClockViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkBox2);
            tv_city=itemView.findViewById(R.id.tv_cityItemChoiceClock);
            tv_timeDifferences=itemView.findViewById(R.id.tv_timeDifferenceItemChoiceClock);
        }
        public void setData(Clock clock){
            checkBox.setChecked(clock.isChecked());
            tv_city.setText(clock.getCity());
            tv_timeDifferences.setText(clock.getTimeDifferences());
        }
        private void setEventCheckBox(Clock clock){
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                checkBox.setChecked(isChecked);
            });
        }
    }
}
