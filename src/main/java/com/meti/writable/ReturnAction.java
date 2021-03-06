package com.meti.writable;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meti.JSONWritable;

import java.util.function.Supplier;

public class ReturnAction extends Action {
	private final JSONWritable value;

	public ReturnAction(JSONWritable value) {
		super("return");
		this.value = value;
	}

	@Override
	protected void configure(ObjectNode node, Supplier<? extends ObjectNode> supplier) {
		node.set("value", value.write(supplier));
	}
}
