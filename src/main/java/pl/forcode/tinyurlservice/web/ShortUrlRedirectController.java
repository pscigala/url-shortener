package pl.forcode.tinyurlservice.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.forcode.tinyurlservice.shortener.ShortenerService;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ShortUrlRedirectController {

	private final ShortenerService shortenerService;

	@GetMapping("/{shortUrlId}")
	void redirect(@PathParam("shortUrlId") String shortUrlId, HttpServletResponse httpServletResponse) {
		//todo
	}

}
