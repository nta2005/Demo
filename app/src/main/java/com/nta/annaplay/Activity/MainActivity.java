package com.nta.annaplay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.StrictMode;

import com.google.android.material.tabs.TabLayout;
import com.nta.annaplay.Adapter.MainViewPagerAdapter;
import com.nta.annaplay.Fragment.Fragment_Home;
import com.nta.annaplay.Fragment.Fragment_Search;
import com.nta.annaplay.R;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Kiểm tra mạng:
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);
        //Đưa fragment vào Viewpager và gán lên Tablayout:
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new Fragment_Home(),"");
        mainViewPagerAdapter.addFragment(new Fragment_Search(),"");
        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.homepage);
        tabLayout.getTabAt(1).setIcon(R.drawable.loupe);
    }
}
