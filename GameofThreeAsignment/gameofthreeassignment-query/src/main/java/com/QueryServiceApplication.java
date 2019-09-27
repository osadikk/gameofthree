package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = { "com.gameofthree.infrastructure.repository" })
@EnableEurekaClient
public class QueryServiceApplication {
    public static void main(String[] args) {
	SpringApplication.run(QueryServiceApplication.class);
    }
}
