/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.sunshine.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.graphics.Palette;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.WindowInsets;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Analog watch face with a ticking second hand. In ambient mode, the second hand isn't shown. On
 * devices with low-bit ambient mode, the hands are drawn without anti-aliasing in ambient mode.
 */
public class SunshineWatchFace extends CanvasWatchFaceService {

    private static final String TAG = "SunshineWatchFace";
    /**
     * Update rate in milliseconds for interactive mode. We update once a second to advance the
     * second hand.
     */
    private static final long INTERACTIVE_UPDATE_RATE_MS = TimeUnit.SECONDS.toMillis(1);

    /**
     * Handler message id for updating the time periodically in interactive mode.
     */
    private static final int MSG_UPDATE_TIME = 0;
    private int mSecHandColor;


    @Override
    public Engine onCreateEngine() {
        return new Engine();
    }

    private class Engine extends CanvasWatchFaceService.Engine implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, DataApi.DataListener {
        Paint mBackgroundPaint;
        Paint mHandPaint;
        Paint mSecPaint;
        Paint mTextPaint;
        Paint mHighPaint;
        Paint mLowPaint;
        boolean mAmbient;

        RectF mDateRectF;
        RectF mSecRectF;
        RectF mMinRectF;
        RectF mHourRectF;

        Path textPath;


        int weatherId;
        String weatherDesc;
        double lowTemp = 0.0;
        double highTemp = 0.0;

        float handsStrokeWidth;

        float mXOffset;
        float mYOffset;
        float tempsOffset;
        float mLineHeight;
        int mBackgroundColor;
        int mHandsColor;
        int mTextColor;

        Calendar mCalendar;
        Date mDate;
        SimpleDateFormat mDateFormat;

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(SunshineWatchFace.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();

        final Handler mUpdateTimeHandler = new EngineHandler(this);

        final BroadcastReceiver mTimeZoneReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mCalendar.setTimeZone(TimeZone.getTimeZone(intent.getStringExtra("time-zone")));
                mDate.setTime(mCalendar.getTimeInMillis());
            }
        };
        boolean mRegisteredTimeZoneReceiver = false;

