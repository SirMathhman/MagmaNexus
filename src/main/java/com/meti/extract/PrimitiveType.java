package com.meti.extract;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meti.JSONWritable;
import com.meti.Type;

import java.util.function.Supplier;

enum PrimitiveType implements Type, JSONWritable {
	INT("Int");

	private final String value;

	PrimitiveType(String value) {
		this.value = value;
	}

	@Override
	public boolean canAssignTo(Type parent) {
		return false;
	}

	@Override
	public String render() {
		return value;
	}

	@Override
	public JSONWritable toWritable() {
		return this;
	}

	@Override
	public ObjectNode write(Supplier<? extends ObjectNode> supplier) {
		ObjectNode node = supplier.get();
		node.put("name", name().toLowerCase());
		return node;
	}
}
