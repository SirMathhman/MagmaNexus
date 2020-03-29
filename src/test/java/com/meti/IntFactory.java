package com.meti;

import java.util.Optional;

class IntFactory implements NodeFactory {
	@Override
	public Optional<Node> parse(String value) {
		return Optional.empty();
	}

	private class Int implements Node {
		private final int value;

		private Int(int value) {
			this.value = value;
		}

		@Override
		public String render() {
			return String.valueOf(value);
		}
	}
}
