package pl.forcode.tinyurlservice.idGenerator;

import org.springframework.stereotype.Component;
import pl.forcode.tinyurlservice.idGenerator.exception.IdPoolGenerationInProgress;

import java.math.BigInteger;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReentrantLock;

@Component
class MultiThreadIdPoolGenerator implements IdPoolGenerator {

	private static final ReentrantLock lock = new ReentrantLock();

	private static final int RANGE = 1_000_000;
	private static final BigInteger RANGE_OFFSET = new BigInteger(String.valueOf(RANGE));

	public static final int DEFAULT_ID_POOLS_AMOUNT = 5;

	private static final int PARALLELISM = ForkJoinPool.getCommonPoolParallelism();
	private static final ForkJoinPool pool = (ForkJoinPool) Executors.newWorkStealingPool(PARALLELISM);

	@Override
	public IdsPool generateNextPool(long lastPoolNumber) {
		return generateNextPools(lastPoolNumber, 1).stream()
				.findFirst()
				.orElseThrow();
	}

	@Override
	public List<IdsPool> generateNextPools(long lastPoolNumber) {
		return generateNextPools(lastPoolNumber, DEFAULT_ID_POOLS_AMOUNT);
	}

	@Override
	public List<IdsPool> generateNextPools(long lastPoolNumber, int poolsAmount) {
		if (lock.isLocked()) {
			throw new IdPoolGenerationInProgress();
		} else {
			lock.lock();
			try {
				List<IdsPool> r = new LinkedList<>();

				for (int i = 0; i < poolsAmount; i++) {
					Collection<String> generatedIds = generate(lastPoolNumber);
					r.add(new IdsPool(lastPoolNumber, generatedIds));
					lastPoolNumber++;
				}
				return r;
			} finally {
				lock.unlock();
			}
		}
	}


	private Collection<String> generate(long rangeNumber) {
		//todo propper math adding on biginteger
		BigInteger from = new BigInteger(String.valueOf(rangeNumber * RANGE - RANGE)).add(RANGE_OFFSET);
		BigInteger to = new BigInteger(String.valueOf(rangeNumber * RANGE)).add(RANGE_OFFSET);
		return generate(from, to);
	}

	private Collection<String> generate(BigInteger from, BigInteger to) {
		IdGenerationRecursiveTask primes = new IdGenerationRecursiveTask(from, to);
		return pool.invoke(primes);
	}


}
