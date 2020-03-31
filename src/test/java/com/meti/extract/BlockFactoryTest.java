package com.meti.extract;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.meti.Node;
import com.meti.data.RootCompiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockFactoryTest {

	@Test
	void parse() throws JsonProcessingException {
		String value = "{return 10;return 20;}";
		Injector injector = Guice.createInjector();
		Node node = RootCompiler.from(injector,
				BlockFactory.class,
				ReturnFactory.class,
				IntFactory.class).parse(value);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode tree = node.toWritable().write(mapper::createObjectNode);
		String result = mapper.writeValueAsString(tree);
		assertEquals("{\"action\":\"block\",\"actions\":[{\"action\":\"return\",\"value\":{\"type\":\"int\"," +
		             "\"value\":10}},{\"action\":\"return\",\"value\":{\"type\":\"int\",\"value\":20}}]}", result);
	}
}