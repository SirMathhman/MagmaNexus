package com.meti.extract;

import com.meti.Type;
import com.meti.data.Compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StructureResolver implements Resolver {
	@Override
	public Optional<? extends Type> resolveName(String name, Compiler compiler) {
		if (name.startsWith("(") || name.contains("=>")) {
			int index = findParamEnd(name) + 1;
			String before = name.substring(0, index).trim();
			String paramString = before.substring(1, before.length() - 1);
			StringBuilder buffer = new StringBuilder();
			List<String> paramTypes = new ArrayList<>();
			int depth = 0;
			for (char c : paramString.toCharArray()) {
				if (c == ',' && depth == 0) {
					paramTypes.add(buffer.toString());
					buffer = new StringBuilder();
				} else {
					if (c == '(') {
						depth++;
					} else if (c == ')') {
						depth--;
					}
					buffer.append(c);
				}
			}
			paramTypes.add(buffer.toString());
			List<Type> types = paramTypes.stream()
					.filter(s -> !s.isBlank())
					.map(String::trim)
					.map(compiler::resolveName)
					.collect(Collectors.toList());
			String after = name.substring(index + 1).trim();
			Type returnType;
			if (after.startsWith("=>")) {
				String returnString = after.substring(2);
				returnType = compiler.resolveName(returnString.trim());
			} else {
				returnType = VoidType.INSTANCE;
			}
			return Optional.of(new StructureType(types, returnType));
		}
		return Optional.empty();
	}

	private int findParamEnd(String name) {
		int index = 0;
		int depth = 0;
		char[] charArray = name.substring(1).toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if (c == '(') {
				depth++;
			} else if (c == ')') {
				if (depth == 0) {
					index = i;
					break;
				} else {
					depth--;
				}
			}
		}
		return index + 1;
	}

	@Override
	public Optional<? extends Type> resolveValue(String value, Compiler compiler) {
		if (value.startsWith("{") && value.endsWith("}")) {
			return Optional.of(WildcardType.INSTANCE);
		}
		return Optional.empty();
	}
}
