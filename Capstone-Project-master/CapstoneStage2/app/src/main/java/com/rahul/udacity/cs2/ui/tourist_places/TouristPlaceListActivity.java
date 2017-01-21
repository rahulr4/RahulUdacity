package com.rahul.udacity.cs2.ui.tourist_places;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.ApplicationController;
import com.rahul.udacity.cs2.base.BaseActivity;

public class TouristPlaceListActivity extends BaseActivity {

    ViewPager pager;
    ViewPagerCategoryAdapter adapter;
    CharSequence Titles[] = {"TEMPLE", "MUSEUMS", "NIGHT CLUB"};

    @Override
    protected void onResume() {
        super.onResume();
        Tracker t = ApplicationController.getApplicationInstance().getTracker();
        t.setScreenName("Tourist Places Categories");
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    protected void initUi() {
        setBackButtonEnabled();

        adapter = new ViewPagerCategoryAdapter(getSupportFragmentManager(), Titles);
        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_restaurant_list;
    }
}
