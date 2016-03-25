package com.app.rahul.popularmovies.utility;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.app.rahul.popularmovies.ApplicationController;


/**
 * Created by omji on 30/9/15.
 * Class to manage network change, right now we are not using this class.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (Utility.getNetworkState(context)) {
            ApplicationController.getApplicationInstance().setIsNetworkConnected(true);
            Lg.i("Network Receiver", "connected");
        } else {
            ApplicationController.getApplicationInstance().setIsNetworkConnected(false);
            Lg.i("Network Receiver", "disconnected");
        }
    }
}
