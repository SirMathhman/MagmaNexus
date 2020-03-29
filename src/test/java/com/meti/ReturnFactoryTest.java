package com.meti;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReturnFactoryTest {

	@Test
	void parse() throws JsonProcessingException {
		String value = "return 10";
		Injector injector = Guice.createInjector();
		Node node = RootCompiler.from(injector,
				ReturnFactory.class,
				IntFactory.class).parse(value);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode tree = node.toWritable().write(mapper::createObjectNode);
		String result = mapper.writeValueAsString(tree);
		assertEquals("{\"action\":\"return\",\"value\":{\"type\":\"int\",\"value\":10}}", result);
	}
}