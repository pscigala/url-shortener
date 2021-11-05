package pl.forcode.tinyurlservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.forcode.tinyurlservice.dto.CreateShortUrlDTO;
import pl.forcode.tinyurlservice.dto.CreateShortUrlResultDTO;
import pl.forcode.tinyurlservice.shortener.CreateShortUrl;
import pl.forcode.tinyurlservice.shortener.ShortenUrlResult;
import pl.forcode.tinyurlservice.shortener.ShortenerService;

@RestController
@RequestMapping("/url")
@RequiredArgsConstructor
public class UrlShortenerController {

	private final ShortenerService shortenerService;

	@PostMapping
	CreateShortUrlResultDTO createShortUrl(@RequestBody CreateShortUrlDTO dto) {
		//todo fasada?
		ShortenUrlResult result = shortenerService.shortUrl(new CreateShortUrl(dto.getUrl()));
		return new CreateShortUrlResultDTO(result.getShortUrl(), result.getOriginalUrl());
	}


}
