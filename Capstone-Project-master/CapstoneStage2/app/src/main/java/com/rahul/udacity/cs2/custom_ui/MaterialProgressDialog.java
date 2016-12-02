package com.rahul.udacity.cs2.custom_ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.utility.Utility;


/**
 * Created by rahul on 28/8/15.
 */
public class MaterialProgressDialog extends ProgressDialog {
    private String message;

    public MaterialProgressDialog(Context context, String message) {
        super(context);
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getContext());

        setContentView(R.layout.layout_dialog_material_progress);
        TextView mTextView = (TextView) findViewById(R.id.progress_dialog_text);

        ImageView simpleDraweeView = (ImageView) findViewById(R.id.my_image_view);

        Utility.addRotationAnimation(simpleDraweeView);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(simpleDraweeView, "rotation", 0, 359);
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.setRepeatMode(ObjectAnimator.RESTART);
        rotation.setInterpolator(new LinearInterpolator());

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(simpleDraweeView, "scaleX", 0.80f, 1.25f);
        scaleX.setRepeatMode(ValueAnimator.REVERSE);
        scaleX.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(simpleDraweeView, "scaleY", 0.80f, 1.25f);
        scaleY.setRepeatMode(ValueAnimator.REVERSE);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);

        AnimatorSet scaleSet = new AnimatorSet();
        scaleSet.playTogether(scaleX, scaleY, rotation);
        scaleSet.setDuration(1000);
        scaleSet.start();

        if (message != null && !message.isEmpty()) {
            mTextView.setText(message);
        }

        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

    }

    @Override
    public void setMessage(CharSequence message) {
        super.setMessage(message);
    }
}
