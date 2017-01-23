package com.rahul.udacity.cs2.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.HashMap;

import static com.rahul.udacity.cs2.database.DatabaseSave.TABLE_PLACES;

/**
 * A Content Provider for WatchThemAll show data
 */
public class TravelProvider extends ContentProvider {
    public static final String PROVIDER_NAME =
            "com.rahul.capstone.data.provider.PlaceProvider";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME);

    static final String name = "name";
    static final int uriCode = 1;
    private static final int PLACES_CONTENT_URI_PLACES = 0;

    static UriMatcher uriMatcher = null;
    private SQLiteDatabase db;
    private static HashMap<String, String> values = new HashMap<>();

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, DatabaseSave.TABLE_PLACES, PLACES_CONTENT_URI_PLACES);
        uriMatcher.addURI(PROVIDER_NAME, "cte/*", uriCode);
    }

    public static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    public static final Uri CONTENT_URI_PLACES = buildUri(DatabaseSave.TABLE_PLACES);
    public static final Uri CONTENT_URI_RESTAURANT = buildUri(DatabaseSave.TABLE_RESTAURANTS);
    public static final Uri CONTENT_URI_HOTELS = buildUri(DatabaseSave.TABLE_HOTELS);

    public static Uri withPlaceId() {
        return CONTENT_URI_PLACES.buildUpon().build();
    }

    public static Uri withResId() {
        return CONTENT_URI_RESTAURANT.buildUpon().build();
    }

    public static Uri wihtHotelId() {
        return CONTENT_URI_HOTELS.buildUpon().build();
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int count;
        switch (uriMatcher.match(uri)) {
            case uriCode:
                count = db.delete(TABLE_PLACES, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case uriCode:
                return "vnd.android.cursor.dir/cte";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long rowID = db.insert(TABLE_PLACES, "", values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(BASE_CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseSave dbHelper = new DatabaseSave(context);
        db = dbHelper.getWritableDatabase();
        return db != null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case PLACES_CONTENT_URI_PLACES:

                Cursor cursor = db.query(DatabaseSave.TABLE_PLACES, null, null, null, null, null, null);
                return cursor;
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case uriCode:
                count = db.update(TABLE_PLACES, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

}
