package com.rahul.udacity.cs2.ui.home.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.model.HomeModel;
import com.rahul.udacity.cs2.ui.options.SelectOptionActivity;
import com.rahul.udacity.cs2.utility.Constants;

import java.util.ArrayList;

/**
 * Created by rahulgupta on 21/01/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private final ArrayList<HomeModel> placesArrayList;

    public HomeAdapter(ArrayList<HomeModel> placesArrayList) {
        this.placesArrayList = placesArrayList;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_simple_list_item_1, parent, false);
        return new HomeViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return placesArrayList.size();
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        holder.textView.setText(placesArrayList.get(position).getName());
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        HomeViewHolder(final View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final HomeModel item = placesArrayList.get(getAdapterPosition());
                    Intent i1 = new Intent(itemView.getContext(), SelectOptionActivity.class);
                    i1.putExtra(Constants.PLACE_ID, item.getKey());
                    i1.putExtra(Constants.NAME, item.getName());
                    itemView.getContext().startActivity(i1);
                }
            });
        }
    }
}
