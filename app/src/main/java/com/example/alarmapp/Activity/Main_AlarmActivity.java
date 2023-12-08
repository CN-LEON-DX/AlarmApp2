package com.example.alarmapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alarmapp.Adapter.ViewPagerAdapter;
import com.example.alarmapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Main_AlarmActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private Button btnSetting;
    private ImageView imageSetting;
    private  ViewPager2 viewPager;
    private TextView tvEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find id
        imageSetting = findViewById(R.id.imageSetting);
        btnSetting=findViewById(R.id.btn_setting);
        tabLayout = findViewById(R.id.tabLayout);
        tvEdit = findViewById(R.id.tvEdit);
        viewPager = findViewById(R.id.viewPager2);
        viewPager.setUserInputEnabled(false);
        /*
            initialize object
        */
        viewPagerAdapter = new ViewPagerAdapter(this);

        // set event change Fragment of View Pager
        setEventChangeFragmentForViewPager();
        // set event for tabLayout
        setEventForTabLayout();
        //set visible btnSetting
        setEventImageSetting();
        //set event btn setting
        setEventButtonSetting();
        requestPermission();
    }
    private void requestPermission(){
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS);
        if(permission!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Main_AlarmActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 0);
        }
    }

    //
    private void setEventForTabLayout(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    public void setEventChangeFragmentForViewPager(){
        viewPager.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position){
                        case 0: tab.setText("Báo thức");
                            tab.setCustomView(R.layout.custome_tab_alarm);
                            break;
                        case 1: tab.setText("Đồng hồ");
                            tab.setCustomView(R.layout.custome_tab_clock);
                            break;
                        case 2: tab.setText("Bấm giờ");
                            tab.setCustomView(R.layout.custome_tab_stopwatch);
                            break;
                    }
                }
        ).attach();
    }
    private void setEventImageSetting(){
        imageSetting.setOnClickListener(v -> {
            if(btnSetting.getVisibility() == View.VISIBLE){
                btnSetting.setVisibility(View.GONE);
            } else {
                btnSetting.setVisibility(View.VISIBLE);
            }
        });
    }
    private void setEventButtonSetting(){
        btnSetting.setOnClickListener(v ->{
            Intent intent = new Intent(Main_AlarmActivity.this,SettingActivity.class);
            startActivity(intent);
            btnSetting.setVisibility(View.GONE);
        });
    }

}
