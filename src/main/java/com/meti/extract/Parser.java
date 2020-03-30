package com.meti.extract;

import com.meti.data.Compiler;
import com.meti.Node;

import java.util.Optional;

public interface Parser {
	Optional<Node> parse(String content, Compiler compiler);
}
