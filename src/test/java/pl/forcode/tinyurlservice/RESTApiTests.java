package pl.forcode.tinyurlservice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import pl.forcode.tinyurlservice.dto.CreateShortUrlDTO;
import pl.forcode.tinyurlservice.dto.CreateShortUrlResultDTO;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class RESTApiTests {

	@LocalServerPort
	private Integer port;


	@BeforeEach
	public void setup() {
		RestAssured.port = port;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	@Test
	void shouldPostNewUrlToShorten() {
		CreateShortUrlResultDTO resultDTO = given().body(new CreateShortUrlDTO("http://localhost"))
				.contentType(ContentType.JSON)
				.post("/url")
				.then()
				.statusCode(HttpStatus.OK.value())
				.extract().as(CreateShortUrlResultDTO.class);

		assertThat(resultDTO).isNotNull();
		assertThat(resultDTO).extracting(CreateShortUrlResultDTO::getShortUrl).isEqualTo("/0000000");
		assertThat(resultDTO).extracting(CreateShortUrlResultDTO::getDestination)
				.isEqualTo("http://localhost");

	}

}
