package com.meti;

enum Primitive implements Type {
	INT("Int");

	private final String value;

	Primitive(String value) {
		this.value = value;
	}

	@Override
	public boolean canAssignTo(Type parent){
		return false;
	}

	@Override
	public String render() {
		return value;
	}
}
