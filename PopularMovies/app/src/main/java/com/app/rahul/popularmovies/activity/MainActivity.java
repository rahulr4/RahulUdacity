package com.app.rahul.popularmovies.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.app.rahul.popularmovies.R;
import com.app.rahul.popularmovies.utility.Utility;

/**
 * Launcher activity for PopularMovies App
 */
public class MainActivity extends BaseActivity {

    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;

    @Override
    public int getLayoutById() {
        return R.layout.activity_main;
    }

    @Override
    public void initUi() {

        mListView = (ListView) findViewById(R.id.popular_movies_lv);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        mProgressBar = Utility.getProgressBarInstance(this, R.id.circular_progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        getMoviesList();
    }

    private void getMoviesList() {

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
                return true;
            case R.id.highest_rated:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
