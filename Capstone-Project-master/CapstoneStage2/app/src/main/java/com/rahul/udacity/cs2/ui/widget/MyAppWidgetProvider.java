package com.rahul.udacity.cs2.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.ApplicationController;
import com.rahul.udacity.cs2.database.DatabaseSave;
import com.rahul.udacity.cs2.ui.home.HomeActivity;

import org.json.JSONObject;

public class MyAppWidgetProvider extends AppWidgetProvider {

    private static final String TAG_RESULT = "result";
    RemoteViews view;
    public static String WIDGET_UPDATE_ACTION = "com.rahul.udacity.cs2.UPDATE_WIDGET";
    private AppWidgetManager appWidgetManager;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        this.appWidgetManager = appWidgetManager;
        Log.i("CapstoneWidget", "OnUpdate Called");
        ComponentName thisWidget = new ComponentName(context,
                MyAppWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {

            view = new RemoteViews(context.getPackageName(), R.layout.appwidget);
            Log.i("CapstoneWidget", "Remote View Initialized");
            Cursor data = new DatabaseSave(context).getSavedHotels();
            if (data.moveToFirst()) {
                Log.i("CapstoneWidget", "Got Data");
                do {
                    Log.v("--", "FOUND FROM DB:" + data.getString(1));
                    String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid="
                            + data.getString(1) + "&key=" + context.getString(R.string.api_key);
                    getPlaceDetail(url, widgetId);

                } while (data.moveToNext());
            }

            Intent intent = new Intent(context, HomeActivity.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            view.setOnClickPendingIntent(R.id.lay, pendingIntent);

            appWidgetManager.updateAppWidget(widgetId, view);

        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
    }

    public static PendingIntent buildButtonPendingIntent(Context context) {
        ++MyWidgetIntentReceiver.clickCount;

        // initiate widget update request
        Intent intent = new Intent();
        intent.setAction(WIDGET_UPDATE_ACTION);
        return PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void getPlaceDetail(String url, final int widgetId) {
        JsonObjectRequest movieReq = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    Log.i("CapstoneWidget", "Api Response");
                    JSONObject list = jsonObject.getJSONObject(TAG_RESULT);
                    String placeName = list.getString("name");
                    Log.i("CapstoneWidget", "Hotel Name :- " + placeName);
                    view.setTextViewText(R.id.place_name, placeName);
                    appWidgetManager.updateAppWidget(widgetId, view);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("CapstoneWidget", "Error :- " + volleyError.getMessage());
            }
        });

        ApplicationController.getApplicationInstance().addToRequestQueue(movieReq);
    }
}
