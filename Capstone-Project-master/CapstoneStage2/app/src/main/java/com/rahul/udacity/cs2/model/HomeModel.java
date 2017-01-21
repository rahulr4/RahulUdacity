package com.rahul.udacity.cs2.model;

/**
 * Created by rahulgupta on 21/01/17.
 */

public class HomeModel {
    private final String name;
    private final String key;

    public HomeModel(String name, String key) {

        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
