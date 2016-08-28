package com.pokemonrebellion.core;

public class CoreDependency {
    private UDPSocket udpSocket;
    private Position position;

    public CoreDependency() {
        udpSocket = new UDPSocket();
        position = new Position();
    }

    public UDPSocket getUdpSocket() {
        return udpSocket;
    }

    public Position getPosition() {
        return position;
    }
}
