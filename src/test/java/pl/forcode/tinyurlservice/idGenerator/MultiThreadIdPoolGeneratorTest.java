package pl.forcode.tinyurlservice.idGenerator;

import org.junit.jupiter.api.Test;

import java.util.List;

class MultiThreadIdPoolGeneratorTest {

	@Test
	void v(){
		MultiThreadIdPoolGenerator gener = new MultiThreadIdPoolGenerator();

		List<IdsPool> idsPools = gener.nextIdPools(1);
		List<IdsPool> idsPools2 = gener.nextIdPools(6);
		List<IdsPool> idsPools3 = gener.nextIdPools(11);

		System.out.println(idsPools.size());
	}
}
