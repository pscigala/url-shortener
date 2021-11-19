package pl.forcode.tinyurlservice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import pl.forcode.tinyurlservice.redis.TestRedisConfiguration;
import pl.forcode.tinyurlservice.web.dto.CreateShortUrlDTO;
import pl.forcode.tinyurlservice.web.dto.CreateShortUrlResultDTO;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ExtendWith(TestRedisConfiguration.class)
public class RESTApiTests {

	private static final String LOCALHOST_URL = "http://localhost";

	@LocalServerPort
	private Integer port;

	@BeforeEach
	public void setup() {
		RestAssured.port = port;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	@Test
	void shouldPostNewUrlToShorten() {
		String originalUrl = getServerHealthCheckUrl();
		CreateShortUrlResultDTO result = given().body(new CreateShortUrlDTO(originalUrl))
				.contentType(ContentType.JSON)
				.post("/api/url")
				.then()
				.statusCode(HttpStatus.OK.value())
				.extract()
				.as(CreateShortUrlResultDTO.class);

		assertThat(result)
				.isNotNull();
		assertThat(result)
				.extracting(CreateShortUrlResultDTO::shortUrl)
				.isNotNull();
		assertThat(result)
				.extracting(CreateShortUrlResultDTO::destination)
				.isEqualTo(originalUrl);
	}


	@Test
	void shouldRedirectToOriginalUrl() {
		String originalUrl = getServerHealthCheckUrl();
		CreateShortUrlResultDTO result = given()
				.body(new CreateShortUrlDTO(originalUrl))
				.contentType(ContentType.JSON)
				.post("/api/url")
				.then()
				.statusCode(HttpStatus.OK.value())
				.extract()
				.as(CreateShortUrlResultDTO.class);

		String shortUrl = result.shortUrl();

		given()
				.redirects()
				.follow(false)
				.get(getServerUrl() + shortUrl)
				.then()
				.statusCode(HttpStatus.MOVED_PERMANENTLY.value())
				.header(HttpHeaders.LOCATION, originalUrl);
	}

	private String getServerHealthCheckUrl() {
		return getServerUrl() + "/actuator/health";
	}

	private String getServerUrl() {
		return LOCALHOST_URL + ":" + port;
	}

}
