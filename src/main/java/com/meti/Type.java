package com.meti;

public interface Type {
	String render();

	boolean canAssignTo(Type parent);
}
