package com.meti.extract;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.meti.data.Cache;
import com.meti.data.Data;
import com.meti.data.MappedCache;
import com.meti.data.RootCompiler;
import com.meti.extract.IntFactory;
import com.meti.extract.ReturnFactory;
import com.meti.extract.StructureFactory;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StructureFactoryTest {
	@Test
	void parse() {
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
