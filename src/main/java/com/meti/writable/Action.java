package com.meti.writable;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meti.JSONWritable;

import java.util.function.Supplier;

abstract class Action implements JSONWritable {
	private final String type;

	Action(String type) {
		this.type = type;
	}

	@Override
	public ObjectNode write(Supplier<? extends ObjectNode> supplier) {
		ObjectNode node = supplier.get();
		node.put("action", type);
		configure(node, supplier);
		return node;
	}

	protected abstract void configure(ObjectNode node, Supplier<? extends ObjectNode> supplier);
}
