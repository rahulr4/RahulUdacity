package com.rahul.udacity.cs2.ui.navdrawer;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;

import com.rahul.udacity.cs2.ui.common.CommonView;


/**
 * Created by rahulgupta on 15/11/16.
 */
interface NavDrawerView extends CommonView {

    void setupNavDrawer(DrawerLayout drawerLayout, Context context);

    void doLogout();
}
