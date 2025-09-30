package com.example.apigatewayservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("h2")
@SpringBootTest(properties = "spring.config.location=classpath:/application-h2.yml")
class ApiGatewayServiceApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void mainMethodRuns() {
        ApiGatewayServiceApplication.main(new String[]{});
    }

}
