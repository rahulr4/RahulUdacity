package com.app.rahul.build_it_bigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.app.rahul.build_it_bigger.backend.myApi.MyApi;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.rahul.joke_display_lib.DisplayJokeActivity;

class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private Context mContext;
    private String mResult;
    private InterstitialAd mInterstitialAd;
    private ProgressBar mProgressBar;
    private MyApi myApiService;

    public EndpointsAsyncTask(Context context, ProgressBar progressBar) {
        this.mContext = context;
        this.mProgressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(mContext.getString(R.string.root_url_api));
            myApiService = builder.build();
        }
        try {
            return myApiService.tellJoke().execute().getJoke();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mResult = result;
        // Setting the interstitial ad
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId(mContext.getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (mProgressBar != null) {
                    mProgressBar.setVisibility(View.GONE);
                }
                mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                if (mProgressBar != null) {
                    mProgressBar.setVisibility(View.GONE);
                }
                startJokeDisplayActivity(false);
            }

            @Override
            public void onAdClosed() {
                startJokeDisplayActivity(true);
            }
        });
        AdRequest ar = new AdRequest
                .Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(mContext.getString(R.string.device_id))
                .build();
        mInterstitialAd.loadAd(ar);
    }

    private void startJokeDisplayActivity(boolean isAdLoaded) {
        Intent intent = new Intent(mContext, DisplayJokeActivity.class);
        if (!isAdLoaded)
            mResult = mContext.getString(R.string.error_occurred);

        intent.putExtra(DisplayJokeActivity.EXTRA_INTENT_JOKE, mResult);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

}