package com.rahul.udacity.cs2.ui.flights;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.ApplicationController;
import com.rahul.udacity.cs2.base.BaseActivity;
import com.rahul.udacity.cs2.model.FlightDetailModel;
import com.rahul.udacity.cs2.utility.Constants;

import java.util.ArrayList;

public class FlightDetailActivity extends BaseActivity implements FlightsDetailView {

    RecyclerView recyclerView;
    String from, to;

    @Override
    protected void onResume() {
        super.onResume();
        Tracker t = ApplicationController.getApplicationInstance().getTracker();
        t.setScreenName("Flight Detail Screen");
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    protected void initUi() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        if (getIntent().getExtras() != null) {
            from = getIntent().getStringExtra(Constants.FROM);
            to = getIntent().getStringExtra(Constants.TO);
        }

        int columnCount = 1;
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);

        String sb = "http://free.rome2rio.com/api/1.2/json/Search?key=" + getString(R.string.api_key) + "&oName=" + from +
                "&dName=" + to +
                "&flags=0x000FFFF0";

        FlightsDetailPresenter presenter = new FlightsDetailPresenter(this);
        presenter.getFlightDetails(sb);
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_flight_detail;
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void showProgress(boolean showProgress) {
        showProgressBar(showProgress);
    }

    @Override
    public void setData(ArrayList<FlightDetailModel> flightDetailModelArrayList) {
        FlightListAdapter flightListAdapter = new FlightListAdapter(this, flightDetailModelArrayList);
        recyclerView.setAdapter(flightListAdapter);
    }
}
