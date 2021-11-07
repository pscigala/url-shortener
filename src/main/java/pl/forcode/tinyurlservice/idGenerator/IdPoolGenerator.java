package pl.forcode.tinyurlservice.idGenerator;

import java.util.List;

public interface IdPoolGenerator {

	IdsPool generateNextPool(long lastPoolNumber);

	List<IdsPool> generateNextPools(long lastPoolNumber);

	List<IdsPool> generateNextPools(long lastPoolNumber, int poolsAmount);
}
