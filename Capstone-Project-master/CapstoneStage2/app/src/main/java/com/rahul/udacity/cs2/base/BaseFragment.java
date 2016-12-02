package com.rahul.udacity.cs2.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.qatarcool.R;
import com.app.qatarcool.custom_ui.MaterialProgressDialog;
import com.app.qatarcool.utils.Utility;

public abstract class BaseFragment extends Fragment {
    private View view;
    protected Context mContext;
    private MaterialProgressDialog mMaterialProgressDialog;
    protected Snackbar mSnackBar;
    protected ImageView mMaterialProgressBar;
    protected ViewDataBinding viewDataBinding;

    @Override
    public void onDetach() {
        super.onDetach();
        if (mSnackBar != null) {
            mSnackBar.dismiss();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutById(), container, false);
        if (viewDataBinding != null)
            return viewDataBinding.getRoot();
        else
            return inflater.inflate(getLayoutById(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        mMaterialProgressBar = (ImageView) view.findViewById(R.id.circular_progress_bar);
        Utility.addRotationAnimation(mMaterialProgressBar);

        mMaterialProgressDialog = Utility.getProgressDialogInstance(mContext);

        initUi();
    }

    protected View findViewById(int resId) {
        return view.findViewById(resId);
    }

    protected abstract void initUi();

    protected abstract int getLayoutById();

    public void showProgressDialog(boolean iShow) {
        try {
            if (mMaterialProgressDialog != null) {
                if (iShow)
                    mMaterialProgressDialog.show();
                else
                    mMaterialProgressDialog.dismiss();
            }
        } catch (Exception ignored) {
        }
    }

    public void showProgressBar(boolean iShow) {

        if (mMaterialProgressBar != null) {
            if (iShow)
                mMaterialProgressBar.setVisibility(View.VISIBLE);
            else
                mMaterialProgressBar.setVisibility(View.GONE);
        }

    }

    public void onMenuItemClick(MenuItem item) {

    }
}


