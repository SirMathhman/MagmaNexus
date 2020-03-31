package com.meti.data;

import com.google.inject.Injector;
import com.meti.Node;
import com.meti.Type;
import com.meti.extract.CompoundFactory;
import com.meti.extract.Unit;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RootCompiler implements Compiler {
	private final Unit root;

	public RootCompiler(Unit root) {
		this.root = root;
	}

	public static Compiler from(Injector injector, Class<?>... factoryClasses) {
		return from(injector, List.of(factoryClasses));
	}

	public static Compiler from(Injector injector, Collection<Class<?>> factoryClasses) {
		List<?> factories = factoryClasses.stream()
				.map(injector::getInstance)
				.collect(Collectors.toList());
		Unit factory = new CompoundFactory(factories);
		return new RootCompiler(factory);
	}

	@Override
	public Node parse(String value) {
		return root.parse(value, this).orElseThrow(() -> new IllegalArgumentException("Failed to parse: \"" + value +
		                                                                              "\""));
	}

	@Override
	public Type resolveName(String name) {
		return root.resolveName(name, this).orElseThrow(() -> new IllegalArgumentException("Failed to resolve type of" +
		                                                                                   " " +
		                                                                                   "name: \"" + name + "\""));
	}

	@Override
	public Type resolveValue(String value) {
		return root.resolveValue(value, this).orElseThrow(() -> new IllegalArgumentException("Failed to resolve type " +
		                                                                                     "of value: \"" + value +
		                                                                                     "\""));
	}
}
