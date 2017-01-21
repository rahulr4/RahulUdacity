package com.rahul.udacity.cs2.ui.restaurant_list.fragments;

import com.rahul.udacity.cs2.model.PlaceListDetail;
import com.rahul.udacity.cs2.ui.common.CommonView;

import java.util.ArrayList;

/**
 * Created by rahulgupta on 21/01/17.
 */
public interface RestaurantView extends CommonView{
    void setData(ArrayList<PlaceListDetail> placeListDetailArrayList);
}
