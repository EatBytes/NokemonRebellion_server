package com.pokemonrebellion.core;

import com.pokemonrebellion.entity.Request;
import com.pokemonrebellion.entity.Response;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class UDPSocket {
    private static final String _IP = "localhost";
    private static final String _EXT_IP = "224.0.0.1";

    private static final int PORT = 8500;
    private static final int EXT_PORT = 8510;

    private InetAddress IP;
    private InetAddress EXT_IP;
    private MulticastSocket socket;

    public UDPSocket() {
        try {
            EXT_IP = InetAddress.getByName(_EXT_IP);
            IP = InetAddress.getByName(_IP);

            socket = new MulticastSocket(PORT);
            socket.joinGroup(EXT_IP);
        } catch(IOException e) {
            e.getStackTrace();
        }
    }

    public DatagramPacket serialize(Response response) {
        return new DatagramPacket(response.getContent(), response.getContent().length, IP, EXT_PORT);
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

    public InetAddress getIp() {
        return IP;
    }
    public int getPort() {
        return PORT;
    }

    public InetAddress getExtIp() {
        return EXT_IP;
    }
    public int getExtPort() {
        return EXT_PORT;
    }

    public MulticastSocket getSocket() {
        return socket;
    }
}
