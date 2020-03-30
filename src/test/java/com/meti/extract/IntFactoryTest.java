package com.meti.extract;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.meti.Node;
import com.meti.data.RootCompiler;
import com.meti.extract.IntFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntFactoryTest {

	@Test
	void parse() throws JsonProcessingException {
		String value = "10";
		Injector injector = Guice.createInjector();
		Node node = RootCompiler.from(injector, IntFactory.class).parse(value);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode tree = node.toWritable().write(mapper::createObjectNode);
		String result = mapper.writeValueAsString(tree);
		assertEquals("{\"type\":\"int\",\"value\":10}", result);
	}
}