package com.meti;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

public class Function implements JSONWritable {
	private final Collection<JSONWritable> actions;
	private final String name;
	private final String type;

	public Function(String name, String type, Collection<JSONWritable> actions) {
		this.name = name;
		this.type = type;
		this.actions = Collections.unmodifiableCollection(actions);
	}

	@Override
	public ObjectNode write(Supplier<? extends ObjectNode> supplier) {
		ObjectNode root = supplier.get();
		root.put("name", name);
		root.put("return", type);
		ArrayNode node = root.putArray("actions");
		actions.stream()
				.map(action -> action.write(supplier))
				.forEach(node::add);
		return root;
	}
}
