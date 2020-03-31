package com.meti.extract;

import com.meti.JSONWritable;
import com.meti.Node;

import java.util.function.Supplier;

public class EmptyNode implements Node {
	@Override
	public String render() {
		return "";
	}

	@Override
	public JSONWritable toWritable() {
		return Supplier::get;
	}
}
