package com.meti.data;

import com.meti.Node;
import com.meti.Type;

public interface Compiler {
	Node parse(String value);

	Type resolveName(String name);

	Type resolveValue(String value);
}
