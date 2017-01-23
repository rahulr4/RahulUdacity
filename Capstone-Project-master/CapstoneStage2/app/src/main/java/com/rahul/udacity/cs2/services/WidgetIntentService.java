package com.rahul.udacity.cs2.services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;

import com.rahul.udacity.cs2.database.DatabaseSave;
import com.rahul.udacity.cs2.ui.widget.MyAppWidgetProvider;

@Deprecated
public class WidgetIntentService extends IntentService {

    DatabaseSave db;

    public WidgetIntentService() {
        super("WidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager
                .getAppWidgetIds(new ComponentName(this, MyAppWidgetProvider.class));
    }

}
