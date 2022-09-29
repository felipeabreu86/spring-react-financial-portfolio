package com.financialportfolio.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"API_JWT_SECRET = api_jwt_secret"})
class BackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
