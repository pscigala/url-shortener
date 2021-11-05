package pl.forcode.tinyurlservice.shortener;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ShortenUrlResult {

	private final String shortUrl;
	private final String originalUrl;
	private final LocalDateTime created;//todo
	private final LocalDateTime expired;//todo

}
