package com.meti.extract;

import com.meti.JSONWritable;

public interface FunctionBuilder {
	void append(JSONWritable action);

	void bind(String type);

	JSONWritable complete(String name);

}
