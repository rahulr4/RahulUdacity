package com.rahul.udacity.cs2.ui.flights;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.model.FlightDetailModel;

import java.util.ArrayList;


class FlightListAdapter extends RecyclerView.Adapter<FlightListAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<FlightDetailModel> detailModelArrayList;

    FlightListAdapter(Context c, ArrayList<FlightDetailModel> detailModelArrayList) {
        this.context = c;
        mInflater = (LayoutInflater) c
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.detailModelArrayList = detailModelArrayList;
    }

    @Override
    public FlightListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.layout_flight_row, viewGroup, false);
        final ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    private Drawable getIcon(int i) {
        switch (detailModelArrayList.get(i).getAirline()) {
            case "6E":
                return context.getResources().getDrawable(R.drawable.indigo_logo);
            case "AI":
                return context.getResources().getDrawable(R.drawable.airindia_logo);
            case "AC":
                return context.getResources().getDrawable(R.drawable.aircanada_logo);
            case "TK":
                return context.getResources().getDrawable(R.drawable.turkish_logo);
            case "ET":
                return context.getResources().getDrawable(R.drawable.ethiopian_logo);
            case "G8":
                return context.getResources().getDrawable(R.drawable.goair_logo);
            case "9W":
                return context.getResources().getDrawable(R.drawable.jetairways_logo);
            case "S2":
                return context.getResources().getDrawable(R.drawable.jetlite_logo);
            case "UK":
                return context.getResources().getDrawable(R.drawable.vistara_logo);
            case "SG":
                return context.getResources().getDrawable(R.drawable.spicejet_logo);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(FlightListAdapter.ViewHolder viewHolder, int i) {

        viewHolder.airline.setText(detailModelArrayList.get(i).getAirline());
        viewHolder.price.setText(context.getString(R.string.price_flight, detailModelArrayList.get(i).getPrice() * 60));
        viewHolder.stime.setText(detailModelArrayList.get(i).getSTime());
        viewHolder.ttime.setText(detailModelArrayList.get(i).getTTime());
        viewHolder.flight.setText(detailModelArrayList.get(i).getFlight());
        viewHolder.terminal.setText(context.getString(R.string.flight_departure, detailModelArrayList.get(i).getSTerminal()));

        viewHolder.flight_icon.setImageDrawable(getIcon(i));

    }

    @Override
    public int getItemCount() {
        return detailModelArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView airline, terminal, stime, ttime, price, flight;
        ImageView flight_icon;

        ViewHolder(View itemView) {
            super(itemView);

            airline = (TextView) itemView.findViewById(R.id.airline);
            terminal = (TextView) itemView.findViewById(R.id.terminal);
            stime = (TextView) itemView.findViewById(R.id.sTime);
            flight = (TextView) itemView.findViewById(R.id.flight);
            ttime = (TextView) itemView.findViewById(R.id.tTime);
            price = (TextView) itemView.findViewById(R.id.price);
            flight_icon = (ImageView) itemView.findViewById(R.id.flight_icon);

        }
    }
}
