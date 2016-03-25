package com.app.rahul.popularmovies.network;

import com.app.rahul.popularmovies.model.movie_api.MoviesResponseBean;
import com.app.rahul.popularmovies.model.reviews_api.ReviewsListingResponse;
import com.app.rahul.popularmovies.model.trailers_api.TrailersResponseBean;

import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

/**
 * Created by rahil on 9/9/15.
 */
public interface ApiServices {

    @GET("discover/movie")
    Call<MoviesResponseBean> apiMoviesList(@QueryMap Map<String, String> stringMap);


    @GET("movie/{movie_id}?")
    Call<MoviesResponseBean.MoviesResult> apiMoviesDetail(@Path("movie_id") long taskId, @QueryMap Map<String, String> stringMap);

    @GET("movie/{movie_id}/videos?")
    Call<TrailersResponseBean> apiMovieTrailers(@Path("movie_id") long movieId, @QueryMap Map<String, String> stringMap);

    @GET("movie/{movie_id}/reviews?")
    Call<ReviewsListingResponse> apiMovieReviews(@Path("movie_id") long movieId, @QueryMap Map<String, String> stringMap);
}
