package com.meti.extract;

import com.meti.JSONWritable;
import com.meti.Node;
import com.meti.Type;
import com.meti.data.Cache;
import com.meti.writable.Function;

import java.util.Collections;
import java.util.List;

class Structure implements Node {
	private final Node content;
	private final List<StructureKey> keys;
	private final String name;
	private final Type type;

	public Structure(List<StructureKey> keys, String name, Type type, Node content) {
		this.keys = Collections.unmodifiableList(keys);
		this.name = name;
		this.type = type;
		this.content = content;
	}

	public Node appendToCache(Cache cache) {
		cache.append(name, toWritable());
		return new EmptyNode();
	}

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
		return new Function(name, type.render(), content.toWritable());
	}
}
