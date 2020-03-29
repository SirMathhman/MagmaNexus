package com.meti;

enum Primitive implements Type {
	INT("Int");

	private final String value;

	Primitive(String value) {
		this.value = value;
	}

	@Override
	public String render() {
		return value;
	}
}
