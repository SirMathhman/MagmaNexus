package com.meti;

import java.util.Optional;

class ReturnFactory implements NodeFactory {
	@Override
	public Optional<Node> parse(String value) {
		return Optional.empty();
	}

	private class Return implements Node {
		private final Node value;

		private Return(Node value) {
			this.value = value;
		}

		@Override
		public String render() {
			return "return " + value.render() + ";";
		}
	}
}
