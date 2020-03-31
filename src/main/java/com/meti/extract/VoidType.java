package com.meti.extract;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meti.JSONWritable;
import com.meti.Type;

import java.util.function.Supplier;

public class VoidType implements Type, JSONWritable {
	public static final Type INSTANCE = new VoidType();

	private VoidType() {
	}

	@Override
	public String render() {
		return "Void";
	}

	@Override
	public JSONWritable toWritable() {
		return this;
	}

	@Override
	public boolean canAssignTo(Type parent) {
		return parent.equals(INSTANCE);
	}

	@Override
	public ObjectNode write(Supplier<? extends ObjectNode> supplier) {
		ObjectNode node = supplier.get();
		node.put("name", "void");
		return node;
	}
}
