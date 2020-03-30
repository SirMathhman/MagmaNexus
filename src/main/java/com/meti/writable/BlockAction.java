package com.meti.writable;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meti.JSONWritable;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

public class BlockAction extends Action {
	private final Collection<JSONWritable> children;

	public BlockAction(Collection<JSONWritable> children) {
		this("block", children);
	}

	BlockAction(String type, Collection<JSONWritable> children) {
		super(type);
		this.children = Collections.unmodifiableCollection(children);
	}

	@Override
	protected void configure(ObjectNode node, Supplier<? extends ObjectNode> supplier) {
		ArrayNode array = node.putArray("actions");
		children.stream()
				.map(action ->  action.write(supplier))
				.forEach(array::add);
	}
}
