package com.pokemonrebellion.core;

import com.pokemonrebellion.entity.Response;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MultiSocket {
    private static final String _IP = "224.0.0.1";
    private static final int PORT = 8510;

    private InetAddress IP;
    private MulticastSocket socket;

    public MultiSocket() {
        try {
            IP = InetAddress.getByName(_IP);
            socket = new MulticastSocket();
            socket.joinGroup(IP);
        } catch(IOException e) {
            System.out.println("Unable to create InetAdress with : " + _IP);
            e.getStackTrace();
        }
    }

    public DatagramPacket serialize(Response response) {
        return new DatagramPacket(response.getContent(), response.getContent().length, IP, PORT);
    }

    public void send(DatagramPacket packet) {
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public InetAddress getIp() {
        return IP;
    }

    public int getPort() {
        return PORT;
    }

    public MulticastSocket getSocket() {
        return socket;
    }
}
