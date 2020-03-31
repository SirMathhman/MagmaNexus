package com.meti.writable;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meti.JSONWritable;

import java.util.function.Supplier;

public class Function implements JSONWritable {
	private final JSONWritable actions;
	private final String name;
	private final JSONWritable type;

	public Function(String name, JSONWritable type, JSONWritable actions) {
		this.name = name;
		this.type = type;
		this.actions = actions;
	}

	@Override
	public ObjectNode write(Supplier<? extends ObjectNode> supplier) {
		ObjectNode root = supplier.get();
		root.put("name", name);
		root.set("type", type.write(supplier));
		root.set("content", actions.write(supplier));
		return root;
	}
}
