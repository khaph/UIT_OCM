package com.uit.khaph.uitocm;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    public ViewPager vpViewPager;
    String userName;
    String className;
    String pictureUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView();

    }

    public void mapView(){
        userName = getIntent().getExtras().getString("userName","unidentify");
        className = getIntent().getExtras().getString("className","unidentify");
        pictureUrl = getIntent().getExtras().getString("pictureUrl","uni");
        vpViewPager = (ViewPager)findViewById(R.id.container);
        vpViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),userName,className,pictureUrl));
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vpViewPager);
    }
}
