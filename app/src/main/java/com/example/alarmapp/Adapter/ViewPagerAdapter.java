package com.example.alarmapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.alarmapp.Fragment.AlarmFragment;
import com.example.alarmapp.Fragment.ClockFragment;
import com.example.alarmapp.Fragment.StopWatchFragment;
import com.example.alarmapp.Fragment.TimerFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AlarmFragment();
            case 1:
                return new ClockFragment();
            case 2:
                return new StopWatchFragment();
            case 3:
                return new TimerFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
