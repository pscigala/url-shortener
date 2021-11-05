package pl.forcode.tinyurlservice.idGenerator;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Base62IdGeneratorTest {


	@Test
	void testRangeGernation() {
		Collection<String> idsRange = Base62IdGenerator.range(new BigInteger("1"), new BigInteger("101"));

		assertEquals(100, idsRange.size());
	}

	@Test
	void testOverMillionNumbers() {
		Collection<String> idsRange = Base62IdGenerator.range(new BigInteger("1"), new BigInteger("1000001"));

		assertEquals(1000_000, idsRange.size());
	}

}
