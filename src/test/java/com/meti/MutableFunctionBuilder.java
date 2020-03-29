package com.meti;

import java.util.ArrayList;
import java.util.List;

public class MutableFunctionBuilder implements FunctionBuilder {
	private final List<JSONWritable> actions = new ArrayList<>();
	private String type = null;

	@Override
	public void append(JSONWritable action) {
		this.actions.add(action);
	}

	@Override
	public void bind(String type) {
		this.type = type;
	}

	@Override
	public JSONWritable complete(String name) {
		List<JSONWritable> actionsCopy = new ArrayList<>(actions);
		JSONWritable writable = new Function(name, type, actionsCopy);
		this.type = null;
		this.actions.clear();
		return writable;
	}
}