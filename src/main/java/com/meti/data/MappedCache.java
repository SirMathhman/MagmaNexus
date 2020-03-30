package com.meti.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meti.JSONWritable;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MappedCache implements Cache {
	private final Map<String, JSONWritable> functions = new HashMap<>();
	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void append(String name, JSONWritable writable) {
		if (functions.containsKey(name)) {
			throw new IllegalArgumentException(name + " is already defined.");
		}

		functions.put(name, writable);
	}

	@Override
	public Map<String, String> render() {
		return functions.keySet()
				.stream()
				.collect(Collectors.toMap(java.util.function.Function.identity(), this::toJSON));
	}

	private String toJSON(String s) {
		try {
			JSONWritable writable = functions.get(s);
			ObjectNode jsonNode = writable.write(mapper::createObjectNode);
			return mapper.writeValueAsString(jsonNode);
		} catch (JsonProcessingException e) {
			return "";
		}
	}
}
