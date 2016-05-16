package com.app.rahul.build_it_bigger.backend;

import com.example.TellJoke;

/**
 * The object model for the data we are sending through endpoints
 */
public class JokeApiBean {

    private TellJoke tellJoke;

    public JokeApiBean() {
        tellJoke = new TellJoke();
    }

    public String getJoke() {
        return tellJoke.getRandomJoke();
    }
}