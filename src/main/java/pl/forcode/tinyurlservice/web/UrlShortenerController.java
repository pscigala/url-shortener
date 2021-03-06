package pl.forcode.tinyurlservice.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.forcode.tinyurlservice.shortener.CreateShortUrl;
import pl.forcode.tinyurlservice.shortener.ShortUrl;
import pl.forcode.tinyurlservice.shortener.ShortenerService;
import pl.forcode.tinyurlservice.web.dto.CreateShortUrlDTO;
import pl.forcode.tinyurlservice.web.dto.CreateShortUrlResultDTO;

@RestController
@RequestMapping("/api/url")
@RequiredArgsConstructor
public class UrlShortenerController {

	private final ShortenerService shortenerService;

	@PostMapping
	CreateShortUrlResultDTO createShortUrl(@RequestBody CreateShortUrlDTO dto) {
		ShortUrl result = shortenerService.shortUrl(new CreateShortUrl(dto.url()));
		return new CreateShortUrlResultDTO("/" + result.id(), result.originalUrl());
	}

}
