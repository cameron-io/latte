package io.netstacker.latte;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.netstacker.latte.api.dtos.account.RegisterDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HttpRequestTests {

	@LocalServerPort
	private int port;
    private String baseUri = "http://localhost:" + port + "/";

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void registerSuccessTest() throws Exception {
        var payload = new RegisterDto();
        payload.setName("AdminTester");
        payload.setEmail("admin@mail.com");
        payload.setPassword("Pa$$w0rd!");
        this.restTemplate.postForEntity(
            baseUri,
            payload,
            null);
	}
}