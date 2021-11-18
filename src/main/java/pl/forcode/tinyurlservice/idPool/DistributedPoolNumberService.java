package pl.forcode.tinyurlservice.idPool;

import lombok.extern.log4j.Log4j2;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.framework.recipes.shared.SharedCountListener;
import org.apache.curator.framework.recipes.shared.SharedCountReader;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.stereotype.Service;
import pl.forcode.tinyurlservice.idPool.exception.PoolNumberGetException;

@Service
@Log4j2
class DistributedPoolNumberService implements PoolNumberService, SharedCountListener {

	private static final String POOL_COUNTER_N_NAME = "/UrlShortener/nextPoolNumber";

	private final DistributedAtomicLong counter;

	public DistributedPoolNumberService(CuratorFramework curatorFramework) {
		this.counter = initDistributedCounter(curatorFramework);
	}

	private DistributedAtomicLong initDistributedCounter(CuratorFramework curatorFramework) {
		return new DistributedAtomicLong(curatorFramework, POOL_COUNTER_N_NAME,
				new RetryNTimes(10, 10));
	}

	@Override
	public long getNextPoolNumber() {
		try {
			AtomicValue<Long> value = counter.increment();
			if (value.succeeded()) {
				Long poolNumber = value.preValue();
				log.info("Retrieved nextPoolNumber: {}", poolNumber);
				return poolNumber;
			}
		} catch (Exception e) {
			throw new PoolNumberGetException(e);
		}
		throw new PoolNumberGetException();
	}

	@Override
	public void countHasChanged(SharedCountReader sharedCountReader, int i) throws Exception {
		log.info("nextPoolNumber changed {}", i);
	}

	@Override
	public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
	}
}
