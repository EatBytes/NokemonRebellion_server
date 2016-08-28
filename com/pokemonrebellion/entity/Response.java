package com.pokemonrebellion.entity;

public class Response {
    private byte[] content;

    public Response(byte[] c) {
        content = c;
    }

    public byte[] getContent() {
        return content;
    }
}
