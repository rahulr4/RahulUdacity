package com.rahul.udacity.cs2.ui.options;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rahul.udacity.cs2.base.ApplicationController;

import org.json.JSONObject;

/**
 * Created by rahulgupta on 21/01/17.
 */

class SelectOptionPresenter {

    private SelectOptionView selectOptionView;

    SelectOptionPresenter(SelectOptionView selectOptionView) {
        this.selectOptionView = selectOptionView;
    }

    private static final String TAG_RESULT = "result";
    private static final String TAG_GEOMETRY = "geometry";
    private static final String TAG_LOCATION = "location";
    private String st_lat, st_lng;
    private Double lat, lng;

    void getPlaceDetail(String url) {
        selectOptionView.showProgress(true);
        Log.i("Volley Url", url);
        JsonObjectRequest movieReq = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    Log.i("Volley Response", jsonObject.toString());
                    JSONObject movies = jsonObject.getJSONObject(TAG_RESULT);
                    JSONObject geometry = movies.getJSONObject(TAG_GEOMETRY);
                    JSONObject location = geometry.getJSONObject(TAG_LOCATION);

                    st_lat = location.getString("lat");
                    st_lng = location.getString("lng");

                    lat = Double.parseDouble(st_lat);
                    lng = Double.parseDouble(st_lng);
                    Log.d("lat", lat + "");
                    Log.d("lng", lng + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                selectOptionView.showProgress(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("Volley Error", volleyError.getMessage());
                selectOptionView.showProgress(false);
            }
        });

        ApplicationController.getApplicationInstance().addToRequestQueue(movieReq);
    }

}
