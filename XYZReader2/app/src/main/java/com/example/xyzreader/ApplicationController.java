package com.example.xyzreader;

import android.app.Application;
import android.content.Context;


public class ApplicationController extends Application {


    public static ApplicationController getAppContext(Context context) {
        return (ApplicationController) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
