package com.future.clockio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableMongoAuditing(auditorAwareRef = "auditorAware")
public class ClockioApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClockioApplication.class, args);
  }

}
