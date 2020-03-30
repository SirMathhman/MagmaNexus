package com.meti.data;

import com.google.inject.AbstractModule;
import com.meti.extract.FunctionBuilder;
import com.meti.extract.MutableFunctionBuilder;

public class Data extends AbstractModule {
	private final Cache cache;

	public Data(Cache cache) {
		this.cache = cache;
	}

	@Override
	protected void configure() {
		bind(FunctionBuilder.class).to(MutableFunctionBuilder.class);
		bind(Cache.class).toInstance(cache);
	}
}
