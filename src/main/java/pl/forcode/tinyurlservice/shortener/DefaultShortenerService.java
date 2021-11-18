package pl.forcode.tinyurlservice.shortener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class DefaultShortenerService implements ShortenerService {

	private final IdRepository idRepository;

	private final ShortUrlRepository shortUrlRepository;

	@Override
	public ShortUrl shortUrl(CreateShortUrl input) {
		ShortUrlId id = idRepository.getNewId();
		ShortUrl shortUrl = new ShortUrl(id.id(), input.url(), LocalDateTime.now());
		return shortUrlRepository.save(shortUrl);
	}

	@Override
	public ShortUrl findShortUrl(String id) {
		return shortUrlRepository.findById(id)
				.orElseThrow();
	}
}
