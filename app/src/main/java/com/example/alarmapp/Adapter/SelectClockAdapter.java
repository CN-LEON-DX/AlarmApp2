package com.example.alarmapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.alarmapp.Base.Clock;
import com.example.alarmapp.R;

import java.util.ArrayList;
import java.util.List;

public class SelectClockAdapter extends ArrayAdapter<Clock> {
    private final List<Clock> listSelectClock;
    private List<Clock> originalList;

    public SelectClockAdapter(Context context, List<Clock> listSelectClock) {
        super(context, 0, listSelectClock);
        this.listSelectClock = listSelectClock;
        this.originalList = new ArrayList<>(listSelectClock);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_clock_recycler, parent, false);
        }
        Clock clock = getItem(position);
        TextView tvGmt = listItemView.findViewById(R.id.tvGMT);
        TextView tvCity = listItemView.findViewById(R.id.tvCity);
        TextView tvTime = listItemView.findViewById(R.id.tvTimeCurrent);
        tvGmt.setVisibility(View.INVISIBLE);
        tvCity.setText(clock.getCity());
        tvTime.setText(clock.getTime());
        tvGmt.setText(clock.getGmt());

        return listItemView;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Clock> filteredList = new ArrayList<>();

                if (originalList == null) {
                    originalList = new ArrayList<>(listSelectClock);
                }

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(originalList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Clock item : originalList) {
                        if (item.getCity().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll((List<Clock>) results.values);
                notifyDataSetChanged();
            }
        };
    }

}

