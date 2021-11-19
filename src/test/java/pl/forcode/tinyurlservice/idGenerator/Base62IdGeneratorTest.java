package pl.forcode.tinyurlservice.idGenerator;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Base62IdGeneratorTest {


	@Test
	void shouldGenerateRangeOfIds() {
		Collection<String> idsRange = Base62IdGenerator.range(BigInteger.ONE, BigInteger.valueOf(101));

		assertEquals(100, idsRange.size());
	}

	@Test
	void shouldGenerateOverMillionNumbers() {
		Collection<String> idsRange = Base62IdGenerator.range(BigInteger.ONE, BigInteger.valueOf(1000001));

		assertEquals(1000_000, idsRange.size());
	}

}
