package com.pokemonrebellion.processor;

import com.pokemonrebellion.definition.ProcessorInterface;
import com.pokemonrebellion.entity.Request;

public class InvalidBufferProcessor extends Processor implements ProcessorInterface {

    public void run(Request request) {
        String c = "invalid_buffer";
    }
}
