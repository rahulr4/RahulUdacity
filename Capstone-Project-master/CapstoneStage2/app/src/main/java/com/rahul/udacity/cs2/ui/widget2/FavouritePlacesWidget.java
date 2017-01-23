package com.rahul.udacity.cs2.ui.widget2;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.ui.home.HomeActivity;
import com.rahul.udacity.cs2.utility.Utility;

public class FavouritePlacesWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context,
                                AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_favourites);

        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget, pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setRemoteAdapter(context, remoteViews);
        } else {
            setRemoteAdapterV11(context, remoteViews);
        }

        //TODO
        //Intent clickIntentTemplate = new Intent(context, PlaceDetailActivity.class);
        Intent clickIntentTemplate = new Intent(context, HomeActivity.class);
        PendingIntent clickPendingIntentTemplate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            clickPendingIntentTemplate = TaskStackBuilder
                    .create(context)
                    .addNextIntentWithParentStack(clickIntentTemplate)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        remoteViews.setPendingIntentTemplate(R.id.widget_list_view, clickPendingIntentTemplate);
        remoteViews.setEmptyView(R.id.widget_list_view, R.id.no_favourite_places_empty_text_view);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    static private void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_list_view,
                new Intent(context, FavouritePlacesWidgetService.class));
    }

    @SuppressWarnings("deprecation")
    static private void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.widget_list_view,
                new Intent(context, FavouritePlacesWidgetService.class));
    }

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);
        if (Utility.ACTION_DATA_UPDATE.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, getClass()));
            appWidgetManager.notifyAppWidgetViewDataChanged(
                    appWidgetIds, R.id.widget_list_view);
        }
    }
}