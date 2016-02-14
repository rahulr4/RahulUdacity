package com.app.rahul.popularmovies.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.app.rahul.popularmovies.ApplicationController;
import com.app.rahul.popularmovies.R;
import com.app.rahul.popularmovies.adapter.MoviesListAdapter;
import com.app.rahul.popularmovies.model.movie_api.MoviesResponseBean;
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
 * Launcher activity for PopularMovies App
 */
public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ProgressBar mProgressBar;
    private GridView mGridView;
    private ArrayList<MoviesResponseBean.MoviesResult> moviesResultsList = new ArrayList<>();
    private int mPagination = 1;
    EndlessScrollListener mEndlessScrollListener = new EndlessScrollListener() {
        @Override
        public void onLoadMore(int page, int totalItemsCount) {
            if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                mPagination = mPagination + 1;
                getMoviesList(View.GONE);
            } else
                mSnackBar = SnackBarBuilder.make(getWindow().getDecorView(), getString(R.string.no_internet_connction)).build();

        }
    };
    private MoviesListAdapter mAdapter;
    private String mSortByParam = AppConstants.POPULARITY_DESC;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean isSortApplied;

    @Override
    public int getLayoutById() {
        return R.layout.activity_main;
    }

    @Override
    public void initUi() {

        mGridView = (GridView) findViewById(R.id.popular_movies_gridview);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mProgressBar = Utility.getProgressBarInstance(this, R.id.circular_progress_bar);
        getMoviesList(View.VISIBLE);
    }

    private void getMoviesList(int progressBarVisibility) {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            mProgressBar.setVisibility(progressBarVisibility);

            HashMap<String, String> stringHashMap = new HashMap<>();
            stringHashMap.put(AppConstants.PARAM_SORT_BY, mSortByParam);
            stringHashMap.put(AppConstants.PARAM_API_KEY, AppConstants.API_KEY);
            stringHashMap.put(AppConstants.PARAM_PAGE, mPagination + "");

            Call<MoviesResponseBean> beanCall = AppRetrofit.getInstance().getApiServices().apiMoviesList(stringHashMap);
            beanCall.enqueue(new Callback<MoviesResponseBean>() {
                @Override
                public void onResponse(Response<MoviesResponseBean> response, Retrofit retrofit) {
                    mProgressBar.setVisibility(View.GONE);
                    MoviesResponseBean responseBean = response.body();
                    moviesResultsList.addAll(responseBean.getResults());

                    if (mPagination == 1) {
                        mGridView.setOnScrollListener(mEndlessScrollListener);
                    }

                    if (mAdapter == null) {
                        mAdapter = new MoviesListAdapter(MainActivity.this, moviesResultsList);
                        mGridView.setAdapter(mAdapter);
                    } else {
                        mAdapter.notifyDataSetChanged();
                    }
                    if (responseBean.getResults().size() == 0) {
                        mGridView.setOnScrollListener(null);
                    }

                    if (responseBean.getResults().isEmpty())
                        Lg.i("Retro", response.toString());
                }

                @Override
                public void onFailure(Throwable t) {
                    Lg.i("Retro", t.toString());
                }
            });
        } else {
            mSnackBar = SnackBarBuilder.make(getWindow().getDecorView(), getString(R.string.no_internet_connction))
                    .setActionText(getString(R.string.retry))
                    .onSnackBarClicked(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getMoviesList(View.VISIBLE);
                        }
                    })
                    .build();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.most_popular:
                isSortApplied = true;
                mSortByParam = AppConstants.POPULARITY_DESC;
                onRefresh();
                return true;
            case R.id.highest_rated:
                mSortByParam = AppConstants.HIGHEST_RATED;
                isSortApplied = true;
                onRefresh();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            mPagination = 1;
            mGridView.setOnScrollListener(null);
            moviesResultsList.clear();
            try {
                mAdapter.notifyDataSetChanged();
                mAdapter = null;
            } catch (Exception ignored) {
            }

            if (isSortApplied) {
                isSortApplied = false;
            } else {
                mSortByParam = AppConstants.POPULARITY_DESC;
            }
            getMoviesList(View.VISIBLE);
        } else {
            mSnackBar = SnackBarBuilder.make(getWindow().getDecorView(), getString(R.string.no_internet_connction)).build();
        }
    }
}
