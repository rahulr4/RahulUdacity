package com.app.rahul.popularmovies.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.app.rahul.popularmovies.R;


/**
 * Created by rahul on 28/1/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Snackbar mSnackBar;
    private Toolbar toolbar;

    @Override
    protected void onPause() {
        super.onPause();
        if (mSnackBar != null)
            mSnackBar.dismiss();
    }

    protected void setToolBarColor(int resColor) {
        if (toolbar != null)
            toolbar.setBackgroundColor(resColor);
    }

    protected void setToolBarTextColor(int resColor) {
        if (toolbar != null)
            toolbar.setTitleTextColor(resColor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutById());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);
        initUi();

    }

    /**
     * Method to return layout resource id
     *
     * @return Resource id of the layout to inflate
     */
    public abstract int getLayoutById();

    /**
     * Method to initialize view parameters
     */
    public abstract void initUi();

}