        /**
         * Whether the display supports fewer bits for each color in ambient mode. When true, we
         * disable anti-aliasing in ambient mode.
         */
        boolean mLowBitAmbient;
        private float mHandsStrokeWidth;
        private int mArcsMargin;

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);


            /**
             * Handles time zone and locale changes.
             */
            final BroadcastReceiver mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    mCalendar.setTimeZone(TimeZone.getDefault());
                    initFormats();
                    invalidate();
                }
            };

            setWatchFaceStyle(new WatchFaceStyle.Builder(SunshineWatchFace.this)
                    .setStatusBarGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL)
                    .setHotwordIndicatorGravity(Gravity.CENTER)
                    .setCardPeekMode(WatchFaceStyle.PEEK_MODE_SHORT)
                    .setBackgroundVisibility(WatchFaceStyle.PEEK_MODE_VARIABLE)
                    .setShowSystemUiTime(false)
                    .setViewProtectionMode(WatchFaceStyle.PROTECT_STATUS_BAR | WatchFaceStyle.PROTECT_HOTWORD_INDICATOR)
                    .build());

            Resources resources = SunshineWatchFace.this.getResources();


            mBackgroundColor = resources.getColor(R.color.analog_background);
            mBackgroundPaint = new Paint();
            mBackgroundPaint.setColor(mBackgroundColor);

            mYOffset = resources.getDimension(R.dimen.analog_y_offset);
            tempsOffset = resources.getDimension(R.dimen.analog_temps_margin);
            mLineHeight = resources.getDimension(R.dimen.analog_line_height);

            mHandsColor = resources.getColor(R.color.analog_hands);
            mHandsStrokeWidth = resources.getDimension(R.dimen.analog_hand_stroke);

            mHandPaint = new Paint();
            mHandPaint.setColor(mHandsColor);
            mHandPaint.setStyle(Paint.Style.STROKE);
            mHandPaint.setStrokeWidth(mHandsStrokeWidth);
            mHandPaint.setAntiAlias(true);
            mHandPaint.setStrokeCap(Paint.Cap.ROUND);

            handsStrokeWidth = resources.getDimension(R.dimen.analog_sec_hand_stroke);
            mSecPaint = new Paint();
            mSecPaint.setColor(mHandsColor);
            mSecPaint.setStrokeWidth(handsStrokeWidth);
            mSecPaint.setAntiAlias(true);
            mSecPaint.setStrokeCap(Paint.Cap.ROUND);

            mTextColor = resources.getColor(android.R.color.white);
            mTextPaint = new Paint();
            mTextPaint.setColor(mTextColor);
            mTextPaint.setAntiAlias(true);
            mTextPaint.setTextAlign(Paint.Align.CENTER);

            mTextColor = resources.getColor(android.R.color.white);
            mHighPaint = new Paint();
            mHighPaint.setColor(mTextColor);
            mHighPaint.setAntiAlias(true);
            mHighPaint.setTextAlign(Paint.Align.CENTER);

            mTextColor = resources.getColor(android.R.color.white);
            mLowPaint = new Paint();
            mLowPaint.setColor(mTextColor);
            mLowPaint.setAntiAlias(true);
            mLowPaint.setTextAlign(Paint.Align.CENTER);

            mCalendar = Calendar.getInstance();
            mDate = mCalendar.getTime();

            mDateRectF = new RectF();
            mHourRectF = new RectF();
            mMinRectF = new RectF();
            mSecRectF = new RectF();

            textPath = new Path();

            initFormats();
        }

        @Override
        public void onDestroy() {
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
            super.onDestroy();
        }

        @Override
        public void onPropertiesChanged(Bundle properties) {
            super.onPropertiesChanged(properties);
            mLowBitAmbient = properties.getBoolean(PROPERTY_LOW_BIT_AMBIENT, false);
        }

        @Override
        public void onTimeTick() {
            super.onTimeTick();
            invalidate();
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            super.onAmbientModeChanged(inAmbientMode);
            if (mAmbient != inAmbientMode) {
                mAmbient = inAmbientMode;
                if (mLowBitAmbient) {
                    mHandPaint.setAntiAlias(!inAmbientMode);
                    mTextPaint.setAntiAlias(!inAmbientMode);
                    mHighPaint.setAntiAlias(!inAmbientMode);
                    mLowPaint.setAntiAlias(!inAmbientMode);
                }

                if (inAmbientMode) {
                    Resources resources = getResources();
                    mBackgroundPaint.setColor(resources.getColor(android.R.color.black));
                    mHandPaint.setColor(resources.getColor(android.R.color.white));
                    mHandPaint.setStrokeWidth(resources.getDimension(R.dimen.analog_hand_stroke_ambient));
                    mTextPaint.setColor(resources.getColor(android.R.color.white));
                    mHighPaint.setColor(resources.getColor(android.R.color.white));
                    mLowPaint.setColor(resources.getColor(android.R.color.white));
                } else {
                    mBackgroundPaint.setColor(mBackgroundColor);
                    mHandPaint.setColor(mHandsColor);
                    mHandPaint.setStrokeWidth(mHandsStrokeWidth);
                    mTextPaint.setColor(mTextColor);
                    mHighPaint.setColor(mHandsColor);
                    mLowPaint.setColor(mHandsColor);
                }
                invalidate();
            }

            // Whether the timer should be running depends on whether we're visible (as well as
            // whether we're in ambient mode), so we may need to start or stop the timer.
            updateTimer();
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            long now = System.currentTimeMillis();
            mCalendar.setTimeInMillis(now);
            mDate.setTime(now);

            int width = bounds.width();
            int height = bounds.height();

            // Draw the background.
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mBackgroundPaint);

            // Find the center. Ignore the window insets so that, on round watches with a
            // "chin", the watch face is centered on the entire screen, not just the usable
            // portion.
            float centerX = width / 2f;
            float centerY = height / 2f;

            float secRot = mCalendar.get(Calendar.SECOND) * 6f;
            int minutes = mCalendar.get(Calendar.MINUTE);
            float minRot = minutes * 6f;
            float hrRot = ((mCalendar.get(Calendar.HOUR) + (minutes / 60f)) * 30f);

            mDateRectF.set(bounds.left + 80, bounds.top + 80, bounds.right - 80, bounds.bottom - 80);
            String date = mDateFormat.format(mCalendar.getTime()).toUpperCase();

            textPath.addArc(mDateRectF, -180, 180);

            canvas.drawTextOnPath(date, textPath, 0, 0, mTextPaint);

            if (highTemp != 0.0 && lowTemp != 0.0) {
                String high = String.valueOf((int) highTemp) + "°";
                String low = String.valueOf((int) lowTemp) + "°";

                canvas.drawText(high, centerX - (centerX / 2) - tempsOffset + mArcsMargin, centerY, mHighPaint);
                canvas.drawText("max", centerX - (centerX / 2) - tempsOffset + mArcsMargin, centerY + mTextPaint.getTextSize(), mTextPaint);
                canvas.drawText(low, centerX + (centerX / 2) + tempsOffset - mArcsMargin, centerY, mLowPaint);
                canvas.drawText("min", centerX + (centerX / 2) + tempsOffset - mArcsMargin, centerY + mTextPaint.getTextSize(), mTextPaint);
            }

            if (!mAmbient) {
                Bitmap weatherBitmap = BitmapFactory.decodeResource(getResources(), Utility.getArtResourceForWeatherCondition(weatherId));
                if (weatherBitmap != null) {
                    Log.d(TAG, "Bitmap is Null, weather_id = " + weatherId);
                    canvas.drawBitmap(weatherBitmap, centerX - (weatherBitmap.getWidth() / 2), centerY - (weatherBitmap.getHeight() / 2), null);
                } else {
                    Log.d(TAG, "Bitmap weather_id = " + weatherId);
                }
            }

            if (!mAmbient) {
                mSecRectF.set(bounds.left + mArcsMargin, bounds.top + mArcsMargin, bounds.right - mArcsMargin, bounds.bottom - mArcsMargin);
                canvas.drawArc(mSecRectF, -90, secRot, false, mHandPaint);
            }

            float minMargin = handsStrokeWidth / 2;
            mMinRectF.set(bounds.left + minMargin + mArcsMargin, bounds.top + minMargin + mArcsMargin, bounds.right - minMargin - mArcsMargin, bounds.bottom - minMargin - mArcsMargin);

            canvas.drawArc(mMinRectF, !mAmbient ? -90 : (-90 + minRot - 10), !mAmbient ? Math.max(minRot, 1f) : 10, false, mHandPaint);

            float hourMargin = handsStrokeWidth;
            mHourRectF.set(bounds.left + hourMargin + mArcsMargin, bounds.top + hourMargin + mArcsMargin, bounds.right - hourMargin - mArcsMargin, bounds.bottom - hourMargin - mArcsMargin);
            canvas.drawArc(mHourRectF, !mAmbient ? -90 : (-90 + hrRot - 10), !mAmbient ? Math.max(hrRot, 1f) : 10, false, mHandPaint);


            float innerTickRadius = centerX - 20;
            float outerTickRadius = centerX;
            for (int tickIndex = 0; tickIndex < 12; tickIndex++) {
                float tickRot = (float) (tickIndex * Math.PI * 2 / 12);
                float innerX = (float) Math.sin(tickRot) * innerTickRadius;
                float innerY = (float) -Math.cos(tickRot) * innerTickRadius;
                float outerX = (float) Math.sin(tickRot) * outerTickRadius;
                float outerY = (float) -Math.cos(tickRot) * outerTickRadius;
                canvas.drawLine(centerX + innerX, centerY + innerY,
                        centerX + outerX, centerY + outerY, mTextPaint);
            }


        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);

            if (visible) {
                mGoogleApiClient.connect();

                registerReceiver();

                // Update time zone in case it changed while we weren't visible.
                mCalendar.setTimeZone(TimeZone.getDefault());
                mDate.setTime(mCalendar.getTimeInMillis());
            } else {
                unregisterReceiver();

                if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                    Wearable.DataApi.removeListener(mGoogleApiClient, this);
                    mGoogleApiClient.disconnect();
                }
            }

            // Whether the timer should be running depends on whether we're visible (as well as
            // whether we're in ambient mode), so we may need to start or stop the timer.
            updateTimer();
        }

        private void registerReceiver() {
            if (mRegisteredTimeZoneReceiver) {
                return;
            }
            mRegisteredTimeZoneReceiver = true;
            IntentFilter filter = new IntentFilter(Intent.ACTION_TIMEZONE_CHANGED);
            SunshineWatchFace.this.registerReceiver(mTimeZoneReceiver, filter);
        }

        private void unregisterReceiver() {
            if (!mRegisteredTimeZoneReceiver) {
                return;
            }
            mRegisteredTimeZoneReceiver = false;
            SunshineWatchFace.this.unregisterReceiver(mTimeZoneReceiver);
        }

        @Override
        public void onApplyWindowInsets(WindowInsets insets) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "onApplyWindowInsets: " + (insets.isRound() ? "round" : "square"));
            }
            super.onApplyWindowInsets(insets);

            // Load resources that have alternate values for round watches.
            Resources resources = SunshineWatchFace.this.getResources();

            boolean isRound = insets.isRound();

            mArcsMargin = insets.getSystemWindowInsetBottom();

            if (mArcsMargin == 0) {
                mArcsMargin = 5;
            }


            mXOffset = resources.getDimension(isRound
                    ? R.dimen.analog_x_offset_round : R.dimen.analog_x_offset);

            mTextPaint.setTextSize(resources.getDimension(R.dimen.analog_date_text_size));

            float tempTextSize = resources.getDimension(mAmbient ? R.dimen.analog_date_text_size : R.dimen.analog_high_temp_text_size);
            mHighPaint.setTextSize(tempTextSize);
            mLowPaint.setTextSize(tempTextSize);
        }

        /**
         * Starts the {@link #mUpdateTimeHandler} timer if it should be running and isn't currently
         * or stops it if it shouldn't be running but currently is.
         */
        private void updateTimer() {
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
            if (shouldTimerBeRunning()) {
                mUpdateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME);
            }
        }

        /**
         * Returns whether the {@link #mUpdateTimeHandler} timer should be running. The timer should
         * only run when we're visible and in interactive mode.
         */
        private boolean shouldTimerBeRunning() {
            return isVisible() && !isInAmbientMode();
        }

        /**
         * Handle updating the time periodically in interactive mode.
         */
        private void handleUpdateTimeMessage() {
            invalidate();
            if (shouldTimerBeRunning()) {
                long timeMs = System.currentTimeMillis();
                long delayMs = INTERACTIVE_UPDATE_RATE_MS
                        - (timeMs % INTERACTIVE_UPDATE_RATE_MS);
                mUpdateTimeHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, delayMs);
            }
        }


        @Override
        public void onConnected(Bundle connectionHint) {
            Log.d(TAG, "onConnected: " + connectionHint);

            Wearable.DataApi.addListener(mGoogleApiClient, Engine.this);

            SWUtils.fetchConfigDataMap(mGoogleApiClient,
                    new SWUtils.FetchConfigDataMapCallback() {
                        @Override
                        public void onConfigDataMapFetched(DataMap startupConfig) {
                            // If the DataItem hasn't been created yet or some keys are missing,
                            // use the default values.

                            //SWUtils.putConfigDataItem(mGoogleApiClient, startupConfig);

                            updateUiforDataMap(startupConfig);
                        }
                    }
            );
        }

        private void initFormats() {
            mDateFormat = new SimpleDateFormat("EEE, MMM dd yyyy", Locale.getDefault());
            mDateFormat.setCalendar(mCalendar);

        }

        @Override
        public void onConnectionSuspended(int cause) {
            Log.d(TAG, "onConnectionSuspended: " + cause);
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            Log.d(TAG, "onConnectionFailed: " + connectionResult);

        }

        @Override
        public void onDataChanged(DataEventBuffer dataEventBuffer) {
            Log.d(TAG, "onDataChanged: " + dataEventBuffer);

            for (DataEvent event : dataEventBuffer) {
                if (event.getType() == DataEvent.TYPE_CHANGED) {
                    // DataItem changed

                } else if (event.getType() == DataEvent.TYPE_DELETED) {
                    // DataItem deleted
                }

                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/forecast") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    updateUiforDataMap(dataMap);
                }
            }
        }

        private void updateUiforDataMap(DataMap dataMap) {
            weatherId = dataMap.getInt("weather_id");
            weatherDesc = dataMap.getString("short_des");
            lowTemp = dataMap.getDouble("min");
            highTemp = dataMap.getDouble("max");

            Resources resources = getResources();
            Bitmap bitmap = BitmapFactory.decodeResource(resources, Utility.getArtResourceForWeatherCondition(weatherId));

            if (bitmap != null) {

                SWUtils.putConfigDataItem(mGoogleApiClient, dataMap);

                Palette palette = new Palette.Builder(bitmap).generate();

                mBackgroundColor = palette.getLightMutedColor(resources.getColor(R.color.analog_background));
                mHandsColor = palette.getVibrantColor(resources.getColor(android.R.color.white));
                mSecHandColor = palette.getDarkVibrantColor(resources.getColor(android.R.color.white));

                if (!mAmbient) {
                    mBackgroundPaint.setColor(mBackgroundColor);
                    mHighPaint.setColor(mHandsColor);
                    mSecPaint.setColor(mSecHandColor);
                    mLowPaint.setColor(mHandsColor);
                    mHandPaint.setColor(mHandsColor);
                }

                invalidate();
            }
        }


    }

    private static class EngineHandler extends Handler {
        private final WeakReference<SunshineWatchFace.Engine> mWeakReference;

        public EngineHandler(SunshineWatchFace.Engine reference) {
            mWeakReference = new WeakReference<>(reference);
        }

        @Override
        public void handleMessage(Message msg) {
            SunshineWatchFace.Engine engine = mWeakReference.get();
            if (engine != null) {
                switch (msg.what) {
                    case MSG_UPDATE_TIME:
                        engine.handleUpdateTimeMessage();
                        break;
                }
            }
        }
    }


}
