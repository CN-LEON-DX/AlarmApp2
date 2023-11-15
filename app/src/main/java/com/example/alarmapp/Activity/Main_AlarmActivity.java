package com.example.alarmapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.alarmapp.Adapter.ViewPagerAdapter;
import com.example.alarmapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Main_AlarmActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private TableLayout tableLayout;
    private TextView tvEdit;
    private SharedPreferences sharedPreferencesPut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        // Thêm tab vào TabLayout
        // Đặt màu cho văn bản của các tab
        //tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.white));
        // Khởi tạo biến adapter đẻ quản lý
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        // Sau đó cần lâý id viewPager2 layout đã tạo bên giao diện
        ViewPager2 viewPager2 = findViewById(R.id.viewPager2);
        // Sau đó mới thiết lập vào nhé ;>
        viewPager2.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    // Thiết lập nội dung của tab tại vị trí này
                    // Ví dụ: Thiết lập tab theo vị trí với các tên tương ứng
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
                        case 3: tab.setText("Hẹn giờ");
                                tab.setCustomView(R.layout.custome_tab_timer);
                                break;
                    }
                }
        ).attach();

//        TabLayout.Tab tab1 = tabLayout.newTab();
//        tab1.setCustomView(R.layout.custome_tab_alarm);
//        tabLayout.addTab(tab1);
//        TabLayout.Tab tab2 = tabLayout.newTab();
//        tab2.setCustomView(R.layout.custome_tab_clock);
//        tabLayout.addTab(tab2);
//        TabLayout.Tab tab3 = tabLayout.newTab();
//        tab3.setCustomView(R.layout.custome_tab_stopwatch);
//        tabLayout.addTab(tab3);
//        TabLayout.Tab tab4 = tabLayout.newTab();
//        tab4.setCustomView(R.layout.custome_tab_timer);
//        tabLayout.addTab(tab4);

        // Đặt sự kiện lắng nghe khi chọn tab
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

    // function event
    public void handleEventEditing_TV(){
        tvEdit = findViewById(R.id.tvEdit);

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
