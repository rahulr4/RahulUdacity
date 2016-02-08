package com.app.rahul.popularmovies.activity;

import android.view.Menu;
import android.widget.Toast;

import com.app.rahul.popularmovies.R;


/**
 * Launcher activity for PopularMovies App
 */
public class MainActivity extends BaseActivity {

    private Toast mLastToast;

    @Override
    public int getLayoutById() {
        return R.layout.activity_main;
    }

    @Override
    public void initUi() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
