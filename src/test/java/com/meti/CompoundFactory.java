package com.meti;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class CompoundFactory implements NodeFactory {
	private final Collection<? extends NodeFactory> factories;

	public CompoundFactory(Collection<? extends NodeFactory> factories) {
		this.factories = Collections.unmodifiableCollection(factories);
	}

	@Override
	public Optional<Node> parse(String value) {
		return factories.stream()
				.map(nodeFactory -> nodeFactory.parse(value))
				.flatMap(Optional::stream)
				.findFirst();
	}
}
