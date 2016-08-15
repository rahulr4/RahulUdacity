package com.example.android.sunshine.app.sync;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.wearable.companion.WatchFaceCompanion;
import android.util.Log;

import com.example.android.sunshine.app.MainActivity;
import com.example.android.sunshine.app.Utility;
import com.example.android.sunshine.app.data.WeatherContract;
import com.google.android.apps.muzei.api.Artwork;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SunshineWatchfaceService extends IntentService implements GoogleApiClient.OnConnectionFailedListener {

    private static final String PATH_WITH_FEATURE = "sunshine_watchface_update/Analog";

    private static final String[] FORECAST_COLUMNS = new String[]{
            WeatherContract.WeatherEntry.COLUMN_WEATHER_ID,
            WeatherContract.WeatherEntry.COLUMN_SHORT_DESC,
            WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
            WeatherContract.WeatherEntry.COLUMN_MAX_TEMP
    };
    private static final String LOG_TAG = "WatchfaceService";
    private GoogleApiClient mGoogleApiClient;


    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startUpdateAction(Context context) {
        Intent intent = new Intent(context, SunshineWatchfaceService.class);
        intent.setAction(SunshineSyncAdapter.ACTION_DATA_UPDATED);
        context.startService(intent);
    }

    public SunshineWatchfaceService() {
        super("SunshineWatchfaceService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            final String action = intent.getAction();
            if (SunshineSyncAdapter.ACTION_DATA_UPDATED.equals(action)) {
                String location = Utility.getPreferredLocation(this);
                Uri weatherForLocationUri = WeatherContract.WeatherEntry.buildWeatherLocationWithStartDate(
                        location, System.currentTimeMillis());
                Cursor cursor = getContentResolver().query(weatherForLocationUri, FORECAST_COLUMNS, null,
                        null, WeatherContract.WeatherEntry.COLUMN_DATE + " ASC");
                if (cursor.moveToFirst()) {
                    int weatherId = cursor.getInt(cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID));
                    String desc = cursor.getString(cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_SHORT_DESC));
                    double high = cursor.getDouble(cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP));
                    double low = cursor.getDouble(cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP));

                    handleWatchfaceUpdate(weatherId, desc, high, low);
                }
                cursor.close();

            }
        }
    }

    private void handleWatchfaceUpdate(final int weatherId, final String desc, final double high, final double low) {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                        Log.d(LOG_TAG, "onConnected: " + connectionHint);

                        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/forecast");
                        DataMap dataMap = putDataMapRequest.getDataMap();
                        dataMap.putInt(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID, weatherId);
                        dataMap.putString(WeatherContract.WeatherEntry.COLUMN_SHORT_DESC, desc);
                        dataMap.putDouble(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP, high);
                        dataMap.putDouble(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP, low);

                        PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest();

                        PendingResult<DataApi.DataItemResult> pendingResult =
                                Wearable.DataApi.putDataItem(mGoogleApiClient, putDataRequest);


                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                        if (Log.isLoggable(LOG_TAG, Log.DEBUG)) {
                            Log.d(LOG_TAG, "onConnectionSuspended: " + cause);
                        }
                    }
                })
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();

        mGoogleApiClient.connect();
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (Log.isLoggable(LOG_TAG, Log.DEBUG)) {
            Log.d(LOG_TAG, "onConnectionFailed: " + result);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
}
