package com.meti;

import java.util.Optional;

interface Parser {
	Optional<Node> parse(String content, Compiler compiler);
}
