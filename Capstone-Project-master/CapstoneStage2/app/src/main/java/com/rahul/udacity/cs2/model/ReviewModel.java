package com.rahul.udacity.cs2.model;

/**
 * Created by rahulgupta on 21/01/17.
 */
public class ReviewModel {
    private final String authorName;
    private final String text;
    private final String rating;

    public ReviewModel(String authorName, String text, String rating) {

        this.authorName = authorName;
        this.text = text;
        this.rating = rating;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getText() {
        return text;
    }

    public String getRating() {
        return rating;
    }
}
