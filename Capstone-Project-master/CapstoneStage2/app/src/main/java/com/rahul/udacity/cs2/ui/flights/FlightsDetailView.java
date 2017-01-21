package com.rahul.udacity.cs2.ui.flights;

import com.rahul.udacity.cs2.model.FlightDetailModel;
import com.rahul.udacity.cs2.ui.common.CommonView;

import java.util.ArrayList;

/**
 * Created by rahulgupta on 21/01/17.
 */
interface FlightsDetailView extends CommonView{
    void setData(ArrayList<FlightDetailModel> flightDetailModelArrayList);
}
