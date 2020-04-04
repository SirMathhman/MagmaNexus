package com.meti.extract;

import com.meti.JSONWritable;
import com.meti.Node;
import com.meti.Type;
import com.meti.data.Cache;
import com.meti.writable.Function;

import java.util.ArrayList;
import java.util.Collections;
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
		return new BuiltStructure(keys, name, type, content);
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

	private static final class BuiltStructure implements Structure {
		private final Node content;
		private final List<StructureKey> keys;
		private final String name;
		private final Type type;

		private BuiltStructure(List<StructureKey> keys, String name, Type type, Node content) {
			this.keys = Collections.unmodifiableList(keys);
			this.name = name;
			this.type = type;
			this.content = content;
		}

		@Override
		public Node appendToCache(Cache cache) {
			cache.append(name, toWritable());
			return new EmptyNode();
		}

		@Override
		public boolean isValid() {
			return keys.contains(StructureKey.VAR) || keys.contains(StructureKey.VAL);
		}

		@Override
		public String render() {
			return ((isMutable()) ? "var" : "val") +
			       " " + name +
			       " : () => " + type +
			       " = " + content.render();
		}

		private boolean isMutable() {
			return keys.contains(StructureKey.VAR);
		}

		@Override
		public JSONWritable toWritable() {
			return new Function(name, type.toWritable(), content.toWritable());
		}
	}
}