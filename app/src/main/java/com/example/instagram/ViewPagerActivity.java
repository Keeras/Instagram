package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;

import adapter.ViewPagerAdapter;
import fragments.HomeFragment;
import fragments.LikeFragment;
import fragments.PostFragment;
import fragments.ProfileFragment;
import fragments.SearchFragment;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_view_pager);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabId);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#102CA2"));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new HomeFragment(), "");
        viewPagerAdapter.addFragment(new SearchFragment(), "");
        viewPagerAdapter.addFragment(new PostFragment(), "");
        viewPagerAdapter.addFragment(new LikeFragment(), "");
        viewPagerAdapter.addFragment(new ProfileFragment(), "");


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.home_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.search_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.post_icon);
        tabLayout.getTabAt(3).setIcon(R.drawable.love_icon);
        tabLayout.getTabAt(4).setIcon(R.drawable.person_icon);

    }
}
