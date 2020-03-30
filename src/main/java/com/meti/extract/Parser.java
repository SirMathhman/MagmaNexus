package com.meti.extract;

import com.meti.data.Compiler;
import com.meti.Node;

import java.util.Optional;

public interface Parser {
	Optional<? extends Node> parse(String content, Compiler compiler);
}
