package pl.forcode.tinyurlservice.idGenerator;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

class IdGenerationRecursiveTask extends RecursiveTask<Collection<String>> {

	private static final int THRESHOLD = 2000_00;

	private final BigInteger from;
	private final BigInteger to;

	IdGenerationRecursiveTask(BigInteger from, BigInteger to) {
		this.from = from;
		this.to = to;
	}

	@Override
	protected Collection<String> compute() {
		if ((to.subtract(from).intValue()) <= THRESHOLD) {
			return generateId();
		}

		BigInteger middleIndex = calculateMiddleIndex();
		IdGenerationRecursiveTask left = new IdGenerationRecursiveTask(from, middleIndex);
		IdGenerationRecursiveTask right = new IdGenerationRecursiveTask(middleIndex, to);

		left.fork();

		Collection<String> rightResult = right.compute();
		Collection<String> leftResult = left.join();

		rightResult.addAll(leftResult);
		return rightResult;
	}

	private BigInteger calculateMiddleIndex() {
		return from.add((to.subtract(from).divide(new BigInteger("2"))));
	}

	private Set<String> generateId() {
		return Base62IdGenerator.range(new BigInteger(String.valueOf(from)), new BigInteger(String.valueOf(to)));
	}


}
