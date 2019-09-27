package com.gameofthree.configuration;

import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.mongodb.MongoClient;

@Configuration
public class AxonConfig {

	@Bean
	public EventStorageEngine storageEngine(MongoClient client) {
		return MongoEventStorageEngine.builder()
				.mongoTemplate(DefaultMongoTemplate.builder().mongoDatabase(client).build()).build();
	}

}
