package com.meti;

import java.util.Optional;

class StructureFactory implements NodeFactory {
	@Override
	public Optional<Node> parse(String value) {
		return Optional.empty();
	}

	static class Structure implements Node {
		private final Node content;
		private final boolean mutable;
		private final String name;
		private final Type type;

		public Structure(boolean mutable, String name, Type type, Node content) {
			this.mutable = mutable;
			this.name = name;
			this.type = type;
			this.content = content;
		}

		@Override
		public String render() {
			return ((mutable) ? "var" : "val") +
			       " " + name +
			       " : () => " + type +
			       " = " + content.render();
		}
	}
}
