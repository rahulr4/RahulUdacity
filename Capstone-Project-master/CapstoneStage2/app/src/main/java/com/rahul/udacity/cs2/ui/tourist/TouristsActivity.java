package com.rahul.udacity.cs2.ui.tourist;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.ApplicationController;
import com.rahul.udacity.cs2.base.BaseActivity;
import com.rahul.udacity.cs2.model.PlaceListDetail;
import com.rahul.udacity.cs2.ui.restaurant_list.adapters.PlaceListAdapter;
import com.rahul.udacity.cs2.ui.tourist_places.TouristPlaceListActivity;
import com.rahul.udacity.cs2.utility.Constants;

import java.util.List;

public class TouristsActivity extends BaseActivity implements TouristsView {

    private Double latitude, longitude;
    FloatingActionButton category;
    AdView mAdView1;
    private RecyclerView recyclerView;

    @Override
    protected void onResume() {
        super.onResume();
        Tracker t = ApplicationController.getApplicationInstance().getTracker();
        t.setScreenName("Tourist Places Screen");
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    protected void initUi() {
        setBackButtonEnabled();
        TouristsPresenter touristsPresenter = new TouristsPresenter(this);
        mAdView1 = (AdView) findViewById(R.id.adView1);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice("EAA80D31926665B9575A0981DE305DB8")
                .build();
        mAdView1.loadAd(adRequest);

        category = (FloatingActionButton) findViewById(R.id.category);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        if (getIntent().getExtras() != null) {
            latitude = getIntent().getDoubleExtra(Constants.LATITUDE, 0.0);
            longitude = getIntent().getDoubleExtra(Constants.LONGTITUDE, 0.0);
        }

        int columnCount = 1;
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);

        String sb = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + "location=" + latitude + "," + longitude +
                "&types=places_of_interest|establishment" +
                "&radius=30000" +
                "&rankby=prominence" +
                "&key=" + getString(R.string.api_key);

        touristsPresenter.getPlaceList(sb);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TouristsActivity.this, TouristPlaceListActivity.class);
                i.putExtra(Constants.LATITUDE, latitude);
                i.putExtra(Constants.LONGTITUDE, longitude);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_tourists;
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
    public void setData(List<PlaceListDetail> placeListDetailList) {
        PlaceListAdapter placeListAdapter = new PlaceListAdapter(this, placeListDetailList, 1);
        recyclerView.setAdapter(placeListAdapter);
    }
}
