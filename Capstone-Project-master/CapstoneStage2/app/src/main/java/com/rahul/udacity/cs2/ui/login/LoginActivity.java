package com.rahul.udacity.cs2.ui.login;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.BaseActivity;
import com.rahul.udacity.cs2.model.RequestBean;
import com.rahul.udacity.cs2.ui.register.RegisterActivity;
import com.rahul.udacity.cs2.utility.Constants;
import com.rahul.udacity.cs2.utility.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;


/**
 * Created by rahulgupta on 10/11/16.
 */

public class LoginActivity extends BaseActivity implements LoginView {

    private EditText mUserNameEd, mPasswordEd;
    private LoginPresenter mPresenter;

    /*********************************************************************************************
     * - LoginActivity ONLY knows how to display views and sending events and data to the presenter
     * - LoginActivity doesn't know anything about the model (SynchronousLoginInteractor)
     * - The only changes to the LoginActivity to allow for asynchronous behavior was to add a ProgressDialog
     * *******************************************************************************************
     */

    @Override
    protected void initUi() {
        mUserNameEd = (EditText) findViewById(R.id.username_ed);
        mPasswordEd = (EditText) findViewById(R.id.password_ed);
        findViewById(R.id.login_tv).setOnClickListener(this);
        findViewById(R.id.register_tv).setOnClickListener(this);

        mPresenter = new LoginPresenter(this);
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.login_tv:
                RequestBean requestBean = new RequestBean();
                requestBean.setUsername(mUserNameEd.getText().toString().trim());
                requestBean.setPassword(mPasswordEd.getText().toString().trim());
                mPresenter.attemptLogin(requestBean);
                break;
            case R.id.register_tv:
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void navigateHome(ArrayList<LinkedHashMap> response) {

        LinkedHashMap linkedHashMap = response.get(0);
        Utility.putBooleanValueInSharedPreference(getActivity(), Constants.PREFS_LOGGED_IN, true);
        Utility.putStringValueInSharedPreference(getActivity(), Constants.PREFS_USER_NAME, (String) linkedHashMap.get("username"));
        Utility.putStringValueInSharedPreference(getActivity(), Constants.PREFS_PASSWORD, (String) linkedHashMap.get("password"));

        /*Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);*/
    }

    @Override
    public void loginFailed(String response) {
        Utility.showSnackBar(getActivity(), getString(R.string.enter_valid_credentials));
        mUserNameEd.setText("");
        mPasswordEd.setText("");
        mUserNameEd.requestFocus();
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void showProgress(boolean showProgress) {
        showProgressDialog(showProgress);
    }
}
