package com.rahul.udacity.cs2.ui.hotels;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.ApplicationController;
import com.rahul.udacity.cs2.base.BaseActivity;
import com.rahul.udacity.cs2.model.PlaceListDetail;
import com.rahul.udacity.cs2.ui.restaurant_list.adapters.PlaceListAdapter;
import com.rahul.udacity.cs2.utility.Constants;

import java.util.List;

public class HotelListActivity extends BaseActivity implements HotelView {

    private RecyclerView recyclerView;

    @Override
    protected void onResume() {
        super.onResume();
        Tracker t = ApplicationController.getApplicationInstance().getTracker();
        t.setScreenName("Hotel Screen");
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    protected void initUi() {
        setBackButtonEnabled();
        AdView mAdView1 = (AdView) findViewById(R.id.adView1);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice("EAA80D31926665B9575A0981DE305DB8")
                .build();
        mAdView1.loadAd(adRequest);

        Double latitude = getIntent().getDoubleExtra(Constants.LATITUDE, 0.0);
        Double longitude = getIntent().getDoubleExtra(Constants.LONGTITUDE, 0.0);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        int columnCount = 1;
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);

        //url to get list of places
        String sb = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + "location=" + latitude + "," + longitude +
                "&types=lodging" +
                "&radius=5000" +
                "&rankby=prominence" +
                "&key=" + getString(R.string.api_key);

        HotelListPresenter listPresenter = new HotelListPresenter(this);
        listPresenter.getPlaceList(sb);
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_hotels_list;
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
    public void setData(List<PlaceListDetail> placeListDetailArrayList) {
        PlaceListAdapter placeListAdapter = new PlaceListAdapter(this, placeListDetailArrayList, Constants.TYPE_HOTELS);
        recyclerView.setAdapter(placeListAdapter);
    }
}
