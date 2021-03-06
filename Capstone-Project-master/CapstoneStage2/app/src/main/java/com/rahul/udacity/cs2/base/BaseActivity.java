package com.rahul.udacity.cs2.base;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahul.media.model.Define;
import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.custom_ui.MaterialProgressDialog;
import com.rahul.udacity.cs2.utility.Utility;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by rahulgupta on 08/11/16.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected ViewDataBinding viewDataBinding;
    private ImageView mMaterialProgressBar;
    private MaterialProgressDialog blockingProgressDialog;
    private Toolbar headerToolBar;
    private TextView titleTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutById());
        Define.ACTIONBAR_COLOR = getResources().getColor(R.color.colorPrimary);
        // Always cast your custom Toolbar here, and set it as the ActionBar.
        headerToolBar = (Toolbar) findViewById(R.id.toolbar);
        if (headerToolBar != null) {
            headerToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onHeaderBackClicked();
                }
            });
        }

        titleTv = (TextView) findViewById(R.id.title_tv);
        if (titleTv != null) {
            titleTv.setText(getTitle());
        }
        mMaterialProgressBar = (ImageView) findViewById(R.id.circular_progress_bar);
        Utility.addRotationAnimation(mMaterialProgressBar);

        blockingProgressDialog = Utility.getProgressDialogInstance(this);
        initUi();
    }

    public void setHeaderTitle(String title) {
        if (titleTv != null) {
            titleTv.setText(title);
        }
    }

    protected void setBackButtonEnabled() {
        if (headerToolBar != null) {
            headerToolBar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        }
    }

    protected Toolbar getHeaderToolBar() {
        return headerToolBar;
    }

    protected Activity getActivity() {
        return BaseActivity.this;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        switch (item.getItemId()) {
            case android.R.id.home:
                onHeaderBackClicked();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onHeaderBackClicked() {
        finish();
    }

    protected abstract void initUi();

    protected abstract int getLayoutById();

    public void showProgressBar(boolean iShow) {

        if (mMaterialProgressBar != null) {
            if (iShow)
                mMaterialProgressBar.setVisibility(View.VISIBLE);
            else
                mMaterialProgressBar.setVisibility(View.GONE);
        }

    }

    public void showProgressDialog(boolean isShow) {
        try {
            if (blockingProgressDialog != null) {
                if (isShow)
                    blockingProgressDialog.show();
                else
                    blockingProgressDialog.dismiss();
            }
        } catch (Exception ignored) {
            Log.i("Error", ignored.getMessage());
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View view) {
    }
}
