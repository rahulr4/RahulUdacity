package com.rahul.udacity.cs2.ui.reviews;

import com.rahul.udacity.cs2.model.ReviewModel;
import com.rahul.udacity.cs2.ui.common.CommonView;

import java.util.ArrayList;

/**
 * Created by rahulgupta on 21/01/17.
 */
interface ReviewsView extends CommonView{
    void setData(ArrayList<ReviewModel> reviewModelArrayList);
}
