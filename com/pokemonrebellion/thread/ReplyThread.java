package com.pokemonrebellion.thread;

import com.pokemonrebellion.core.CoreDependency;
import com.pokemonrebellion.core.SingleSocket;
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

    private SingleSocket socket;
    private boolean running = true;
    private CoreDependency coreDependency;

    public ReplyThread(CoreDependency c) {
        coreDependency = c;
    }

    @Override
    public void run() {
        DatagramPacket packet = new DatagramPacket(BUFFER, BUFFER_LENGTH);
        socket = coreDependency.getSingleSocket();

        while (running) {
            System.out.println("[REPLY] => Waiting...");

            packet = socket.receive(packet);

            Request request = new Request(packet);
            Response response = processAction(request);
            sendReply(response, request);
        }
    }

    private Response processAction(Request request) {
        if (!request.getStatus()) {
            return processors[1].run(request);
        }

        coreDependency.getPosition().switchState();

        return processors[request.getId()].run(request);
    }

    private void sendReply(Response response, Request request) {
        DatagramPacket resPacket = socket.serialize(response, request);
        socket.send(resPacket);

        Response r = new Response(coreDependency.getPosition().getState().getBytes());
        DatagramPacket allPacket = coreDependency.getMultiSocket().serialize(r);
        coreDependency.getMultiSocket().send(allPacket);
    }
}
