package com.app.rahul.appportfolio.activity;

import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.app.rahul.appportfolio.R;
import com.app.rahul.appportfolio.utility.Lg;

/**
 * Launcher activity for AppPortFolio App
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Toast mLastToast;

    @Override
    public int getLayoutById() {
        return R.layout.activity_main;
    }

    @Override
    public void initUi() {
        findViewById(R.id.spotify_streamer_btn).setOnClickListener(this);
        findViewById(R.id.scores_app_btn).setOnClickListener(this);
        findViewById(R.id.library_app_btn).setOnClickListener(this);
        findViewById(R.id.build_it_bigger_btn).setOnClickListener(this);
        findViewById(R.id.xyz_reader_btn).setOnClickListener(this);
        findViewById(R.id.capstone_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mLastToast != null)
            mLastToast.cancel();
        //TODO Currently only showing toast so no individual clicks code written. To be managed afterwards
        mLastToast = Lg.showToast(this, (String) v.getTag());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
