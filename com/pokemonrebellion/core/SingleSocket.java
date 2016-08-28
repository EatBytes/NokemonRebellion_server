package com.pokemonrebellion.core;

import com.pokemonrebellion.entity.Request;
import com.pokemonrebellion.entity.Response;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SingleSocket {
    private static final String _IP = "localhost";
    private static final int PORT = 8500;

    private InetAddress IP;
    private DatagramSocket socket;

    public SingleSocket() {
        try {
            IP = InetAddress.getByName(_IP);
            socket = new DatagramSocket(PORT);
        } catch(IOException e) {
            e.getStackTrace();
        }
    }

    public DatagramPacket serialize(Response response, Request request) {
        return new DatagramPacket(response.getContent(),
                                response.getContent().length,
                                request.getRaw().getAddress(),
                                request.getRaw().getPort());
    }

    public void send(DatagramPacket data) {
        try {
            socket.send(data);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public DatagramPacket receive(DatagramPacket packet) {
        try {
            socket.receive(packet);

            return packet;
        } catch (IOException e) {
            e.getStackTrace();
        }

        return null;
    }
}
