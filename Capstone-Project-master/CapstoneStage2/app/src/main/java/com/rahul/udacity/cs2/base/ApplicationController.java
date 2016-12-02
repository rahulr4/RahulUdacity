package com.rahul.udacity.cs2.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.app.qatarcool.R;
import com.app.qatarcool.database.DatabaseHelper;
import com.app.qatarcool.utils.Utility;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by rahulgupta on 08/11/16.
 */

public class ApplicationController extends Application implements Application.ActivityLifecycleCallbacks {
    private static ApplicationController mInstance;
    private boolean mIsNetworkConnected;
    private Activity currentActivity;

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

        Fabric.with(this, new Crashlytics());
        mInstance = this;
        mIsNetworkConnected = Utility.getNetworkState(this);
        DatabaseHelper.getDatabaseHelperInstance(this);
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
}
