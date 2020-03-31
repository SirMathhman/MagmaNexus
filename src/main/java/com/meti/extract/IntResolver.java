package com.meti.extract;

import com.meti.Type;
import com.meti.data.Compiler;

import java.util.Optional;

public class IntResolver implements Resolver {
	@Override
	public Optional<? extends Type> resolveName(String name, Compiler compiler) {
		return Optional.of(PrimitiveType.INT)
				.filter(s -> "Int".equals(name));
	}

	@Override
	public Optional<? extends Type> resolveValue(String value, Compiler compiler) {
		try {
			Integer.parseInt(value);
			return Optional.of(PrimitiveType.INT);
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}
}
