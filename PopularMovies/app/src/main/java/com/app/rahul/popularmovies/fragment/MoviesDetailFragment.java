package com.app.rahul.popularmovies.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.rahul.popularmovies.ApplicationController;
import com.app.rahul.popularmovies.R;
import com.app.rahul.popularmovies.adapter.TrailersAdapter;
import com.app.rahul.popularmovies.database.MoviesListingDao;
import com.app.rahul.popularmovies.model.movie_api.MoviesResponseBean;
import com.app.rahul.popularmovies.model.trailers_api.TrailersResponseBean;
import com.app.rahul.popularmovies.network.AppRetrofit;
import com.app.rahul.popularmovies.utility.AppConstants;
import com.app.rahul.popularmovies.utility.Lg;
import com.app.rahul.popularmovies.utility.SnackBarBuilder;
import com.app.rahul.popularmovies.utility.SquareImageView;
import com.app.rahul.popularmovies.utility.Utility;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.HashMap;

import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Rahul on 2/21/2016.
 */
public class MoviesDetailFragment extends BaseFragment implements View.OnClickListener {
    private MoviesResponseBean.MoviesResult moviesResult;
    private ProgressBar mTrailersProgressBar;
    private RecyclerView mTrailersRecyclerView;

    @Override
    public int getLayoutById() {
        return R.layout.fragment_movies_detail;
    }

    @Override
    public void initUi() {
        moviesResult = getArguments().getParcelable(AppConstants.EXTRA_INTENT_PARCEL);
        final TextView movieName = (TextView) findViewById(R.id.movie_name);
        Utility.setText(movieName, moviesResult.getTitle());

        Utility.setText((TextView) findViewById(R.id.movie_desc), moviesResult.getOverview());
        String formattedDate = Utility.parseDateTime(moviesResult.getReleaseDate(), AppConstants.DATE_FORMAT1
                , AppConstants.DATE_FORMAT2);
        Utility.setText((TextView) findViewById(R.id.movie_release_year), formattedDate);
        Utility.setText((TextView) findViewById(R.id.movie_rating), moviesResult.getVoteAverage() + " /10");
        final SquareImageView movieImageView = (SquareImageView) findViewById(R.id.movie_image);
        mTrailersProgressBar = Utility.getProgressBarInstance(mContext, R.id.trailer_progress_bar);
        mTrailersProgressBar = (ProgressBar) findViewById(R.id.trailer_progress_bar);
        mTrailersProgressBar.setIndeterminateDrawable(new IndeterminateProgressDrawable(mContext));
        mTrailersRecyclerView = (RecyclerView) findViewById(R.id.movie_detail_trailers_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mTrailersRecyclerView.setLayoutManager(linearLayoutManager);

        TextView markFavoriteTv = (TextView) findViewById(R.id.mark_favorite);
        markFavoriteTv.setOnClickListener(this);

        if (!TextUtils.isEmpty(moviesResult.getPosterPath()))
            Picasso.with(mContext)
                    .load(AppConstants.BASE_THUMB_IMAGE_URL + moviesResult.getPosterPath())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            movieImageView.setImageBitmap(bitmap);

                            // Asynchronous
                            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette p) {
                                    // Use generated instance
                                    Palette.Swatch vibrantSwatch = p.getVibrantSwatch();
                                    if (vibrantSwatch != null) {
                                        movieName.setTextColor(vibrantSwatch.getTitleTextColor());
                                        ((LinearLayout) movieName.getParent()).setBackgroundColor(vibrantSwatch.getRgb());
                                    }

                                    int defaultColor = getResources().getColor(R.color.colorPrimary);
                                    setToolBarColor(p.getLightVibrantColor(defaultColor));
                                    setToolBarTextColor(p.getDarkMutedColor(defaultColor));


                                    Lg.i("Palette", p.toString());
                                }
                            });


                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
        else {
            movieImageView.setImageResource(R.drawable.placeholder);
        }
        getMoviesDetail();
        getMovieTrailers();
    }

    private void getMoviesDetail() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            showProgressDialog(false);
            HashMap<String, String> stringHashMap = new HashMap<>();
            stringHashMap.put(AppConstants.PARAM_API_KEY, AppConstants.API_KEY);

            Call<MoviesResponseBean.MoviesResult> beanCall = AppRetrofit.getInstance().getApiServices().apiMoviesDetail(moviesResult.getId(), stringHashMap);
            beanCall.enqueue(new Callback<MoviesResponseBean.MoviesResult>() {
                @Override
                public void onResponse(Response<MoviesResponseBean.MoviesResult> response, Retrofit retrofit) {
                    showProgressDialog(false);
                    MoviesResponseBean.MoviesResult moviesResult2 = response.body();
                    if (moviesResult2 != null) {
                        Utility.setText((TextView) findViewById(R.id.movie_runtime), moviesResult2.getRuntime() + " min");
                        Utility.setText((TextView) findViewById(R.id.movie_tagline), moviesResult2.getTagLine());
                    }

                }

                @Override
                public void onFailure(Throwable t) {
                    showProgressDialog(false);
                    Lg.i("Retro", t.toString());
                }
            });
        } else {
            mSnackBar = SnackBarBuilder.make(mParent, getString(R.string.no_internet_connction))
                    .setActionText(getString(R.string.retry))
                    .onSnackBarClicked(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getMoviesDetail();
                        }
                    })
                    .build();
        }
    }

    private void getMovieTrailers() {
        if (ApplicationController.getApplicationInstance().isNetworkConnected() && isAdded()) {
            mTrailersProgressBar.setVisibility(View.VISIBLE);
            HashMap<String, String> stringHashMap = new HashMap<>();
            stringHashMap.put(AppConstants.PARAM_API_KEY, AppConstants.API_KEY);
            Call<TrailersResponseBean> beanCall = AppRetrofit.getInstance().getApiServices().apiMovieTrailers(moviesResult.getId(), stringHashMap);
            beanCall.enqueue(new Callback<TrailersResponseBean>() {
                @Override
                public void onResponse(Response<TrailersResponseBean> response1, Retrofit retrofit) {
                    mTrailersProgressBar.setVisibility(View.GONE);
                    TrailersResponseBean responseBean = response1.body();
                    TrailersAdapter trailersAdapter = new TrailersAdapter(mContext, responseBean.getResults());
                    mTrailersRecyclerView.setAdapter(trailersAdapter);
                }

                @Override
                public void onFailure(Throwable t) {
                    mTrailersProgressBar.setVisibility(View.GONE);
                    Lg.i("Retro", t.toString());
                }
            });
        } else {
            mSnackBar = SnackBarBuilder.make(mParent, getString(R.string.no_internet_connction))
                    .setActionText(getString(R.string.retry))
                    .onSnackBarClicked(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getMoviesDetail();
                        }
                    })
                    .build();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mark_favorite:
                MoviesListingDao moviesListingDao = new MoviesListingDao(mContext);
                boolean isMovieFavorited = moviesListingDao.toggleFavouriteMovie(moviesResult);
                if (isMovieFavorited)
                    mSnackBar = SnackBarBuilder.make(mParent, moviesResult.getTitle() +
                            mContext.getString(R.string.add_to_fav)).build();
                else {
                    mSnackBar = SnackBarBuilder.make(mParent, moviesResult.getTitle() +
                            mContext.getString(R.string.removed_from_favourites)).build();
                }
                break;
        }
    }
}
