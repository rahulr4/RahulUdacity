package com.rahul.udacity.cs2.ui.widget2;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class FavouritePlacesWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavouritePlacesWidgetDataProvider(this, intent);
    }
}
