package com.meti;

import java.util.Map;

public interface Cache {
	void append(String name, JSONWritable writable);

	Map<String, String> render();
}
