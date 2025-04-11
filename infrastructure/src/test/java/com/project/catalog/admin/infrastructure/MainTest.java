package com.project.catalog.admin.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class MainTest {

    @Test
    void createMainTest() {
        System.out.println("Hello, World!");
    }
}
