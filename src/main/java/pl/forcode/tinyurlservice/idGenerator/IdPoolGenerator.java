package pl.forcode.tinyurlservice.idGenerator;

import java.util.List;

public interface IdPoolGenerator {

	List<IdsPool> nextIdPools(int lastPoolNumber);

	List<IdsPool> nextIdPools(int lastPoolNumber, int poolsAmount);
}
