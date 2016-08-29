package com.pokemonrebellion.processor;

import com.pokemonrebellion.core.UDPSocket;
import com.pokemonrebellion.definition.ProcessorInterface;
import com.pokemonrebellion.entity.Request;
import com.pokemonrebellion.entity.Response;

import java.net.DatagramPacket;

public class UpdatePositionProcessor extends Processor implements ProcessorInterface {

    public void run(Request request) {
        //byte[] content = getOutputStream(new int[2]);
        String s = request.getContent();
        Response r = new Response(s.getBytes());

        String b = "client";
        Response r2 = new Response(b.getBytes());

        UDPSocket so = getCoreDependency().getUdpSocket();

        DatagramPacket replyToClient = so.serialize(r2, request);
        so.send(replyToClient);

        DatagramPacket replyToAll = so.serialize(r);
        so.send(replyToAll);
    }
}
