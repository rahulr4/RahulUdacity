package com.app.rahul.popularmovies.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.app.rahul.popularmovies.ApplicationController;
import com.app.rahul.popularmovies.R;
import com.app.rahul.popularmovies.adapter.ReviewsListAdapter;
import com.app.rahul.popularmovies.model.reviews_api.ReviewsListingResponse;
import com.app.rahul.popularmovies.network.AppRetrofit;
import com.app.rahul.popularmovies.utility.AppConstants;
import com.app.rahul.popularmovies.utility.EndlessScrollListener;
import com.app.rahul.popularmovies.utility.Lg;
import com.app.rahul.popularmovies.utility.SnackBarBuilder;
import com.app.rahul.popularmovies.utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Rahul on 2/21/2016.
 */
public class ReviewsListingActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ListView mReviewsListView;
    private int mPagination = 1;
    private ProgressBar progressBar;
    EndlessScrollListener mEndlessScrollListener = new EndlessScrollListener() {
        @Override
        public void onLoadMore(int page, int totalItemsCount) {
            if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                mPagination = mPagination + 1;
                getMovieReviews();
            } else
                mSnackBar = SnackBarBuilder.make(getWindow().getDecorView(), getString(R.string.no_internet_connction)).build();

        }
    };
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String movieId;
    private ArrayList<ReviewsListingResponse.ReviewsEntity> moviesReviewsList = new ArrayList<>();
    private ReviewsListAdapter mAdapter;

    @Override
    public int getLayoutById() {
        return R.layout.activity_reviews_listing;
    }

    @Override
    public void initUi() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        movieId = getIntent().getStringExtra(AppConstants.EXTRA_INTENT_PARCEL);
        mReviewsListView = (ListView) findViewById(R.id.reviews_listview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        progressBar = Utility.getProgressBarInstance(this, R.id.circular_progress_bar);
        getMovieReviews();
    }

    private void getMovieReviews() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            progressBar.setVisibility(View.VISIBLE);
            HashMap<String, String> stringHashMap = new HashMap<>();
            stringHashMap.put(AppConstants.PARAM_API_KEY, AppConstants.API_KEY);
            stringHashMap.put(AppConstants.PARAM_PAGE, mPagination + "");

            Call<ReviewsListingResponse> beanCall = AppRetrofit.getInstance().getApiServices().apiMovieReviews(/*movieId*/140420, stringHashMap);
            beanCall.enqueue(new Callback<ReviewsListingResponse>() {
                @Override
                public void onResponse(Response<ReviewsListingResponse> response1, Retrofit retrofit) {
                    progressBar.setVisibility(View.GONE);
                    ReviewsListingResponse responseBean = response1.body();
                    if (responseBean != null) {
                        moviesReviewsList.addAll(responseBean.getResults());

                        if (mPagination == 1) {
                            mReviewsListView.setOnScrollListener(mEndlessScrollListener);
                        }

                        if (mAdapter == null) {
                            mAdapter = new ReviewsListAdapter(ReviewsListingActivity.this, moviesReviewsList);
                            mReviewsListView.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }
                        if (responseBean.getResults().size() == 0) {
                            mReviewsListView.setOnScrollListener(null);
                        }

                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Lg.i("Retro", t.toString());
                }
            });
        } else {
            mSnackBar = SnackBarBuilder.make(mParent, getString(R.string.no_internet_connction))
                    .setActionText(getString(R.string.retry))
                    .onSnackBarClicked(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getMovieReviews();
                        }
                    })
                    .build();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return false;
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            mPagination = 1;
            mReviewsListView.setOnScrollListener(null);
            moviesReviewsList.clear();
            try {
                mAdapter.notifyDataSetChanged();
                mAdapter = null;
            } catch (Exception ignored) {
            }

            getMovieReviews();
        } else {
            mSnackBar = SnackBarBuilder.make(getWindow().getDecorView(), getString(R.string.no_internet_connction)).build();
        }
    }
}
