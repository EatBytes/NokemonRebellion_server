package com.pokemonrebellion.definition;

import com.pokemonrebellion.entity.Request;
import com.pokemonrebellion.entity.Response;

public interface ProcessorInterface {
	Response run(Request request);
}
