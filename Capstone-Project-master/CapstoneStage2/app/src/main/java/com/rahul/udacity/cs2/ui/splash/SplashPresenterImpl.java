
package com.rahul.udacity.cs2.ui.splash;


import android.app.Activity;
import android.os.Handler;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.ApplicationController;
import com.rahul.udacity.cs2.utility.Constants;
import com.rahul.udacity.cs2.utility.Utility;

class SplashPresenterImpl implements SplashPresenter {

    private SplashView loginView;

    SplashPresenterImpl(SplashView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void checkLoggedIn() {
        if (!ApplicationController.getApplicationInstance().isNetworkConnected())
            Utility.showSnackBar((Activity) loginView.getViewContext(),
                    loginView.getViewContext().getString(R.string.no_internet_connection));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isLoggedIn = Utility.getBooleanSharedPreference(ApplicationController.getApplicationInstance(),
                        Constants.PREFS_LOGGED_IN);
                if (!isLoggedIn) {
                    onFail();
                } else
                    onSuccess();
            }
        }, 2000);

    }

    void onDestroy() {
        loginView = null;
    }


    @Override
    public void onSuccess() {
        if (loginView != null) {
            loginView.navigateHome();
        }
    }

    @Override
    public void onFail() {
        if (loginView != null) {
            loginView.navigateLogin();
        }
    }

}
