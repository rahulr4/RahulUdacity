package com.rahul.udacity.cs2.ui.navdrawer;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.ui.common.ApiListener;
import com.rahul.udacity.cs2.utility.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class NavDrawerPresenter implements ApiListener {

    // Referencing any class that implements the ILoginView interface provides greater flexibility
    private NavDrawerView view;

    NavDrawerPresenter(NavDrawerView loginView) {
        this.view = loginView;
    }

    void doLogout() {
        showLogoutDialog();
    }

    private void showLogoutDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(getViewContext()).create();
        alertDialog.setTitle(getViewContext().getString(R.string.app_name));
        alertDialog.setMessage(getViewContext().getString(R.string.logout_confirmation_alert));
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getViewContext().getString(R.string.confirm),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        Utility.clearAllSharedPrefData(getViewContext());
                        onApiSuccess(null);
                        alertDialog.dismiss();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getViewContext().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        alertDialog.dismiss();

                    }
                });
        alertDialog.show();
    }


    @Override
    public void onApiSuccess(ArrayList<LinkedHashMap> response) {
        view.doLogout();
    }

    @Override
    public Context getViewContext() {
        return view.getViewContext();
    }
}
