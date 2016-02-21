package com.app.rahul.popularmovies.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.widget.TextView;

import com.app.rahul.popularmovies.R;
import com.app.rahul.popularmovies.model.movie_api.MoviesResponseBean;
import com.app.rahul.popularmovies.utility.AppConstants;
import com.app.rahul.popularmovies.utility.Lg;
import com.app.rahul.popularmovies.utility.SquareImageView;
import com.app.rahul.popularmovies.utility.Utility;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by Rahul on 2/21/2016.
 */
public class MoviesDetailActivity extends BaseActivity {
    @Override
    public int getLayoutById() {
        return R.layout.activity_movies_detail;
    }

    @Override
    public void initUi() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        MoviesResponseBean.MoviesResult moviesResult = getIntent().getParcelableExtra(AppConstants.EXTRA_INTENT_PARCEL);
        final TextView movieName = (TextView) findViewById(R.id.movie_name);
        Utility.setText(movieName, moviesResult.getTitle());

        final SquareImageView movieImageView = (SquareImageView) findViewById(R.id.movie_image);
        if (!moviesResult.getPosterPath().isEmpty())
            Picasso.with(this)
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
                                        movieName.setBackgroundColor(vibrantSwatch.getRgb());
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
    }
}
