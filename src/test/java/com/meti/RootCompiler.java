package com.meti;

import com.google.inject.Injector;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class RootCompiler implements Compiler {
	private final Parser root;

	public RootCompiler(Parser root) {
		this.root = root;
	}

	public static Compiler from(Injector injector, Class<? extends Parser>... factoryClasses) {
		return from(injector, List.of(factoryClasses));
	}

	public static Compiler from(Injector injector, Collection<Class<? extends Parser>> factoryClasses) {
		List<? extends Parser> factories = factoryClasses.stream()
				.map(injector::getInstance)
				.collect(Collectors.toList());
		Parser factory = new CompoundFactory(factories);
		return new RootCompiler(factory);
	}

	@Override
	public Node parse(String value) {
		return root.parse(value, this).orElseThrow(() -> new IllegalArgumentException("Failed to parse: " + value));
	}
}
