package com.groupfour.MedicalCare;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = MedicalCareApplication.class
)

@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
class MedicalCareApplicationTests {

    @Test
    void contextLoads() {

    }

}
