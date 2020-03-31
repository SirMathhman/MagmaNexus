package com.meti.extract;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmptyNodeTest {

	@Test
	void render() {
		assertTrue(new EmptyNode().render().isBlank());
	}

	@Test
	void toWritable() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode json = new EmptyNode()
				.toWritable()
				.write(mapper::createObjectNode);
		assertEquals("{}", mapper.writeValueAsString(json));
	}
}