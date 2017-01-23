package com.rahul.udacity.cs2.ui.widget2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.database.DatabaseSave;
import com.rahul.udacity.cs2.database.TravelProvider;
import com.rahul.udacity.cs2.model.PlaceListDetail;
import com.rahul.udacity.cs2.ui.home.HomeActivity;

public class FavouritePlacesWidgetDataProvider
        implements RemoteViewsService.RemoteViewsFactory {

    private static final String LOG_TAG = FavouritePlacesWidgetDataProvider.class.getSimpleName();

    private Cursor mFavPlacesCursor;
    private Context mContext;
    private Intent mIntent;

    public FavouritePlacesWidgetDataProvider(Context context, Intent intent) {
        mContext = context;
        mIntent = intent;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        if (mFavPlacesCursor != null) {
            mFavPlacesCursor.close();
        }

        final long identityToken = Binder.clearCallingIdentity();

        mFavPlacesCursor = mContext.getContentResolver().query(
                TravelProvider.CONTENT_URI_PLACES, null, null, null, null);

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (mFavPlacesCursor != null) {
            mFavPlacesCursor.close();
            mFavPlacesCursor = null;
        }
    }

    @Override
    public int getCount() {
        return (mFavPlacesCursor == null ? 0 : mFavPlacesCursor.getCount());
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                mFavPlacesCursor == null ||
                !mFavPlacesCursor.moveToPosition(position)) {
            return null;
        }

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                R.layout.list_item_favourites);

        PlaceListDetail placesListInfo = getPlacesListInfo(mFavPlacesCursor);

        remoteViews.setTextViewText(R.id.place_name, placesListInfo.getPlace_name());

        final Intent fillInIntent = new Intent(mContext, HomeActivity.class);
        remoteViews.setOnClickFillInIntent(R.id.place_pic, fillInIntent);

        return remoteViews;
    }

    private PlaceListDetail getPlacesListInfo(Cursor cursor) {
        PlaceListDetail placesListInfo = new PlaceListDetail();

        placesListInfo.setPlace_id(cursor.getString(
                cursor.getColumnIndex(DatabaseSave.KEY_PLACE_ID)));
        placesListInfo.setPlace_name(cursor.getString(
                cursor.getColumnIndex(DatabaseSave.KEY_PLACE_NAME)));

        return placesListInfo;
    }

    @Override
    public RemoteViews getLoadingView() {
        return new RemoteViews(mContext.getPackageName(), R.layout.list_item_favourites);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        if (mFavPlacesCursor.moveToPosition(position))
            return mFavPlacesCursor.getLong(0);
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
