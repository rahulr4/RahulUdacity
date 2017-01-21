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

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        ComponentName thisWidget = new ComponentName(context,
                MyAppWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {

            view = new RemoteViews(context.getPackageName(), R.layout.appwidget);

            Cursor data = new DatabaseSave(context).getSavedHotels();
            if (data.moveToFirst()) {

                do {
                    Log.v("--", "FOUND FROM DB:" + data.getString(1));
                    String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + data.getString(1) + "&key=API_KEY";
                    getPlaceDetail(url);

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

    public void getPlaceDetail(String url) {
        JsonObjectRequest movieReq = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {

                    JSONObject list = jsonObject.getJSONObject(TAG_RESULT);
                    String placeName = list.getString("name");
                    view.setTextViewText(R.id.place_name, placeName);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });

        ApplicationController.getApplicationInstance().addToRequestQueue(movieReq);
    }
}
