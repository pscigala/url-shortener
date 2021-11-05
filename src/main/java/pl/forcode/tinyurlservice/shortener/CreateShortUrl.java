package pl.forcode.tinyurlservice.shortener;

import lombok.Value;

@Value
public class CreateShortUrl {

	private final String originalUrl;

}
