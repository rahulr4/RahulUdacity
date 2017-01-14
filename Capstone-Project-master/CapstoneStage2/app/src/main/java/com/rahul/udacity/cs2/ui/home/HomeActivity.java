package com.rahul.udacity.cs2.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.BaseActivity;
import com.rahul.udacity.cs2.ui.navdrawer.NavDrawerFragment;

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
            showConfirmationDialog();
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
