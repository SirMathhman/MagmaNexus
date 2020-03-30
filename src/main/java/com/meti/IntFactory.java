package com.meti;

import java.util.Optional;

class IntFactory implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		try {
			int i = Integer.parseInt(content);
			return Optional.of(new Int(i));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
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

		@Override
		public JSONWritable toWritable() {
			return new IntValue(value);
		}
	}
}
