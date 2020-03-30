package com.meti.extract;

import com.meti.data.Compiler;
import com.meti.JSONWritable;
import com.meti.Node;
import com.meti.writable.IntValue;

import java.util.Optional;

public class IntFactory implements Parser {
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
