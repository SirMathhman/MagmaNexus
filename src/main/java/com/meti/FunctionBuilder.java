package com.meti;

import java.util.ArrayList;
import java.util.List;

public interface FunctionBuilder {
	void append(JSONWritable action);

	void bind(String type);

	JSONWritable complete(String name);

}
