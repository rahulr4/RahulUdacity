package com.rahul.udacity.cs2.ui.restaurant_list.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.BaseFragment;
import com.rahul.udacity.cs2.model.PlaceListDetail;
import com.rahul.udacity.cs2.ui.restaurant_list.adapters.PlaceListAdapter;
import com.rahul.udacity.cs2.utility.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anirudh on 06/09/15.
 */
public class RestaurantsFragment extends BaseFragment implements RestaurantView {

    private PlaceListAdapter placeListAdapter;
    private List<PlaceListDetail> placeListDetailList = new ArrayList<>();

    private RestaurantPresenter restaurantPresenter;


    @Override
    protected void initUi() {
        Double latitude = getActivity().getIntent().getDoubleExtra(Constants.LATITUDE, 0.0);
        Double longitude = getActivity().getIntent().getDoubleExtra(Constants.LONGTITUDE, 0.0);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        int columnCount = 1;
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);

        placeListAdapter = new PlaceListAdapter(getActivity(), placeListDetailList);
        recyclerView.setAdapter(placeListAdapter);

        //url to get list of places
        String sb = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + "location=" + latitude + "," + longitude +
                "&types=restaurant" +
                "&radius=5000" +
                "&rankby=prominence" +
                "&key=" + getString(R.string.api_key);

        restaurantPresenter.getPlaceList(sb);
    }

    @Override
    protected int getLayoutById() {
        return R.layout.fragment_restaurants;
    }

    @Override
    public Context getViewContext() {
        return mContext;
    }

    @Override
    public void showProgress(boolean showProgress) {
        showProgressBar(showProgress);
    }

    @Override
    public void setData(ArrayList<PlaceListDetail> placeListDetailArrayList) {

    }
}
