package com.rahul.udacity.cs2.ui.navdrawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.BaseFragment;
import com.rahul.udacity.cs2.database.DatabaseSave;
import com.rahul.udacity.cs2.ui.home.HomeView;
import com.rahul.udacity.cs2.ui.home.NavDrawerEnum;
import com.rahul.udacity.cs2.ui.login.LoginActivity;
import com.rahul.udacity.cs2.utility.Utility;

public class NavDrawerFragment extends BaseFragment implements NavDrawerView, View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private HomeView mHomeView;
    private NavDrawerEnum mSelectedEnum;
    private boolean drawerItemClicked;
    private NavDrawerPresenter navDrawerPresenter;


    @Override
    protected void initUi() {
        navDrawerPresenter = new NavDrawerPresenter(this);
        findViewById(R.id.home).setOnClickListener(this);
        findViewById(R.id.my_places_lv).setOnClickListener(this);
        findViewById(R.id.saved_hotels_lv).setOnClickListener(this);
        findViewById(R.id.restaurants_lv).setOnClickListener(this);

    }

    @Override
    protected int getLayoutById() {
        return R.layout.fragment_nav_drawer;
    }

    @Override
    public void setupNavDrawer(DrawerLayout mDrawerLayout, Context context) {
        mHomeView = (HomeView) context;
        this.mDrawerLayout = mDrawerLayout;

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerToggle = new ActionBarDrawerToggle((Activity) mContext, mDrawerLayout,
                null, R.string.navigation_drawer_open,
                R.string.navigation_drawer_open) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }
                Utility.hideKeyboard(getContext());
                //Handle drawer clicks
                if (drawerItemClicked) {
                    drawerItemClicked = false;
                    mHomeView.onNavigationDrawerItemSelected(mSelectedEnum);

                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }
                Utility.hideKeyboard(getContext());

            }
        };

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    public void doLogout() {
        DatabaseSave databaseSave = new DatabaseSave(getActivity());
        databaseSave.clearDatabase();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        getActivity().finish();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                drawerItemClicked = true;
                mSelectedEnum = NavDrawerEnum.HOME;
                mDrawerLayout.closeDrawers();
                break;
            case R.id.my_places_lv:
                drawerItemClicked = true;
                mSelectedEnum = NavDrawerEnum.SAVED_PLACES;
                mDrawerLayout.closeDrawers();
                break;
            case R.id.saved_hotels_lv:
                drawerItemClicked = true;
                mSelectedEnum = NavDrawerEnum.SAVED_HOTELS;
                mDrawerLayout.closeDrawers();
            case R.id.restaurants_lv:
                drawerItemClicked = true;
                mSelectedEnum = NavDrawerEnum.SAVED_RESTAURANTS;
                mDrawerLayout.closeDrawers();
                break;
            case R.id.share_app_lv:
                drawerItemClicked = true;
                mSelectedEnum = NavDrawerEnum.SHARE_APP;
                mDrawerLayout.closeDrawers();
                break;
            case R.id.logout_iv:
                navDrawerPresenter.doLogout();
                break;

        }
    }

    @Override
    public Context getViewContext() {
        return mContext;
    }

    @Override
    public void showProgress(boolean showProgress) {

    }
}
