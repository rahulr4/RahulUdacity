package com.rahul.udacity.cs2.ui.login;

import android.app.Activity;
import android.support.v4.util.Pair;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.ApplicationController;
import com.rahul.udacity.cs2.model.RequestBean;
import com.rahul.udacity.cs2.ui.common.ApiListener;
import com.rahul.udacity.cs2.utility.Utility;

import java.util.ArrayList;
import java.util.List;

class AsyncLoginInteractor {

    /******************************************************************************************
     * An Interactor helps models cross application boundaries such as networks or serialization
     * This LoginInteractor knows nothing about a UI or the LoginPresenter
     * Because this is an asynchronous call it will call back on the OnLoginFinishedListener when complete
     * ******************************************************************************************
     */

    void validateCredentialsAsync(final ApiListener listener, RequestBean requestBean) {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            List<Pair<String, String>> properties = new ArrayList<>();
            properties.add(new Pair<>("username", requestBean.getUsername()));
            properties.add(new Pair<>("password", requestBean.getPassword()));

            //TODO Api Logic goes here
        } else
            Utility.showSnackBar((Activity) listener.getViewContext(),
                    listener.getViewContext().getString(R.string.no_internet_connection));
        // SOAP Api Logic
    }
}
