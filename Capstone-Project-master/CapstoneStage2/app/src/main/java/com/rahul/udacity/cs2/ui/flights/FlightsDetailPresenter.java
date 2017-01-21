package com.rahul.udacity.cs2.ui.flights;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rahul.udacity.cs2.base.ApplicationController;
import com.rahul.udacity.cs2.model.FlightDetailModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rahulgupta on 21/01/17.
 */

class FlightsDetailPresenter {

    private FlightsDetailView flightsDetailView;
    private static final String TAG_ROUTES = "routes";
    private static final String TAG_SEGMENTS = "segments";
    private static final String TAG_ITENARIES = "itineraries";

    FlightsDetailPresenter(FlightsDetailView flightsDetailView) {

        this.flightsDetailView = flightsDetailView;
    }

    void getFlightDetails(String url) {
        flightsDetailView.showProgress(true);
        Log.i("Volley Url", url);

        JsonObjectRequest placeReq = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                flightsDetailView.showProgress(false);
                Log.i("Volley Response", jsonObject.toString());

                ArrayList<FlightDetailModel> flightDetailModelArrayList = new ArrayList<>();
                try {
                    JSONArray routes = jsonObject.getJSONArray(TAG_ROUTES);
                    for (int j = 0; j < routes.length(); j++) {
                        JSONObject object = routes.getJSONObject(j);
                        JSONArray segments = object.getJSONArray(TAG_SEGMENTS);
                        for (int k = 0; k < segments.length(); k++) {
                            JSONObject object1 = segments.getJSONObject(k);
                            JSONArray itenaries = object1.getJSONArray(TAG_ITENARIES);

                            for (int b = 0; b < itenaries.length(); b++) {
                                JSONObject object2 = itenaries.getJSONObject(b);

                                JSONArray legs = object2.getJSONArray("legs");

                                FlightDetailModel flightDetailModel = new FlightDetailModel();

                                for (int i = 0; i < legs.length(); i++) {
                                    JSONObject object3 = legs.getJSONObject(i);
                                    JSONObject indictiveprices = object3.getJSONObject("indicativePrice");

                                    flightDetailModel.setPrice(indictiveprices.getDouble("price"));

                                    JSONArray hops = object3.getJSONArray("hops");
                                    JSONObject object4 = hops.getJSONObject(0);

                                    flightDetailModel.setSTime(object4.getString("sTime"));
                                    flightDetailModel.setTTime(object4.getString("tTime"));
                                    flightDetailModel.setFlight(object4.getString("flight"));
                                    flightDetailModel.setAirline(object4.getString("airline"));
                                    flightDetailModel.setSTerminal(object4.getString("sTerminal"));
                                    flightDetailModel.setDuration(object4.getDouble("duration"));
                                    flightDetailModelArrayList.add(flightDetailModel);
                                }
                            }
                        }
                    }

                    flightsDetailView.setData(flightDetailModelArrayList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                flightsDetailView.showProgress(false);
//                Log.i("Volley Error", volleyError.getMessage());

            }
        });

        ApplicationController.getApplicationInstance().addToRequestQueue(placeReq);
    }
}
