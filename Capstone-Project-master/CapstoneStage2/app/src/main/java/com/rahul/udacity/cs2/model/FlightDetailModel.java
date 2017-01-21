package com.rahul.udacity.cs2.model;

/**
 * Created by rahulgupta on 21/01/17.
 */
public class FlightDetailModel {
    private double price;
    private String STime;
    private String TTime;
    private String flight;
    private String airline;
    private String STerminal;
    private double duration;

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setSTime(String STime) {
        this.STime = STime;
    }

    public String getSTime() {
        return STime;
    }

    public void setTTime(String TTime) {
        this.TTime = TTime;
    }

    public String getTTime() {
        return TTime;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getFlight() {
        return flight;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getAirline() {
        return airline;
    }

    public void setSTerminal(String STerminal) {
        this.STerminal = STerminal;
    }

    public String getSTerminal() {
        return STerminal;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getDuration() {
        return duration;
    }
}
