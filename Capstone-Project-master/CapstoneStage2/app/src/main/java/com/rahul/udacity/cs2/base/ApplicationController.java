package com.rahul.udacity.cs2.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.kumulos.android.Kumulos;
import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.utility.Utility;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by rahulgupta on 08/11/16.
 */

public class ApplicationController extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = ApplicationController.class.getSimpleName();
    private static ApplicationController mInstance;
    private boolean mIsNetworkConnected;
    private Activity currentActivity;
    private RequestQueue mRequestQueue;

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public static ApplicationController getApplicationInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        Kumulos.initWithAPIKeyAndSecretKey("38b9a881-da7e-49d8-9344-949aa00322c8", "TuPtUiep+fOUfUEVpGpl7yjqDWlofe2FLOok", this);

        mInstance = this;
        mIsNetworkConnected = Utility.getNetworkState(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/NeoSansCyr-Regular.ttf").setFontAttrId(R.attr.fontPath).build());
    }

    /**
     * Method to tell the state of internet connectivity
     *
     * @return State of internet
     */
    public boolean isNetworkConnected() {
        return mIsNetworkConnected;
    }

    public void setIsNetworkConnected(boolean mIsNetworkConnected) {
        this.mIsNetworkConnected = mIsNetworkConnected;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        this.currentActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}

