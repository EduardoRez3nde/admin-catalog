package com.project.catalog.admin.infrastructure;

import com.project.catalog.admin.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
@Import(WebServerConfig.class)
public class Main {
    public static void main(String[] args) {
        //System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "dev");
        SpringApplication.run(Main.class, args);
    }
}