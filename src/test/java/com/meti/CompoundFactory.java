package com.meti;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class CompoundFactory implements Parser {
	private final Collection<? extends Parser> factories;

	public CompoundFactory(Collection<? extends Parser> factories) {
		this.factories = Collections.unmodifiableCollection(factories);
	}

	@Override
	public Optional<Node> parse(String content, Compiler compiler) {
		return factories.stream()
				.map(nodeFactory -> nodeFactory.parse(content, compiler))
				.flatMap(Optional::stream)
				.findFirst();
	}
}
