package de.held.authenticationdemo;

import java.net.URI;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthenticationDemoApplicationTests {

	@Autowired
	private TestRestTemplate testClient;

	@Test
	public void testAuthentication_noHeaderSet() {
		ResponseEntity<String> response = testClient.getForEntity("/secure", String.class);
		Assertions.assertThat(response.getStatusCode().value()).isEqualTo(403);
	}

	@Test
	public void testAuthentication_validHeadersSet() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-user", "Marcus Held");
		headers.add("x-password", "SuperSecret");
		RequestEntity requestEntity = new RequestEntity(headers, HttpMethod.GET, URI.create("/secure"));

		ResponseEntity<String> response = testClient.exchange(requestEntity, String.class);

		Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
	}

	@Test
	public void testAuthentication_invalidHeadersSet() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-user", "Peter Parker");
		headers.add("x-password", "HasNoAccount");
		RequestEntity requestEntity = new RequestEntity(headers, HttpMethod.GET, URI.create("/secure"));

		ResponseEntity<String> response = testClient.exchange(requestEntity, String.class);

		Assertions.assertThat(response.getStatusCode().value()).isEqualTo(403);
	}

}
