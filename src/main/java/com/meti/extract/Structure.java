package com.meti.extract;

import com.meti.Node;
import com.meti.data.Cache;

public interface Structure extends Node {
	Node appendToCache(Cache cache);

	boolean isValid();
}
