package com.meti;

interface Type {
	String render();

	boolean canAssignTo(Type parent);
}
