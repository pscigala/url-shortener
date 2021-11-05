package pl.forcode.tinyurlservice.dto;

import lombok.Value;

@Value
public class CreateShortUrlResultDTO {

	private final String shortUrl;
	private final String destination;

}
