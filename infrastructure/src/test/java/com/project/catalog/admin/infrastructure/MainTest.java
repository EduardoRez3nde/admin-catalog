package com.project.catalog.admin.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    void createMainTest() {
        Assertions.assertNotNull(new Main());
        Main.main(new String[] {});
    }
}
