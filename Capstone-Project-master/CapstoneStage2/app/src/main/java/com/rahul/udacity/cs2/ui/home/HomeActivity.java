package com.rahul.udacity.cs2.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.BaseActivity;
import com.rahul.udacity.cs2.ui.navdrawer.NavDrawerFragment;
import com.rahul.udacity.cs2.ui.saved.SavedListFragment;
import com.rahul.udacity.cs2.utility.Constants;

/**
 * Created by rahulgupta on 11/11/16.
 */

public class HomeActivity extends BaseActivity implements HomeView {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void initUi() {
        setHeaderTitle(getString(R.string.choose_place));
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavDrawerFragment mNavDrawerFragment = (NavDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_left);
        mNavDrawerFragment.setupNavDrawer(mDrawerLayout, this);

        getHeaderToolBar().setNavigationIcon(R.drawable.menu_icon);

        onNavigationDrawerItemSelected(NavDrawerEnum.HOME);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawers();
        else
            super.onBackPressed();
//            showConfirmationDialog();
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_home;
    }

    @Override
    protected void onHeaderBackClicked() {
        //Drawer Logic
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(NavDrawerEnum navDrawerEnum) {
        switch (navDrawerEnum) {
            case HOME:
                setHeaderTitle(getString(R.string.home));
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment(),
                        HomeFragment.class.getSimpleName())
                        .commitAllowingStateLoss();
                break;
            default:
                switch (navDrawerEnum) {
                    case SAVED_HOTELS:
                        setHeaderTitle(getString(R.string.my_saved_hotels));
                        break;
                    case SAVED_PLACES:
                        setHeaderTitle(getString(R.string.my_saved_places));
                        break;
                    case SAVED_RESTAURANTS:
                        setHeaderTitle(getString(R.string.my_saved_restaurants));
                        break;
                }
                SavedListFragment savedListFragment = new SavedListFragment();

                Bundle bundle = new Bundle();
                bundle.putString(Constants.MODE, navDrawerEnum.toString());
                savedListFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.container, savedListFragment,
                        SavedListFragment.class.getSimpleName())
                        .commitAllowingStateLoss();
                break;
        }
    }

    private void showConfirmationDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(getString(R.string.app_name));
        alertDialog.setMessage(getString(R.string.exit_app_message));
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.confirm),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        alertDialog.dismiss();
                        finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.cancel),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        alertDialog.dismiss();

                    }
                });
        alertDialog.show();
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    @Override
    public void showProgress(boolean showProgress) {
    }
}
