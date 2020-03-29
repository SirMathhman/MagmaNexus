package com.meti;

import com.google.inject.Injector;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class RootCompiler implements Compiler {
	private final NodeFactory root;

	public RootCompiler(NodeFactory root) {
		this.root = root;
	}

	public static Compiler from(Injector injector, Class<? extends NodeFactory>... factoryClasses) {
		return from(injector, List.of(factoryClasses));
	}

	public static Compiler from(Injector injector, Collection<Class<? extends NodeFactory>> factoryClasses) {
		List<? extends NodeFactory> factories = factoryClasses.stream()
				.map(injector::getInstance)
				.collect(Collectors.toList());
		NodeFactory factory = new CompoundFactory(factories);
		return new RootCompiler(factory);
	}

	@Override
	public void accept(String value) {
		root.parse(value).orElseThrow(() -> new IllegalArgumentException("Failed to parse: " + value));
	}
}
