package com.meti;

import com.google.inject.AbstractModule;

class Data extends AbstractModule {
	private final Cache cache;

	Data(Cache cache) {
		this.cache = cache;
	}

	@Override
	protected void configure() {
		bind(FunctionBuilder.class).to(MutableFunctionBuilder.class);
		bind(Cache.class).toInstance(cache);
	}
}
