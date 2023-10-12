package com.example.alarmapp.clock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alarmapp.R;
import java.util.List;

public class SelectClockAdapterRecycler extends RecyclerView.Adapter<SelectClockAdapterRecycler.SelectClockViewHolder> {
    private List<SelectClock> selectClocks;
    private Context context;

    public SelectClockAdapterRecycler(List<SelectClock> selectClocks, Context context) {
        this.selectClocks = selectClocks;
        this.context = context;
    }

    @NonNull
    @Override
    public SelectClockAdapterRecycler.SelectClockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_clock_select,parent,false);
        return new SelectClockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectClockAdapterRecycler.SelectClockViewHolder holder, int position) {
        holder.setData(selectClocks.get(position));
    }

    @Override
    public int getItemCount() {
        return selectClocks.isEmpty()? 0 : selectClocks.size();
    }
    class SelectClockViewHolder extends RecyclerView.ViewHolder{
        private TextView country;
        private TextView hour;
        private TextView minute;
        private TextView gmt;
        public SelectClockViewHolder(@NonNull View itemView) {
            super(itemView);
            country=(TextView) itemView.findViewById(R.id.tv_country);
            hour=(TextView)  itemView.findViewById(R.id.tv_itemhour);
            minute=(TextView) itemView.findViewById(R.id.tv_minute);
            gmt=(TextView) itemView.findViewById(R.id.tv_itemgmt);
        }
        public void setData(SelectClock selectClock){
            if(selectClock.getCountry()!=null&&selectClock.getHour()!=null
                    &&selectClock.getMinute()!=null&&selectClock.getGmt()!=null){
                country.setText(selectClock.getCountry());
                hour.setText(selectClock.getHour());
                minute.setText(selectClock.getMinute());
                gmt.setText(selectClock.getGmt());
            }
        }
    }
}
