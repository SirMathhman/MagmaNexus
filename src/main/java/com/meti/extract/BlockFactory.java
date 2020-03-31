package com.meti.extract;

import com.meti.JSONWritable;
import com.meti.Node;
import com.meti.data.Compiler;
import com.meti.writable.BlockAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class BlockFactory implements Parser {
	@Override
	public Optional<? extends Node> parse(String content, Compiler compiler) {
		if (content.startsWith("{") && content.endsWith("}")) {
			String values = content.substring(1, content.length() - 1);
			StringBuilder buffer = new StringBuilder();
			List<String> lines = new ArrayList<>();
			int depth = 0;
			for (char c : values.toCharArray()) {
				if (c == ';' && depth == 0) {
					lines.add(buffer.toString());
					buffer = new StringBuilder();
				} else {
					if (c == '{') {
						depth++;
					} else if (c == '}') {
						depth--;
					}
					buffer.append(c);
				}
			}
			lines.add(buffer.toString());
			List<Node> nodes = lines.stream()
					.filter(s -> !s.isBlank())
					.map(String::trim)
					.map(compiler::parse)
					.collect(Collectors.toList());
			return Optional.of(new Block(nodes));
		}
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
			List<JSONWritable> writables = content.stream()
					.map(Node::toWritable)
					.collect(Collectors.toList());
			return new BlockAction(writables);
		}
	}
}
