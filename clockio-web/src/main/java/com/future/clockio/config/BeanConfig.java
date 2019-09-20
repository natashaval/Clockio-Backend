package com.future.clockio.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {

  @Value("${cloudinary.cloud.name}")
  private String cloudinaryName;

  @Value("${cloudinary.cloud.api.key}")
  private String cloudinaryApiKey;

  @Value("${cloudinary.cloud.api.secret}")
  private String cloudinaryApiSecret;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public Cloudinary cloudinary() {
    return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", cloudinaryName,
            "api_key", cloudinaryApiKey,
            "api_secret", cloudinaryApiSecret
    ));
  }
}
