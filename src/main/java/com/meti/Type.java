package com.meti;

public interface Type {
	boolean canAssignTo(Type parent);

	String render();

	JSONWritable toWritable();
}
