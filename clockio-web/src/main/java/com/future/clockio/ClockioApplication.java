package com.future.clockio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableMongoRepositories
//@EnableMongoAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableFeignClients
public class ClockioApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClockioApplication.class, args);
  }

}
