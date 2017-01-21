package com.rahul.udacity.cs2.ui.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.BaseFragment;
import com.rahul.udacity.cs2.model.HomeModel;
import com.rahul.udacity.cs2.ui.home.adapters.HomeAdapter;
import com.rahul.udacity.cs2.ui.home.adapters.PlaceAutoCompleteAdapter;
import com.rahul.udacity.cs2.ui.options.SelectOptionActivity;
import com.rahul.udacity.cs2.utility.Constants;

import java.util.ArrayList;

/**
 * Created by rahulgupta on 16/11/16.
 */

public class HomeFragment extends BaseFragment implements HomeView {

    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void initUi() {
        HomePresenter homePresenter = new HomePresenter(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.places_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<HomeModel> homePresenterList = homePresenter.createList();
        recyclerView.setAdapter(new HomeAdapter(homePresenterList));

        AutoCompleteTextView searchAutoCompleteTv = (AutoCompleteTextView) findViewById(R.id.search_auto_tv);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .build();

        final PlaceAutoCompleteAdapter mAdapter = new PlaceAutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1,
                mGoogleApiClient, BOUNDS_GREATER_SYDNEY, null);
        searchAutoCompleteTv.setAdapter(mAdapter);

        searchAutoCompleteTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final PlaceAutoCompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(i);
                final String placeId = String.valueOf(item.placeId);

                Intent i1 = new Intent(getActivity(), SelectOptionActivity.class);
                i1.putExtra(Constants.PLACE_ID, placeId);
                i1.putExtra(Constants.NAME, item.description);
                startActivity(i1);

            }
        });
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

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }
}
