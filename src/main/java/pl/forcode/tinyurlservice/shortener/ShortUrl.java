package pl.forcode.tinyurlservice.shortener;

import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash("ShortUrl")
public record ShortUrl(String id, String originalUrl, LocalDateTime created) {

}
