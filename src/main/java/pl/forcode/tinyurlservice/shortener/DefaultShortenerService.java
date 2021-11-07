package pl.forcode.tinyurlservice.shortener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DefaultShortenerService implements ShortenerService {

	private final IdRepository idRepository;

	@Override
	public ShortenUrlResult shortUrl(CreateShortUrl input) {
		ShortUrlId id = idRepository.getNewId();

		ShortenUrlResult result = new ShortenUrlResult("/" + id.getId(), input.getOriginalUrl(), LocalDateTime.now(), null);

		return result;
	}
}
