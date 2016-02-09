package com.app.rahul.popularmovies.network;

import com.app.rahul.popularmovies.model.RequestBean;
import com.app.rahul.popularmovies.utility.AppConstants;

import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by rahil on 9/9/15.
 */
public interface ApiServices {
    @POST(AppConstants.POST_REVIEW)
    Call<RateSellerResponse> postReviewApi(@Body RequestBean params);


}
