package com.meti;

import java.util.Optional;

interface NodeFactory {
	Optional<Node> parse(String value);
}
