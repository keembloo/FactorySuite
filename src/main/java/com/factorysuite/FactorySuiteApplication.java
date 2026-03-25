package com.factorysuite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FactorySuiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(FactorySuiteApplication.class, args);
    }
}