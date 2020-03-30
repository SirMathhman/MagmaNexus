package com.meti;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

class BlockFactory implements Parser {

	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		return Optional.empty();
	}

	private class Block implements Node {
		private final Collection<? extends Node> content;

		private Block(Collection<? extends Node> content) {
			this.content = content;
		}

		@Override
		public String render() {
			return content.stream()
					.map(Node::render)
					.collect(Collectors.joining("", "{", "}"));
		}

		@Override
		public JSONWritable toWritable() {
			return null;
		}
	}

}
