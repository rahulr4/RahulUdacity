package com.rahul.udacity.cs2.ui.place_detail;

import com.rahul.udacity.cs2.ui.common.CommonView;

import org.json.JSONObject;

/**
 * Created by rahulgupta on 21/01/17.
 */

public interface PlaceDetailView extends CommonView {
    void parseData(JSONObject jsonObject);
}
