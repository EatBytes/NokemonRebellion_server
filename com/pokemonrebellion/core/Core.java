package com.pokemonrebellion.core;

public class Core {
    public final static UDPSocket udpSocket = new UDPSocket();
    public final static Position position = new Position();
    private static Core INSTANCE = new Core();

    private Core() {}

    public static Core get() {
        return INSTANCE;
    }
}
