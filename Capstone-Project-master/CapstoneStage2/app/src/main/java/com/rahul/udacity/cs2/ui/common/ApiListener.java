package com.rahul.udacity.cs2.ui.common;

import android.content.Context;

public interface ApiListener {
    void onApiSuccess(String response);

    Context getViewContext();
}
