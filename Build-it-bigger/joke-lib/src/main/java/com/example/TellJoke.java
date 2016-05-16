package com.example;

import java.util.ArrayList;
import java.util.Random;

public class TellJoke {

    private ArrayList<String> jokes;
    private Random random;

    public TellJoke() {
        jokes = new ArrayList<>();
        jokes.add("There are only 10 types of people in the world: those that understand binary and those that don’t.");
        jokes.add("Computers make very fast, very accurate mistakes.");
        jokes.add("Be nice to the nerds, for all you know they might be the next Bill Gates!");
        jokes.add("CAPS LOCK – Preventing Login Since 1980.");
        jokes.add("The Internet: where men are men, women are men, and children are FBI agents.");
        jokes.add("The box said ‘Requires Windows Vista or better’. So I installed LINUX.");
        jokes.add("Microsoft: “You’ve got questions. We’ve got dancing paperclips.”");
        jokes.add("The more I C, the less I see.");
        jokes.add("Java: write once, debug everywhere.");

        random = new Random();
    }

    public ArrayList<String> getJokes() {
        return jokes;
    }

    public String getRandomJoke() {
        return jokes.get(random.nextInt(jokes.size()));
    }
}
