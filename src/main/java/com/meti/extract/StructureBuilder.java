package com.meti.extract;

import com.meti.Node;
import com.meti.Type;

import java.util.List;

public interface StructureBuilder {
	Structure build();

	StructureBuilder withContent(Node content);

	StructureBuilder withKeys(List<StructureKey> keys);

	StructureBuilder withName(String name);

	StructureBuilder withType(Type type);
}
