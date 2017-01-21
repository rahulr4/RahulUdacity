package com.rahul.udacity.cs2.ui.shopping;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rahul.udacity.cs2.base.ApplicationController;
import com.rahul.udacity.cs2.model.PlaceListDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahulgupta on 21/01/17.
 */

class ShoppingPresenter {

    private ShoppingView selectOptionView;

    ShoppingPresenter(ShoppingView selectOptionView) {
        this.selectOptionView = selectOptionView;
    }

    private List<PlaceListDetail> placeListDetailList = new ArrayList<>();
    private static final String TAG_RESULT = "results";
    private static final String TAG_ICON = "icon";
    private static final String TAG_NAME = "name";
    private static final String TAG_PLACE_ID = "place_id";
    private static final String TAG_RATING = "rating";
    private static final String TAG_ADDRESS = "vicinity";
    private static final String TAG_PHOTOS = "photos";
    private static final String TAG_PHOTOS_REFERENCE = "photo_reference";


    public void getPlaceList(String url) {
        selectOptionView.showProgress(true);
        Log.i("Volley Url", url);

        JsonObjectRequest placeReq = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                selectOptionView.showProgress(false);
                Log.i("Volley Response", jsonObject.toString());

                try {
                    JSONArray list = jsonObject.getJSONArray(TAG_RESULT);
                    for (int i = 0; i < list.length(); i++) {

                        JSONObject m = list.getJSONObject(i);
                        PlaceListDetail placeListDetail = new PlaceListDetail();

                        placeListDetail.setPlace_id(m.getString(TAG_PLACE_ID));
                        placeListDetail.setIcon_url(m.getString(TAG_ICON));
                        placeListDetail.setPlace_address(m.getString(TAG_ADDRESS));
                        placeListDetail.setPlace_name(m.getString(TAG_NAME));

                        if (m.has(TAG_RATING)) {
                            placeListDetail.setPlace_rating(m.getDouble(TAG_RATING));
                        }

                        if (m.has(TAG_PHOTOS)) {
                            JSONArray photos = m.getJSONArray(TAG_PHOTOS);

                            Log.d("photos", photos.toString());

                            for (int j = 0; j < photos.length(); j++) {
                                JSONObject photo = photos.getJSONObject(j);

                                ArrayList<String> photos_reference = new ArrayList<>();
                                photos_reference.add(photo.getString(TAG_PHOTOS_REFERENCE));

                                placeListDetail.setPhoto_reference(photos_reference);
                            }
                        }

                        placeListDetailList.add(placeListDetail);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                selectOptionView.setData(placeListDetailList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                selectOptionView.showProgress(false);
                Log.i("Volley Error", volleyError.getMessage());
            }
        });

        ApplicationController.getApplicationInstance().addToRequestQueue(placeReq);
    }

}
