package com.example.alarmapp.Model.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Model.item.StopWatch;
import com.example.alarmapp.R;

import java.util.List;

public class StopWatchAdapter extends RecyclerView.Adapter<StopWatchAdapter.StopWatchViewHolder>{
    private List<StopWatch> stopWatchList;
    private Context context;

    public StopWatchAdapter(List<StopWatch> stopWatchList, Context context) {
        this.stopWatchList = stopWatchList;
        this.context = context;
    }

    @NonNull
    @Override
    public StopWatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_stopwatch_recycler, parent, false);
        return new StopWatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StopWatchViewHolder holder, int position) {
        holder.setData(stopWatchList.get(position));
    }

    @Override
    public int getItemCount() {
        return stopWatchList.isEmpty() ? 0 : stopWatchList.size();
    }
    public void addItem(StopWatch stopWatch){
        stopWatchList.add(stopWatch);

    }
    class StopWatchViewHolder extends RecyclerView.ViewHolder{
        private TextView tvIndexOf, tvTimeRecord, tvTimeAdd;
        public StopWatchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndexOf = itemView.findViewById(R.id.tvIndexOf);
            tvTimeRecord = itemView.findViewById(R.id.tvTimeRecord);
            tvTimeAdd = itemView.findViewById(R.id.tvTimeAdded);
        }
        public void setData(StopWatch stopWatch){
            if (stopWatch.getIndexOf() != null && stopWatch.getTimeRecord()!= null && stopWatch.getTimeAdd() != null){
                tvIndexOf.setText(stopWatch.getIndexOf());
                tvTimeRecord.setText(stopWatch.getTimeRecord());
                tvTimeAdd.setText(stopWatch.getTimeAdd());
            }
        }
    }
}
