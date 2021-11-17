package pl.forcode.tinyurlservice.idGenerator;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

class Base62IdGenerator {

	private static final Base62 base62 = new Base62();

	public static Set<String> range(BigInteger from, BigInteger to) {
		int initialCapacity = to.intValue() - from.intValue();
		Set<String> result = new HashSet<>(initialCapacity);

		while (!from.equals(to)) {
			result.add(base62.encode(from));
			from = from.add(new BigInteger("1"));
		}

		return result;
	}

}
