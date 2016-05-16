package com.rahul.joke_display_lib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by rahul on 16/5/16.
 */
public class DisplayJokeActivity extends AppCompatActivity {

    public final static String EXTRA_INTENT_JOKE = "extra_intent_joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);
        // Processing the joke intent
        String joke = getIntent().getStringExtra(EXTRA_INTENT_JOKE);
        TextView textViewJoke = (TextView) findViewById(R.id.joke_tv);
        textViewJoke.setText(joke);
    }

}
