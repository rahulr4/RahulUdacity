package com.rahul.udacity.cs2.ui.reviews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.model.ReviewModel;

import java.util.ArrayList;

class ReviewAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater mInflater;
    private ArrayList<ReviewModel> reviewModelArrayList;

    ReviewAdapter(Context c, ArrayList<ReviewModel> reviewModelArrayList) {
        this.ctx = c;
        mInflater = (LayoutInflater) c
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.reviewModelArrayList = reviewModelArrayList;
    }

    @Override
    public int getCount() {
        return reviewModelArrayList.size();
    }

    @Override
    public ReviewModel getItem(int i) {
        return reviewModelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.layout_reviews_row, viewGroup, false);

            holder.authorname = (TextView) convertView.findViewById(R.id.authorname);
            holder.authorreview = (TextView) convertView.findViewById(R.id.authorreview);
            holder.rating = (RatingBar) convertView.findViewById(R.id.rating);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Log.d("name", reviewModelArrayList.get(i).getAuthorName());
        holder.authorname.setText(reviewModelArrayList.get(i).getAuthorName());
        holder.authorreview.setText(reviewModelArrayList.get(i).getText());
        holder.rating.setRating(Float.parseFloat(reviewModelArrayList.get(i).getRating()));

        return convertView;
    }

    static class ViewHolder {
        TextView authorname, authorreview;
        RatingBar rating;
    }
}
