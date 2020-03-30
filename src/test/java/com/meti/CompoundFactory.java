package com.meti;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CompoundFactory implements Parser, Resolver {
	private final Collection<Parser> parsers = new ArrayList<>();
	private final Collection<Resolver> resolvers = new ArrayList<>();

	public CompoundFactory(Iterable<?> instances) {
		for (Object factory : instances) {
			if (factory instanceof Parser) parsers.add((Parser) factory);
			if (factory instanceof Resolver) resolvers.add((Resolver) instances);
		}
	}

	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		return parsers.stream()
				.map(nodeFactory -> nodeFactory.parse(content, compiler))
				.flatMap(Optional::stream)
				.findFirst();
	}

	@Override
	public Optional<Type> resolveName(String name, Compiler compiler) {
		return resolvers.stream()
				.map(nodeFactory -> nodeFactory.resolveName(name, compiler))
				.flatMap(Optional::stream)
				.findFirst();
	}

	@Override
	public Optional<Type> resolveValue(String value, Compiler compiler) {
		return resolvers.stream()
				.map(nodeFactory -> nodeFactory.resolveValue(value, compiler))
				.flatMap(Optional::stream)
				.findFirst();
	}
}
