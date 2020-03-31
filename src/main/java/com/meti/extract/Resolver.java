package com.meti.extract;

import com.meti.data.Compiler;
import com.meti.Type;

import java.util.Optional;

public interface Resolver {
	Optional<? extends Type> resolveName(String name, Compiler compiler);

	Optional<? extends Type> resolveValue(String value, Compiler compiler);
}
