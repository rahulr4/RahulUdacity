package com.app.rahul.popularmovies.network;

import com.app.rahul.popularmovies.model.movie_api.MoviesResponseBean;

import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by rahil on 9/9/15.
 */
public interface ApiServices {

    @GET("discover/movie")
    Call<MoviesResponseBean> apiMoviesList(@QueryMap Map<String, String> stringMap);

}
