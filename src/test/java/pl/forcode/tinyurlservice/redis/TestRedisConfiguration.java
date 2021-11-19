package pl.forcode.tinyurlservice.redis;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class TestRedisConfiguration implements BeforeAllCallback, AfterAllCallback {

	private static final String REDIS_IMAGE = "redis:6.2.6";
	private static final int REDIS_DEFAULT_PORT = 6379;

	private static final GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse(REDIS_IMAGE))
			.withExposedPorts(REDIS_DEFAULT_PORT);

	@Override
	public void beforeAll(ExtensionContext extensionContext) throws Exception {
		redis.start();
		System.setProperty("spring.redis.host", redis.getHost());
		System.setProperty("spring.redis.port", String.valueOf(redis.getMappedPort(6379)));
	}

	@Override
	public void afterAll(ExtensionContext extensionContext) throws Exception {
		redis.stop();
	}
}
