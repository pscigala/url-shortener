package pl.forcode.tinyurlservice.shortener;

import org.springframework.stereotype.Component;
import pl.forcode.tinyurlservice.idGenerator.IdPoolGenerator;
import pl.forcode.tinyurlservice.idGenerator.IdsPool;
import pl.forcode.tinyurlservice.idPool.PoolNumberService;

import java.util.concurrent.ConcurrentLinkedQueue;

import static java.util.stream.Collectors.toCollection;

@Component
class PreGeneratedIdRepository implements IdRepository {

	private ConcurrentLinkedQueue<ShortUrlId> idQueue;

	private final IdPoolGenerator idPoolGenerator;
	private final PoolNumberService idPoolNumberService;

	public PreGeneratedIdRepository(IdPoolGenerator idPoolGenerator, PoolNumberService idPoolNumberService) {
		this.idPoolGenerator = idPoolGenerator;
		this.idPoolNumberService = idPoolNumberService;
		generateNextIdPool();
	}

	@Override
	public ShortUrlId getNewId() {
		if (idQueue.isEmpty()) {
			generateNextIdPool();
		}
		return idQueue.poll();
	}

	private void generateNextIdPool() {
		IdsPool idsPools = idPoolGenerator.generateNextPool(idPoolNumberService.getNextPoolNumber());
		idQueue = initIdQueue(idsPools);
	}

	private ConcurrentLinkedQueue<ShortUrlId> initIdQueue(IdsPool idsPools) {
		return idsPools.ids().stream()
				.map(ShortUrlId::new)
				.collect(toCollection(ConcurrentLinkedQueue::new));
	}
}
