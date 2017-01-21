package com.rahul.udacity.cs2.ui.flights;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.base.ApplicationController;
import com.rahul.udacity.cs2.base.BaseActivity;
import com.rahul.udacity.cs2.utility.Constants;

public class FlightsListActivity extends BaseActivity {

    RelativeLayout from, to;
    TextView fromname, toname;
    Button search;


    private void showCities(final int option) {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                getActivity());
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.select_dialog_singlechoice);

        arrayAdapter.add("Mumbai");
        arrayAdapter.add("Delhi");
        arrayAdapter.add("Bangkok");
        arrayAdapter.add("Bangalore");
        arrayAdapter.add("Pune");
        arrayAdapter.add("Hyderabad");
        arrayAdapter.add("Kolkata");
        arrayAdapter.add("Chennai");
        arrayAdapter.add("Goa");
        arrayAdapter.add("Dubai");
        arrayAdapter.add("Singapore");
        arrayAdapter.add("Kathmandu");

        builderSingle.setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        if (option == 1)
                            fromname.setText(strName);
                        else
                            toname.setText(strName);
                    }
                });
        builderSingle.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tracker t = ApplicationController.getApplicationInstance().getTracker();
        t.setScreenName("Flight Screen");
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    @Override
    protected void initUi() {
        setBackButtonEnabled();
        from = (RelativeLayout) findViewById(R.id.from);
        to = (RelativeLayout) findViewById(R.id.to);
        fromname = (TextView) findViewById(R.id.fromName);
        toname = (TextView) findViewById(R.id.toName);
        search = (Button) findViewById(R.id.search);


        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCities(1);
            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCities(2);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), FlightDetailActivity.class);
                i.putExtra(Constants.FROM, fromname.getText());
                i.putExtra(Constants.TO, toname.getText());
                startActivity(i);

            }
        });
    }

    @Override
    protected int getLayoutById() {
        return R.layout.activity_flights_list;
    }

}

