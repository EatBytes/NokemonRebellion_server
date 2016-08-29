package com.pokemonrebellion.thread;

import com.pokemonrebellion.core.CoreDependency;
import com.pokemonrebellion.core.UDPSocket;
import com.pokemonrebellion.definition.ProcessorInterface;
import com.pokemonrebellion.entity.Request;
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

    @Override
    public void run() {
        DatagramPacket data = new DatagramPacket(BUFFER, BUFFER_LENGTH);
        socket = CoreDependency.getInstance().getUdpSocket();

        while (running) {
            System.out.println("[REPLY] => Waiting...");

            DatagramPacket packet = socket.receive(data);

            Request request = new Request(packet);
            processAction(request);
        }
    }

    private void processAction(Request request) {
        if (!request.getStatus()) {
            processors[1].run(request);
        }

        processors[request.getId()].run(request);
    }
}
