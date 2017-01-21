package com.rahul.udacity.cs2.ui.reviews;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rahul.udacity.cs2.base.ApplicationController;
import com.rahul.udacity.cs2.model.ReviewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rahulgupta on 21/01/17.
 */

class ReviewPresenter {

    private ReviewsView reviewsView;

    ReviewPresenter(ReviewsView reviewsView) {
        this.reviewsView = reviewsView;
    }

    void getReviews(String url) {
        reviewsView.showProgress(true);
        JsonObjectRequest movieReq = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                reviewsView.showProgress(false);
                ArrayList<ReviewModel> reviewModelArrayList = new ArrayList<>();
                try {

                    JSONObject list = jsonObject.getJSONObject("result");
                    JSONArray reviews = list.getJSONArray("reviews");

                    for (int k = 0; k < reviews.length(); k++) {
                        ReviewModel reviewModel = new ReviewModel(reviews.getJSONObject(k).getString("author_name"),
                                reviews.getJSONObject(k).getString("text"),
                                String.valueOf(reviews.getJSONObject(k).getDouble("rating")));
                        reviewModelArrayList.add(reviewModel);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                reviewsView.setData(reviewModelArrayList);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                reviewsView.showProgress(false);
            }
        });

        ApplicationController.getApplicationInstance().addToRequestQueue(movieReq);
    }
}
