package pl.forcode.tinyurlservice.config;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestContainersConfig implements BeforeAllCallback, DisposableBean {

	private static final String REDIS_IMAGE = "redis:6.2.6";
	private static final int REDIS_DEFAULT_PORT = 6379;

	private static final String ZOO_IMAGE = "zookeeper:3.7.0";
	private static final int ZOO_DEFAULT_PORT = 2181;

	private static final GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse(REDIS_IMAGE))
			.withExposedPorts(REDIS_DEFAULT_PORT);

	private static final GenericContainer<?> zookeeperContainer = new GenericContainer<>(DockerImageName.parse(ZOO_IMAGE))
			.withExposedPorts(ZOO_DEFAULT_PORT);

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		redisContainer.start();
		zookeeperContainer.start();
		overrideProperties();
	}

	@Override
	public void destroy() throws Exception {
		zookeeperContainer.stop();
		redisContainer.stop();
	}

	private void overrideProperties() {
		System.setProperty("spring.redis.host", redisContainer.getHost());
		System.setProperty("spring.redis.port", String.valueOf(redisContainer.getMappedPort(REDIS_DEFAULT_PORT)));

		var connectString = zookeeperContainer.getHost() + ":" + zookeeperContainer.getMappedPort(ZOO_DEFAULT_PORT);
		System.setProperty("spring.cloud.zookeeper.connect-string", connectString);
	}
}
