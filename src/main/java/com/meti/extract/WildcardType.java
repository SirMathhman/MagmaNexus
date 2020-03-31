package com.meti.extract;

import com.meti.JSONWritable;
import com.meti.Type;

public class WildcardType implements Type {
	public static final Type INSTANCE = new WildcardType();

	private WildcardType() {
	}

	@Override
	public boolean canAssignTo(Type parent) {
		return true;
	}

	@Override
	public String render() {
		throw new IllegalArgumentException("Cannot render wildcard type.");
	}

	@Override
	public JSONWritable toWritable() {
		throw new IllegalArgumentException("Cannot convert wildcard type to JSON.");
	}
}
