package com.pokemonrebellion.processor;

import com.pokemonrebellion.definition.ProcessorInterface;
import com.pokemonrebellion.entity.Request;
import com.pokemonrebellion.entity.Response;

public class InvalidBufferProcessor extends Processor implements ProcessorInterface {

    public Response run(Request request) {
        String c = "invalid_buffer";

        return new Response(c.getBytes());
    }
}
