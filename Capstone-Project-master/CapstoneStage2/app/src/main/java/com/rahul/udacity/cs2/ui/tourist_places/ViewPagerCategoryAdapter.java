package com.rahul.udacity.cs2.ui.tourist_places;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rahul.udacity.cs2.base.BaseFragment;
import com.rahul.udacity.cs2.ui.tourist_places.fragments.MuseumFragment;
import com.rahul.udacity.cs2.ui.tourist_places.fragments.NightClubFragment;
import com.rahul.udacity.cs2.ui.tourist_places.fragments.TempleFragment;

class ViewPagerCategoryAdapter extends FragmentStatePagerAdapter {

    private CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created

    ViewPagerCategoryAdapter(FragmentManager fm, CharSequence mTitles[]) {
        super(fm);
        this.Titles = mTitles;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public BaseFragment getItem(int position) {
        switch (position) {
            case 0:
                TempleFragment temple = new TempleFragment();
                return temple;
            case 1:
                MuseumFragment museum = new MuseumFragment();
                return museum;
            case 2:
                NightClubFragment nightClub = new NightClubFragment();
                return nightClub;

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
        return Titles.length;
    }
}