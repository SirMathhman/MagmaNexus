package com.meti;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class StructureFactory implements Parser {
	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		/*
		val x : () => Int = {return 10;}
		{
			before = {
				mutable = "val",
				name = "x",
				params = [],
				return = "Int"
			},
			after = {
				content = "return 10"
			}
		}
		*/
		String atIndex;
		int index = 0;
		do {
			index = content.indexOf('=', index);
			if (-1 == index) {
				throw new ParseException("No assignment was found.");
			}
			atIndex = content.substring(index, index + 2);
		} while ("=>".equals(atIndex));
		String before = content.substring(0, index);
		String after = content.substring(index + 1);
		Node value = compiler.parse(after);
		Type actual = compiler.resolveValue(after);
		int typeIndex = before.indexOf(':');
		Type type;
		if (-1 == typeIndex) {
			type = actual;
		} else {
			String typeString = before.substring(typeIndex + 1);
			Type expected = compiler.resolveName(typeString);
			if (actual.canAssignTo(expected)) {
				type = expected;
			} else {
				throw new ParseException(actual + " is not assignable to " + expected);
			}
		}
		String keyString = -1 == typeIndex ? before : before.substring(0, typeIndex);
		int lastSpace = keyString.lastIndexOf(' ');
		String keys = keyString.substring(0, lastSpace);
		String name = keyString.substring(lastSpace + 1);
		List<StructureKey> keyList = Arrays.stream(keys.split(" "))
				.map(String::toUpperCase)
				.map(StructureKey::valueOf)
				.collect(Collectors.toList());
		if (!keyList.contains(StructureKey.VAL) && !keyList.contains(StructureKey.VAR)) {
			return Optional.empty();
		}
		return Optional.of(new Structure(keyList.contains(StructureKey.VAR), name, type, value));
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

		@Override
		public JSONWritable toWritable() {
			return new Function(name, type.render(), content.toWritable());
		}
	}
}
