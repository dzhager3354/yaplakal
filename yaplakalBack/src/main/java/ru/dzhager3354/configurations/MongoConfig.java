package ru.dzhager3354.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {
    @Bean("databaseName")
    public String connection() {
        return "mongo";
    }
}
