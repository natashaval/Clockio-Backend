package com.future.clockio.client.config;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FaceConfiguration {

  @Value("${feign.azure.cognitive.key}")
  private String key;

  @Bean // applicable to Feign
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      requestTemplate.header("Ocp-Apim-Subscription-Key", key);
    };
  }

  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.BASIC;
  }

  @Bean
  public FeignErrorDecoder errorDecoder() {
    return new FeignErrorDecoder();
  }
}
