package com.example.alarmapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Base.Clock;
import com.example.alarmapp.R;

import java.util.List;

public class SelectClockAdapter extends RecyclerView.Adapter<SelectClockAdapter.SelectClockViewHolder> {
    private List<Clock> listSelectClock;
    private Context context;

    public SelectClockAdapter(List<Clock> listSelectClock, Context context) {
        this.listSelectClock = listSelectClock;
        this.context = context;
    }

    @NonNull
    @Override
    public SelectClockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_clock_recycler, parent, false);
        return new SelectClockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectClockViewHolder holder, int position) {
        holder.setData(listSelectClock.get(position));
    }

    @Override
    public int getItemCount() {
        return listSelectClock.isEmpty() ? 0:listSelectClock.size();
    }

    class SelectClockViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_country, tv_GMT, tv_timeCurrent;

        public SelectClockViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_country = itemView.findViewById(R.id.tvCountry);
            tv_GMT = itemView.findViewById(R.id.tvGMT);
            tv_timeCurrent = itemView.findViewById(R.id.tvTimeCurrent);
        }

        public void setData(@NonNull Clock clock) {
            if (clock.getGmt() != null && clock.getTime() != null && clock.getCountry() != null) {
                tv_country.setText(String.valueOf(clock.getCountry()));
                tv_GMT.setText(String.valueOf(clock.getGmt()));
                tv_timeCurrent.setText(String.valueOf(clock.getTime()));
            }
        }
    }
}

