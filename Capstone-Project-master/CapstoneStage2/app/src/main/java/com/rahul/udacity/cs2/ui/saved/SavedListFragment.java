package com.rahul.udacity.cs2.ui.saved;

import android.content.Context;
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
import com.rahul.udacity.cs2.model.PlaceListDetail;
import com.rahul.udacity.cs2.ui.home.NavDrawerEnum;
import com.rahul.udacity.cs2.ui.restaurant_list.adapters.PlaceListAdapter;
import com.rahul.udacity.cs2.utility.Constants;

import java.util.List;

public class SavedListFragment extends BaseFragment implements SavedListView {

    TextView noDataTv;
    DatabaseSave db;


    private RecyclerView recyclerView;

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

        SavedListPresenter savedListPresenter = new SavedListPresenter(this);

        String mode = getArguments().getString(Constants.MODE, "");
        showProgress(false);
        NavDrawerEnum navDrawerEnum = NavDrawerEnum.valueOf(mode);
        switch (navDrawerEnum) {
            case SAVED_PLACES:

                if (!db.getAllPlaces().isEmpty()) {
                    savedListPresenter.getPlaceDetail(db.getAllPlaces());
                } else {
                    noDataTv.setVisibility(View.VISIBLE);
                }

                break;
            case SAVED_RESTAURANTS:

                if (!db.getAllRes().isEmpty()) {
                    savedListPresenter.getPlaceDetail(db.getAllRes());
                } else {
                    noDataTv.setVisibility(View.VISIBLE);
                }

                break;

            case SAVED_HOTELS:

                if (!db.getAllHotels().isEmpty()) {
                    savedListPresenter.getPlaceDetail(db.getAllHotels());
                } else {
                    noDataTv.setVisibility(View.VISIBLE);
                }
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
    public Context getViewContext() {
        return mContext;
    }

    @Override
    public void showProgress(boolean showProgress) {
        showProgressBar(showProgress);
    }
}
