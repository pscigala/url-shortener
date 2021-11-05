package pl.forcode.tinyurlservice.shortener;

import org.springframework.stereotype.Component;
import pl.forcode.tinyurlservice.shortener.exception.EmptyIdStorageException;

import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class PreGeneratedIdRepository implements IdRepository {

	private ConcurrentLinkedQueue<ShortUrlId> idStorage = new ConcurrentLinkedQueue<>();

	public PreGeneratedIdRepository() {
		for (int i = 0; i < 100; i++) {
			String s = i + "" + i + "" + i + "" + i + "" + i + "" + i + "" + i;//todo
			idStorage.add(new ShortUrlId(String.valueOf(s)));
		}
	}

	@Override
	public ShortUrlId getNewId() {
		if (idStorage.isEmpty()) {
			throw new EmptyIdStorageException();
		}
		return idStorage.poll();
	}
}
