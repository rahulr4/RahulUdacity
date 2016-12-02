package com.rahul.udacity.cs2.custom_ui;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rahul.udacity.cs2.R;

/**
 * Created by Rahul on 10-10-2016.
 */

public class CustomToast {

    private static CustomToast mInstance;
    private Activity context;
    private Toast toast;

    private CustomToast(Activity context) {

        this.context = context;
    }

    public static synchronized CustomToast getInstance(Activity context) {
        synchronized (CustomToast.class) {
            if (mInstance == null) {
                mInstance = new CustomToast(context);
            }
        }
        return mInstance;
    }

    private void initToast() {
        LayoutInflater li = context.getLayoutInflater();
        View layout = li.inflate(R.layout.layout_custom_toast,
                (ViewGroup) context.findViewById(R.id.custom_toast_layout));

        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 80);
        toast.setView(layout);//setting the view of custom toast layout
    }

    public void showToast(String str) {
        if (toast == null) {
            initToast();
        }
        TextView textView = (TextView) toast.getView().findViewById(R.id.custom_toast_message);
        textView.setText(str);

        toast.show();
    }

    public void showToast(int str) {
        if (toast == null) {
            initToast();
        }
        TextView textView = (TextView) toast.getView().findViewById(R.id.custom_toast_message);
        textView.setText(str);

        toast.show();
    }
}
