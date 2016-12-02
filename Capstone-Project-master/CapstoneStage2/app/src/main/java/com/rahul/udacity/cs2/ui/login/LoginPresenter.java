package com.rahul.udacity.cs2.ui.login;

import android.app.Activity;
import android.content.Context;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.ApplicationController;
import com.rahul.udacity.cs2.model.RequestBean;
import com.rahul.udacity.cs2.ui.common.ApiListener;
import com.rahul.udacity.cs2.utility.Utility;

public class LoginPresenter implements ApiListener {

    /******************************************************************************************
     * - LoginPresenter has a reference to both the View and the Interactor
     * - LoginPresenter retrieves data from the model, and notifies the view to display it.
     * - OnLoginFinishedListener adds the methods that are necessary for asynchronous callbacks which leaves the rest of the interface intact
     * ******************************************************************************************
     */

    // Referencing any class that implements the ILoginView interface provides greater flexibility
    private LoginView view;
    private AsyncLoginInteractor interactor;

    LoginPresenter(LoginView loginView) {
        this.view = loginView;
        this.interactor = new AsyncLoginInteractor();
    }

    void attemptLogin(RequestBean requestBean) {
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
        if (!Utility.validateInputFields(requestBean.getUsername(),
                view.getViewContext(), R.string.username_required)) {
            return false;
        } else if (!Utility.validateInputFields(requestBean.getPassword(),
                view.getViewContext(), R.string.password_required)) {
            return false;
        }
        return true;
    }

    @Override
    public void onApiSuccess(String response) {
        view.showProgress(false);
        if (response.equalsIgnoreCase("Failed"))
            view.loginFailed(response);
        else
            view.navigateHome();
    }

    @Override
    public Context getViewContext() {
        return view.getViewContext();
    }
}
