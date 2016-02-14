package com.app.rahul.popularmovies.utility;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ProgressBar;

import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;

/**
 * Created by rahul on 8/2/16.
 */
public class Utility {

    /**
     * Static method to get an instance of material styled progress bar
     *
     * @param mContext Context of the calling class
     * @param resId    Resource Id of the progress bar
     * @return An instance of MaterialProgressBar
     */
    public static ProgressBar getProgressBarInstance(Context mContext, int resId) {

        ProgressBar nonBlockingProgressBar = (ProgressBar) ((Activity) mContext).getWindow().findViewById(resId);
        if (nonBlockingProgressBar != null)
            nonBlockingProgressBar.setIndeterminateDrawable(new IndeterminateProgressDrawable(mContext));
        return nonBlockingProgressBar;
    }

    /**
     * Static method to check network availability
     *
     * @param context Context of the calling class
     */

    public static boolean getNetworkState(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}