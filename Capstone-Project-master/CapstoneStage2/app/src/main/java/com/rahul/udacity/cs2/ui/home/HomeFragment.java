package com.rahul.udacity.cs2.ui.home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.BaseFragment;
import com.rahul.udacity.cs2.model.HomeModel;
import com.rahul.udacity.cs2.ui.home.adapters.HomeAdapter;

import java.util.ArrayList;

/**
 * Created by rahulgupta on 16/11/16.
 */

public class HomeFragment extends BaseFragment implements HomeView {
    private String TAG = HomeFragment.class.getSimpleName();
    private HomePresenter homePresenter;


    @Override
    protected void initUi() {
        homePresenter = new HomePresenter(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.places_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<HomeModel> homePresenterList = homePresenter.createList();
        recyclerView.setAdapter(new HomeAdapter(homePresenterList));
    }

    @Override
    protected int getLayoutById() {
        return R.layout.fragment_home;
    }


    @Override
    public Context getViewContext() {
        return mContext;
    }

    @Override
    public void showProgress(boolean showProgress) {
        showProgressDialog(showProgress);
    }

    @Override
    public void onNavigationDrawerItemSelected(NavDrawerEnum navDrawerEnum) {

    }
}
