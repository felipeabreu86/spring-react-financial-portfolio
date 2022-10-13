package com.financialportfolio.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = { 
        "API_JWT_SECRET = API_JWT_SECRET", 
        "EMAIL_PASSWORD = EMAIL_PASSWORD",
        "EMAIL_FROM = EMAIL_FROM" })
class BackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
