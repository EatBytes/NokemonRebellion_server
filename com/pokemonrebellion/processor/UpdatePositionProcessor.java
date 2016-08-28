package com.pokemonrebellion.processor;

import com.pokemonrebellion.definition.ProcessorInterface;
import com.pokemonrebellion.entity.Request;
import com.pokemonrebellion.entity.Response;

public class UpdatePositionProcessor extends Processor implements ProcessorInterface {

    public Response run(Request request) {
        byte[] content = getOutputStream(new int[2]);

        return new Response(content);
    }
}
