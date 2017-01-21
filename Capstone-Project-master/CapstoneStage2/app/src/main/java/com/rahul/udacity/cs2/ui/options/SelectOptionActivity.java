package com.rahul.udacity.cs2.ui.options;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.BaseActivity;
import com.rahul.udacity.cs2.utility.Constants;

/**
 * Created by Anirudh on 04/09/15.
 */
public class SelectOptionActivity extends BaseActivity implements SelectOptionView {

    String placeid;
    String url;
    LinearLayout hotels, restaurants, tourist, flights, shopping;
    FrameLayout frameLayout;
    ImageView back;
    TextView cityname;

    @Override
    protected void initUi() {
        hotels = (LinearLayout) findViewById(R.id.hotels);
        restaurants = (LinearLayout) findViewById(R.id.restaurants);
        shopping = (LinearLayout) findViewById(R.id.shopping);
        tourist = (LinearLayout) findViewById(R.id.tourist);
        flights = (LinearLayout) findViewById(R.id.flight);
        frameLayout = (FrameLayout) findViewById(R.id.mainFrame);
        back = (ImageView) findViewById(R.id.back);
        cityname = (TextView) findViewById(R.id.cityname);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (getIntent().getExtras() != null) {
            placeid = getIntent().getStringExtra(Constants.PLACE_ID);
            String name = getIntent().getStringExtra(Constants.NAME);
            cityname.setText(name);
        }

        url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + placeid + "&key=" + getString(R.string.api_key);
        SelectOptionPresenter selectOptionPresenter = new SelectOptionPresenter(this);
        selectOptionPresenter.getPlaceDetail(url);

        restaurants.setOnClickListener(this);
        /*restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectOptionActivity.this, RestaurantListActivity.class);
                i.putExtra("lat", lat);
                i.putExtra("lng", lng);
                startActivity(i);
                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);

            }
        });

        tourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectOptionActivity.this, TouristMainActivity.class);
                i.putExtra("lat", lat);
                i.putExtra("lng", lng);
                startActivity(i);
                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
            }
        });

        hotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectOptionActivity.this, HotelListActivity.class);
                i.putExtra("lat", lat);
                i.putExtra("lng", lng);
                startActivity(i);
                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
            }
        });

        flights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectOptionActivity.this, FlightActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
            }
        });

        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectOptionActivity.this, ShoppingActivity.class);
                i.putExtra("lat", lat);
                i.putExtra("lng", lng);
                startActivity(i);
                overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
            }
        });*/
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.restaurants:
                break;
        }
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_select_option;
    }


    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void showProgress(boolean showProgress) {
        showProgressDialog(showProgress);
    }
}
