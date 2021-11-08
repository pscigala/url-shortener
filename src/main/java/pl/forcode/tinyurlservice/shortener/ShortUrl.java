package pl.forcode.tinyurlservice.shortener;

import lombok.Value;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Value
@RedisHash("ShortUrl")
public class ShortUrl {

	private final String id;
	private final String originalUrl;
	private final LocalDateTime created;

}
