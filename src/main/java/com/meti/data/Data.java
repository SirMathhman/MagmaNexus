package com.meti.data;

import com.google.inject.AbstractModule;

public class Data extends AbstractModule {
	private final Cache cache;

	public Data(Cache cache) {
		this.cache = cache;
	}

	@Override
	protected void configure() {
		bind(Cache.class).toInstance(cache);
	}
}
