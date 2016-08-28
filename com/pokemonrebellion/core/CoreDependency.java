package com.pokemonrebellion.core;

public class CoreDependency {
    private SingleSocket singleSocket;
    private MultiSocket multiSocket;
    private Position position;

    public CoreDependency() {
        singleSocket = new SingleSocket();
        multiSocket = new MultiSocket();
        position = new Position();
    }

    public MultiSocket getMultiSocket() {
        return multiSocket;
    }

    public Position getPosition() {
        return position;
    }

    public SingleSocket getSingleSocket() {
        return singleSocket;
    }
}
