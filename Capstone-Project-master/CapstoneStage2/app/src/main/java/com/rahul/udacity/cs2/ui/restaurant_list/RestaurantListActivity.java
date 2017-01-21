package com.rahul.udacity.cs2.ui.restaurant_list;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.ApplicationController;
import com.rahul.udacity.cs2.base.BaseActivity;
import com.rahul.udacity.cs2.base.BaseFragment;
import com.rahul.udacity.cs2.ui.restaurant_list.fragments.RestaurantsFragment;


public class RestaurantListActivity extends BaseActivity {

    ViewPager pager;
    ViewPagerAdapter adapter;
    CharSequence Titles[] = {"RESTAURANTS", "BAKERY", "COFFEE & TEA", "LIQUOR"};

    @Override
    protected void onResume() {
        super.onResume();
        Tracker t = ApplicationController.getApplicationInstance().getTracker();
        t.setScreenName("Restaurant Screen");
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    protected void initUi() {
        setBackButtonEnabled();

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles);
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

    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        CharSequence titles[];

        ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[]) {
            super(fm);
            this.titles = mTitles;
        }

        //This method return the fragment for the every position in the View Pager
        @Override
        public BaseFragment getItem(int position) {
            switch (position) {
                case 0:
                    RestaurantsFragment restaurants = new RestaurantsFragment();
                    return restaurants;
                case 1:
                    RestaurantsFragment bakery = new RestaurantsFragment();
                    return bakery;
                case 2:
                    RestaurantsFragment cafe = new RestaurantsFragment();
                    return cafe;
                case 3:
                    RestaurantsFragment liquor = new RestaurantsFragment();
                    return liquor;

                default:
                    return null;
            }

        }

        // This method return the titles for the Tabs in the Tab Strip
        @Override
        public CharSequence getPageTitle(int position) {
            return Titles[position];
        }

        // This method return the Number of tabs for the tabs Strip
        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
