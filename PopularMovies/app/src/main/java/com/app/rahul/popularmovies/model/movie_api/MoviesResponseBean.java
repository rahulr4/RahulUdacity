package com.app.rahul.popularmovies.model.movie_api;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rahul on 9/2/16.
 */
public class MoviesResponseBean {

    private int page;

    @SerializedName("total_results")
    private long totalResults;

    @SerializedName("total_pages")
    private long totalPages;

    ArrayList<MoviesResult> results;

    public int getPage() {
        return page;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public ArrayList<MoviesResult> getResults() {
        return results;
    }

    public static class MoviesResult {

        @SerializedName("poster_path")
        public String posterPath;

        public boolean adult;

        public String overview;

        @SerializedName("release_date")
        public String releaseDate;

        @SerializedName("genre_ids")
        public ArrayList<Long> genreIds = new ArrayList<Long>();

        public long id;

        @SerializedName("original_title")
        public String originalTitle;

        @SerializedName("original_language")
        public String originalLanguage;

        public String title;

        @SerializedName("backdrop_path")
        public String backdropPath;

        @SerializedName("popularity")
        public double popularity;

        @SerializedName("vote_count")
        public long voteCount;

        public boolean video;

        @SerializedName("vote_average")
        public double voteAverage;

        public String getPosterPath() {
            if (TextUtils.isEmpty(posterPath))
                return "";
            return posterPath;
        }

        public boolean isAdult() {
            return adult;
        }

        public String getOverview() {
            return overview;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public ArrayList<Long> getGenreIds() {
            return genreIds;
        }

        public long getId() {
            return id;
        }

        public String getOriginalTitle() {
            return originalTitle;
        }

        public String getOriginalLanguage() {
            return originalLanguage;
        }

        public String getTitle() {
            return title;
        }

        public String getBackdropPath() {
            return backdropPath;
        }

        public double getPopularity() {
            return popularity;
        }

        public long getVoteCount() {
            return voteCount;
        }

        public boolean isVideo() {
            return video;
        }

        public double getVoteAverage() {
            return voteAverage;
        }
    }
}
