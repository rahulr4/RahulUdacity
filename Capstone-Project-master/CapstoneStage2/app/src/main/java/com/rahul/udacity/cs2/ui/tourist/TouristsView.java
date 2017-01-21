package com.rahul.udacity.cs2.ui.tourist;

import com.rahul.udacity.cs2.model.PlaceListDetail;
import com.rahul.udacity.cs2.ui.common.CommonView;

import java.util.List;

/**
 * Created by rahulgupta on 21/01/17.
 */
interface TouristsView extends CommonView{
    void setData(List<PlaceListDetail> placeListDetailList);
}
