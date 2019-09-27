package com.gameofthreeassignmentweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.gameofthreeassignmentweb.util.exception.ResponseEntityErrorHandler;

@SpringBootApplication
@EnableEurekaClient
public class WebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntityErrorHandler errorHandler = new ResponseEntityErrorHandler();
		errorHandler.setMessageConverters(restTemplate.getMessageConverters());
		restTemplate.setErrorHandler(errorHandler);
		return restTemplate;
	}

}
