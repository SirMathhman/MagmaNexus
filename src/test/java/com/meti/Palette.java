package com.meti;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Palette {
	@Test
	void test() {
		String value = "val main : () => Int = {return 0;}";
		Cache cache = new MappedCache();
		Injector injector = Guice.createInjector(new Data(cache));
		RootCompiler.from(injector,
				StructureFactory.class,
				ReturnFactory.class,
				IntFactory.class).parse(value);
		Map<String, String> functions = cache.render();
		assertEquals(1, functions.size());
		assertEquals("{}", functions.get("main"));
	}
}
