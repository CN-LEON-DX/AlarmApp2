package com.example.alarmapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.alarmapp.Fragment.AlarmFragment;
import com.example.alarmapp.Fragment.ClockFragment;
import com.example.alarmapp.Fragment.StopWatchFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments = new ArrayList<>();
        fragments.add(new AlarmFragment());
        fragments.add(new ClockFragment());
        fragments.add(new StopWatchFragment());
    }

    // ... Các phương thức khác

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
