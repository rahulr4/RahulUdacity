package com.rahul.udacity.cs2.ui.place_detail;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rahul.udacity.cs2.base.ApplicationController;

import org.json.JSONObject;


/**
 * Created by rahulgupta on 21/01/17.
 */

public class PlaceDetailPresenter {
    private PlaceDetailView placeDetailView;

    PlaceDetailPresenter(PlaceDetailView placeDetailView) {

        this.placeDetailView = placeDetailView;
    }

    public void getPlaceDetail(String url) {
        placeDetailView.showProgress(true);
        Log.i("Volley Url", url);

        JsonObjectRequest movieReq = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                placeDetailView.showProgress(false);
                Log.i("Volley Response", jsonObject.toString());

                placeDetailView.parseData(jsonObject);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                placeDetailView.showProgress(false);
                Log.i("Volley Error", volleyError.getMessage());

            }
        });

        ApplicationController.getApplicationInstance().addToRequestQueue(movieReq);
    }
}
