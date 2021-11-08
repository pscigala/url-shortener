package pl.forcode.tinyurlservice.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.forcode.tinyurlservice.shortener.ShortUrl;
import pl.forcode.tinyurlservice.shortener.ShortenerService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ShortUrlRedirectController {

	private final ShortenerService shortenerService;

	@GetMapping("/{shortUrlId}")
	void redirect(@PathVariable("shortUrlId") String shortUrlId, HttpServletResponse httpServletResponse) {
		ShortUrl shortUrl = shortenerService.findShortUrl(shortUrlId);
		httpServletResponse.setHeader(HttpHeaders.LOCATION, shortUrl.getOriginalUrl());
		httpServletResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
	}

}
