package com.rahul.udacity.cs2.ui.reviews;

import android.content.Context;
import android.widget.ListView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.ApplicationController;
import com.rahul.udacity.cs2.base.BaseActivity;
import com.rahul.udacity.cs2.model.ReviewModel;
import com.rahul.udacity.cs2.utility.Constants;

import java.util.ArrayList;

public class ReviewActivity extends BaseActivity implements ReviewsView {

    String place_id;

    ListView listView;
    ReviewPresenter reviewPresenter;

    @Override
    protected void onResume() {
        super.onResume();
        Tracker t = ApplicationController.getApplicationInstance().getTracker();
        t.setScreenName("Review Screen");
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    protected void initUi() {
        setBackButtonEnabled();
        reviewPresenter = new ReviewPresenter(this);
        listView = (ListView) findViewById(R.id.list);
        place_id = getIntent().getStringExtra(Constants.PLACE_ID);
        String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + place_id
                + "&key=" + getString(R.string.api_key);

        reviewPresenter.getReviews(url);
    }

    @Override
    protected int getLayoutById() {
        return R.layout.layout_review;
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void showProgress(boolean showProgress) {
        showProgressDialog(showProgress);
    }

    @Override
    public void setData(ArrayList<ReviewModel> reviewModelArrayList) {
        showProgress(false);
        ReviewAdapter adapter = new ReviewAdapter(this, reviewModelArrayList);
        listView.setAdapter(adapter);
    }
}
