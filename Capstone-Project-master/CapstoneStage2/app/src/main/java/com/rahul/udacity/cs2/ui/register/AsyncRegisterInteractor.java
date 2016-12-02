package com.rahul.udacity.cs2.ui.register;

import android.app.Activity;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.ApplicationController;
import com.rahul.udacity.cs2.model.RequestBean;
import com.rahul.udacity.cs2.ui.common.ApiListener;
import com.rahul.udacity.cs2.utility.Utility;

class AsyncRegisterInteractor {

    void validateCredentialsAsync(final ApiListener listener, RequestBean requestBean) {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {

            //TODO Api logic for register
        } else
            Utility.showSnackBar((Activity) listener.getViewContext(),
                    listener.getViewContext().getString(R.string.no_internet_connection));
        // SOAP Api Logic
    }
}
