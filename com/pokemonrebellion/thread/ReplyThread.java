package com.pokemonrebellion.thread;

import com.pokemonrebellion.core.CoreDependency;
import com.pokemonrebellion.core.UDPSocket;
import com.pokemonrebellion.definition.ProcessorInterface;
import com.pokemonrebellion.entity.Request;
import com.pokemonrebellion.entity.Response;
import com.pokemonrebellion.processor.CloseProcessor;
import com.pokemonrebellion.processor.InvalidBufferProcessor;
import com.pokemonrebellion.processor.UpdatePositionProcessor;

import java.net.DatagramPacket;

public class ReplyThread implements Runnable {
    private static final int BUFFER_LENGTH = 1024;
    private static final byte[] BUFFER = new byte[BUFFER_LENGTH];
    private final ProcessorInterface[] processors = new ProcessorInterface[] {
            new CloseProcessor(),
            new InvalidBufferProcessor(),
            new UpdatePositionProcessor()
    };

    private UDPSocket socket;
    private boolean running = true;
    private CoreDependency coreDependency;

    public ReplyThread(CoreDependency c) {
        coreDependency = c;
    }

    @Override
    public void run() {
        DatagramPacket data = new DatagramPacket(BUFFER, BUFFER_LENGTH);
        socket = coreDependency.getUdpSocket();

        while (running) {
            System.out.println("[REPLY] => Waiting...");

            DatagramPacket packet = socket.receive(data);

            Request request = new Request(packet);
            Response response = processAction(request);
            sendReply(response, request);
        }
    }

    private Response processAction(Request request) {
        if (!request.getStatus()) {
            return processors[1].run(request);
        }

        coreDependency.getPosition().switchState(); //TEST

        return processors[request.getId()].run(request);
    }

    private void sendReply(Response response, Request request) {
        //Reply single client
        DatagramPacket resPacket = socket.serialize(response, request);
        socket.send(resPacket);

        //Reply group
        Response r = new Response(coreDependency.getPosition().getState().getBytes());
        DatagramPacket allPacket = socket.serialize(r);
        socket.send(allPacket);
    }
}
