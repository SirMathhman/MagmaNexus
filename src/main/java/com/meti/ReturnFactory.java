package com.meti;

import java.util.Optional;

class ReturnFactory implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		if (content.startsWith("return ")) {
			String valueString = content.substring(7);
			Node value = compiler.parse(valueString);
			return Optional.of(new Return(value));
		}
		return Optional.empty();
	}

	private static final class Return implements Node {
		private final Node value;

		private Return(Node value) {
			this.value = value;
		}

		@Override
		public String render() {
			return "return " + value.render() + ";";
		}

		@Override
		public JSONWritable toWritable() {
			return new ReturnAction(value.toWritable());
		}
	}
}
