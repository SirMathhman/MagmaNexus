package com.meti.extract;

import com.meti.Node;
import com.meti.Type;

import java.util.ArrayList;
import java.util.List;

public class FunctionalStructureBuilder implements StructureBuilder {
	private final Node content;
	private final List<StructureKey> keys;
	private final String name;
	private final Type type;

	public FunctionalStructureBuilder() {
		this(null, new ArrayList<>(), null, null);
	}

	public FunctionalStructureBuilder(Node content, List<StructureKey> keys, String name, Type type) {
		this.content = content;
		this.keys = new ArrayList<>(keys);
		this.name = name;
		this.type = type;
	}

	@Override
	public Structure build() {
		return new Structure(keys, name, type, content);
	}

	@Override
	public StructureBuilder withContent(Node content) {
		return new FunctionalStructureBuilder(content, keys, name, type);
	}

	@Override
	public StructureBuilder withKeys(List<StructureKey> keys) {
		return new FunctionalStructureBuilder(content, keys, name, type);
	}

	@Override
	public StructureBuilder withName(String name) {
		return new FunctionalStructureBuilder(content, keys, name, type);
	}

	@Override
	public StructureBuilder withType(Type type) {
		return new FunctionalStructureBuilder(content, keys, name, type);
	}
}