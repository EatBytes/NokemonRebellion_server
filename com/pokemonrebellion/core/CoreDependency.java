package com.pokemonrebellion.core;

public class CoreDependency {
    private UDPSocket udpSocket;
    private Position position;
    private static CoreDependency INSTANCE = new CoreDependency();

    private CoreDependency() {
        udpSocket = new UDPSocket();
        position = new Position();
    }

    public UDPSocket getUdpSocket() {
        return udpSocket;
    }

    public Position getPosition() {
        return position;
    }

    public static CoreDependency getInstance() {
        return INSTANCE;
    }
}
