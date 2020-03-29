package com.meti;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.function.Supplier;

public interface JSONWritable {
	ObjectNode write(Supplier<? extends ObjectNode> supplier);
}
