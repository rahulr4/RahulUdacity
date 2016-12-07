package com.rahul.udacity.cs2.ui.register;

import android.app.Activity;
import android.content.Context;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.ApplicationController;
import com.rahul.udacity.cs2.model.RequestBean;
import com.rahul.udacity.cs2.ui.common.ApiListener;
import com.rahul.udacity.cs2.utility.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

class RegisterPresenter implements ApiListener {

    private RegisterView view;
    private AsyncRegisterInteractor interactor;

    RegisterPresenter(RegisterView loginView) {
        this.view = loginView;
        this.interactor = new AsyncRegisterInteractor();
    }

    void attemptSignUp(RequestBean requestBean) {
        if (isValid(requestBean)) {
            if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
                view.showProgress(true);
                interactor.validateCredentialsAsync(this, requestBean);
            } else
                Utility.showSnackBar((Activity) getViewContext(),
                        getViewContext().getString(R.string.no_internet_connection));
        }
    }

    private boolean isValid(RequestBean requestBean) {
        if (!Utility.validateUsernamaeFields(requestBean.getUsername(),
                view.getViewContext(), R.string.username_required)) {
            return false;
        } else if (!Utility.validateInputFields(requestBean.getPassword(),
                view.getViewContext(), R.string.password_required)) {
            return false;

        } else if (!Utility.validatePasswordSameFields(requestBean.getPassword(),
                requestBean.getRePassword(),
                view.getViewContext(), R.string.password_match_validation)) {
            return false;
        } else if (!Utility.validateEmailFields(requestBean.getEmail(),
                view.getViewContext(), R.string.email_required)) {
            return false;
        } else if (!Utility.validateMinSixRegex(requestBean.getPassword(),
                view.getViewContext(), R.string.password_min_required)) {
            return false;
        }

        return true;
    }

    @Override
    public void onApiSuccess(ArrayList<LinkedHashMap> response) {
        view.showProgress(false);
        if (response.equalsIgnoreCase("Failed")) {
            view.registerFailed(response);
            Utility.showSnackBar((Activity) getViewContext(), getViewContext().getString(R.string.registration_failed));
        } else if (response.equalsIgnoreCase("UserExists")) {
            view.registerFailed(response);
            Utility.showSnackBar((Activity) getViewContext(), getViewContext().getString(R.string.user_exists));
        } else
            view.navigateHome();
    }

    @Override
    public Context getViewContext() {
        return view.getViewContext();
    }
}
