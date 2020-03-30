package com.meti;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.function.Supplier;

public class IntValue implements JSONWritable {
	private final int value;

	public IntValue(int value) {
		this.value = value;
	}

	@Override
	public ObjectNode write(Supplier<? extends ObjectNode> supplier) {
		ObjectNode node = supplier.get();
		node.put("type", "int");
		node.put("value", value);
		return node;
	}
}
