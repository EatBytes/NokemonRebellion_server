package com.pokemonrebellion.entity;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;

public class Request {
    private int id = 2;
    private int opt = 0;
    private String content;
    private DatagramPacket raw;
    private boolean status;

    public Request(DatagramPacket r) {
        DataInputStream buffer = getBuffer(r);

        try {
            id = buffer.readInt();
            opt = buffer.readInt();
            content = buffer.readUTF();
            status = true;
        } catch (IOException e) {
            status = false;
        }

        raw = r;
    }

    private DataInputStream getBuffer(DatagramPacket r) {
        byte[] bytes = r.getData();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        return new DataInputStream(bais);
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public DatagramPacket getRaw() {
        return raw;
    }

    public boolean getStatus() {
        return status;
    }
}
