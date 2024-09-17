package io.netstacker.latte;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.netstacker.latte.api.controller.AccountController;

@SpringBootTest
class LatteApplicationTests {
    @Autowired
	private AccountController accountController;

	@Test
	void contextLoads() throws Exception {
		assertNotEquals(null, accountController);
	}
}
