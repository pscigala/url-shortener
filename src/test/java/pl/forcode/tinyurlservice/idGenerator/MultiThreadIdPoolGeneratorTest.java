package pl.forcode.tinyurlservice.idGenerator;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MultiThreadIdPoolGeneratorTest {

	@Test
	void shouldReturnIdPoolsGeneratedByMultipleThreads() {
		MultiThreadIdPoolGenerator generator = new MultiThreadIdPoolGenerator();

		List<IdsPool> idsPools = generator.generateNextPools(70);

		assertNotNull(idsPools);
	}
}
