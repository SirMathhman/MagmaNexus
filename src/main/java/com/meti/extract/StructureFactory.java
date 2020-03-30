package com.meti.extract;

import com.meti.Node;
import com.meti.Type;
import com.meti.data.Compiler;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StructureFactory implements Parser {
	@Override
	public Optional<? extends Node> parse(String content, Compiler compiler) {
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
		int index = findEquals(content);
		return Optional.of(index)
				.filter(integer -> -1 != integer)
				.flatMap(integer -> parseValid(content, compiler, index));
	}

	private static int findEquals(String content) {
		String atIndex = "";
		int index = -1;
		do {
			index = content.indexOf('=', index + 1);
			if (-1 != index) atIndex = content.substring(index, index + 2);
		} while ("=>".equals(atIndex) || -1 == index);
		return index;
	}

	private static Optional<? extends Node> parseValid(String content, Compiler compiler, int index) {
		String before = content.substring(0, index);
		String after = content.substring(index + 1);
		StructureBuilder builder = new FunctionalStructureBuilder();
		StructureBuilder withBefore = parseBefore(before, builder);
		StructureBuilder withAfter = parseAfter(compiler, before, after, withBefore);
		return wrapInOptional(withAfter);
	}

	private static StructureBuilder parseBefore(String before, StructureBuilder structureBuilder) {
		String nameString = parseNameString(before);
		String name = parseName(nameString);
		List<StructureKey> keys = parseKeys(nameString);
		return structureBuilder
				.withKeys(keys)
				.withName(name);
	}

	private static StructureBuilder parseAfter(Compiler compiler, String before, String after,
	                                           StructureBuilder builder) {
		Node value = compiler.parse(after);
		Type actual = compiler.resolveValue(after);
		Type type = before.contains(":") ? actual : validate(compiler, before, actual);
		return builder.withType(type)
				.withContent(value);
	}

	private static Optional<? extends Node> wrapInOptional(StructureBuilder builder) {
		return Optional.of(builder)
				.map(StructureBuilder::build)
				.filter(Structure::isValid);
	}

	private static String parseNameString(String before) {
		if (before.contains(":")) {
			int index = before.indexOf(':');
			return before.substring(0, index).trim();
		}
		return before.trim();
	}

	private static String parseName(String nameString) {
		int space = nameString.lastIndexOf(' ');
		return nameString.substring(space + 1);
	}

	private static List<StructureKey> parseKeys(String keyString) {
		int space = keyString.lastIndexOf(' ');
		String keys = keyString.substring(0, space);
		return convertKeyStringToList(keys);
	}

	private static Type validate(Compiler compiler, String before, Type actual) {
		int index = before.indexOf(':');
		String typeString = before.substring(index + 1);
		Type expected = compiler.resolveName(typeString);
		if (!actual.canAssignTo(expected)) throw new ExtractException(actual + " is not assignable to " + expected);
		return expected;
	}

	private static List<StructureKey> convertKeyStringToList(String keyString) {
		return Arrays.stream(keyString.split(" "))
				.map(String::toUpperCase)
				.map(StructureKey::valueOf)
				.collect(Collectors.toList());
	}
}
