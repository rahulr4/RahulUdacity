package com.rahul.udacity.cs2.ui.saved;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.ApplicationController;
import com.rahul.udacity.cs2.base.BaseFragment;
import com.rahul.udacity.cs2.database.DatabaseSave;
import com.rahul.udacity.cs2.database.TravelProvider;
import com.rahul.udacity.cs2.model.PlaceListDetail;
import com.rahul.udacity.cs2.ui.home.NavDrawerEnum;
import com.rahul.udacity.cs2.ui.restaurant_list.adapters.PlaceListAdapter;
import com.rahul.udacity.cs2.utility.Constants;

import java.util.ArrayList;
import java.util.List;

public class SavedListFragment extends BaseFragment implements SavedListView, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int SAVED_PLACES_LOADER_ID = 0;
    private static final int SAVED_RESTAURANT_LOADER_ID = 1;
    private static final int SAVED_HOTEL_LOADER_ID = 2;
    TextView noDataTv;
    DatabaseSave db;
    private RecyclerView recyclerView;
    private SavedListPresenter savedListPresenter;

    @Override
    public void onResume() {
        super.onResume();
        Tracker t = ApplicationController.getApplicationInstance().getTracker();
        t.setScreenName("Saved Places Screen");
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    protected void initUi() {

        db = new DatabaseSave(mContext);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        int columnCount = 1;
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);

        noDataTv = (TextView) findViewById(R.id.nodata);
        noDataTv.setVisibility(View.GONE);

        savedListPresenter = new SavedListPresenter(this);

        String mode = getArguments().getString(Constants.MODE, "");
        showProgress(false);
        NavDrawerEnum navDrawerEnum = NavDrawerEnum.valueOf(mode);
        switch (navDrawerEnum) {
            case SAVED_PLACES:

                getActivity().getSupportLoaderManager().initLoader(
                        SAVED_PLACES_LOADER_ID, null, this);
                break;
            case SAVED_RESTAURANTS:

                getActivity().getSupportLoaderManager().initLoader(
                        SAVED_RESTAURANT_LOADER_ID, null, this);
                break;

            case SAVED_HOTELS:
                getActivity().getSupportLoaderManager().initLoader(
                        SAVED_HOTEL_LOADER_ID, null, this);

        }
    }

    @Override
    protected int getLayoutById() {
        return R.layout.fragment_saved_list;
    }

    @Override
    public void setData(List<PlaceListDetail> placeListDetailList) {
        PlaceListAdapter placeListAdapter = new PlaceListAdapter(mContext, placeListDetailList, Constants.TYPE_PLACES);
        recyclerView.setAdapter(placeListAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getSupportLoaderManager().destroyLoader(SAVED_PLACES_LOADER_ID);

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
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        Uri uri = null;
        if (loaderId == SAVED_PLACES_LOADER_ID) {
            uri = TravelProvider.withPlaceId();
        } else if (loaderId == SAVED_RESTAURANT_LOADER_ID) {
            uri = TravelProvider.withResId();
        } else if (loaderId == SAVED_HOTEL_LOADER_ID) {
            uri = TravelProvider.wihtHotelId();
        }
        return new CursorLoader(mContext, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        ArrayList<String> List = new ArrayList<>();
        // Select All Query
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    // Adding contact to list
                    switch (loader.getId()) {
                        case SAVED_PLACES_LOADER_ID:
                            List.add(cursor.getString(cursor.getColumnIndex(DatabaseSave.KEY_PLACE_ID)));
                            break;
                        case SAVED_RESTAURANT_LOADER_ID:
                            List.add(cursor.getString(cursor.getColumnIndex(DatabaseSave.KEY_RESTAURANTS_ID)));
                            break;
                        case SAVED_HOTEL_LOADER_ID:
                            List.add(cursor.getString(cursor.getColumnIndex(DatabaseSave.KEY_HOTELS_ID)));
                            break;
                    }

                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        savedListPresenter.getPlaceDetail(List);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        loader.reset();

    }

}
