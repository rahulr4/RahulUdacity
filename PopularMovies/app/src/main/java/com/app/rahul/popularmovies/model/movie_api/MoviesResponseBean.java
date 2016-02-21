package com.app.rahul.popularmovies.model.movie_api;

import android.os.Parcel;
import android.os.Parcelable;
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

    public static class MoviesResult implements Parcelable {

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

        protected MoviesResult(Parcel in) {
            posterPath = in.readString();
            adult = in.readByte() != 0;
            overview = in.readString();
            releaseDate = in.readString();
            id = in.readLong();
            originalTitle = in.readString();
            originalLanguage = in.readString();
            title = in.readString();
            backdropPath = in.readString();
            popularity = in.readDouble();
            voteCount = in.readLong();
            video = in.readByte() != 0;
            voteAverage = in.readDouble();
        }

        public static final Creator<MoviesResult> CREATOR = new Creator<MoviesResult>() {
            @Override
            public MoviesResult createFromParcel(Parcel in) {
                return new MoviesResult(in);
            }

            @Override
            public MoviesResult[] newArray(int size) {
                return new MoviesResult[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(posterPath);
            parcel.writeByte((byte) (adult ? 1 : 0));
            parcel.writeString(overview);
            parcel.writeString(releaseDate);
            parcel.writeLong(id);
            parcel.writeString(originalTitle);
            parcel.writeString(originalLanguage);
            parcel.writeString(title);
            parcel.writeString(backdropPath);
            parcel.writeDouble(popularity);
            parcel.writeLong(voteCount);
            parcel.writeByte((byte) (video ? 1 : 0));
            parcel.writeDouble(voteAverage);
        }
    }
}
