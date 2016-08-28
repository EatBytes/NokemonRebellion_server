package com.pokemonrebellion.core;

public class Position {
    private String state = "up";

    public void switchState() {
        if (state.equals("up")) {
            state = "down";
        } else {
            state = "up";
        }
    }

    public String getState() {
        return state;
    }
}
