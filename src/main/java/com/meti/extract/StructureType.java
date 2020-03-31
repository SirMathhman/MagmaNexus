package com.meti.extract;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meti.JSONWritable;
import com.meti.Type;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class StructureType implements Type, JSONWritable {
	private final Collection<? extends Type> parameters;
	private final Type returnType;

	public StructureType(Collection<? extends Type> parameters, Type returnType) {
		this.parameters = Collections.unmodifiableCollection(parameters);
		this.returnType = returnType;
	}

	@Override
	public boolean canAssignTo(Type parent) {
		//TODO: structure resolution
		return parent.equals(WildcardType.INSTANCE);
	}

	@Override
	public String render() {
		String paramString = parameters.stream()
				.map(Type::render)
				.collect(Collectors.joining(",", "(", ")"));
		return paramString + "=>" + returnType.render();
	}

	@Override
	public JSONWritable toWritable() {
		return this;
	}

	@Override
	public ObjectNode write(Supplier<? extends ObjectNode> supplier) {
		ObjectNode node = supplier.get();
		ArrayNode paramNode = node.putArray("params");
		parameters.stream()
				.map(Type::toWritable)
				.map(writable -> writable.write(supplier))
				.forEach(paramNode::add);
		node.set("return", returnType.toWritable().write(supplier));
		return node;
	}
}
