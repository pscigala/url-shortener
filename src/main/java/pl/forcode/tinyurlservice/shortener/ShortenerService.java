package pl.forcode.tinyurlservice.shortener;


public interface ShortenerService {

	ShortUrl shortUrl(CreateShortUrl input);

	ShortUrl findShortUrl(String id);
}
