package com.divya.apps.comments.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

@Configuration
//@EnableMongoRepositories(basePackages = { "com.divya" })
public class MongoConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(new OffsetDateTimeToDocumentConverter(), new DocumentToOffsetDateTimeConverter()));
    }

}