package com.rahul.udacity.cs2.ui.shopping;

import com.rahul.udacity.cs2.model.PlaceListDetail;
import com.rahul.udacity.cs2.ui.common.CommonView;

import java.util.List;

/**
 * Created by rahulgupta on 21/01/17.
 */
interface ShoppingView extends CommonView {
    void setData(List<PlaceListDetail> placeListDetailList);
}
