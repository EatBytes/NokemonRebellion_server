package com.pokemonrebellion.thread;

import com.pokemonrebellion.core.Position;

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MultiThread implements Runnable {
    private MulticastSocket socket;
    private InetAddress IP;
    private int PORT;
    private boolean running = true;
    private Position position;

    public MultiThread(MulticastSocket s, InetAddress ip, int port, Position p) {
        socket = s;
        IP = ip;
        PORT = port;
        position = p;
    }

    @Override
    public void run() {
        while (running) {
            String s = position.getState();
            DatagramPacket up = new DatagramPacket(s.getBytes(), s.length(), IP, PORT);

            try {
                socket.send(up);
            } catch (IOException e) {
                e.getStackTrace();
                running = false;
            }
        }
    }
}
