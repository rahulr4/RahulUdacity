package com.app.rahul.popularmovies.activity;

import android.view.MenuItem;

import com.app.rahul.popularmovies.R;
import com.app.rahul.popularmovies.fragment.MoviesDetailFragment;

/**
 * Created by Rahul on 2/21/2016.
 */
public class MoviesDetailActivity extends BaseActivity {

    @Override
    public int getLayoutById() {
        return R.layout.activity_movies_detail;
    }

    @Override
    public void initUi() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        MoviesDetailFragment moviesDetailFragment = new MoviesDetailFragment();
        moviesDetailFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detail_container, moviesDetailFragment).commit();
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
}
