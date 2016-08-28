package com.pokemonrebellion.processor;

import com.pokemonrebellion.definition.ProcessorInterface;
import com.pokemonrebellion.entity.Request;
import com.pokemonrebellion.entity.Response;

public class CloseProcessor extends Processor implements ProcessorInterface {

	public Response run(Request request) {
		String c = "closing";
        
        return new Response(c.getBytes());
	}
}
