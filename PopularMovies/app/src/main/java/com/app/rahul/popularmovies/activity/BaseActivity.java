package com.app.rahul.popularmovies.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.app.rahul.popularmovies.R;
import com.app.rahul.popularmovies.utility.MaterialProgressDialog;
import com.app.rahul.popularmovies.utility.Utility;


/**
 * Created by rahul on 28/1/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Snackbar mSnackBar;
    private Toolbar toolbar;
    private MaterialProgressDialog mProgressDialog;
    protected View mParent;

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
        mParent = findViewById(R.id.parent);
        mProgressDialog = Utility.getProgressDialogInstance(this);
        if (toolbar != null)
            setSupportActionBar(toolbar);
        initUi();

    }

    protected void showProgressDialog(boolean isShow) {
        if (mProgressDialog != null) {
            if (isShow)
                mProgressDialog.show();
            else
                mProgressDialog.dismiss();
        }
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
