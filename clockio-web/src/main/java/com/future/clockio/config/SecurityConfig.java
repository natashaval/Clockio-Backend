package com.future.clockio.config;

import com.future.clockio.config.filter.CorsFilter;
import com.future.clockio.service.helper.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

//http://sivatechlab.com/secure-rest-api-using-spring-security-oauth2-part-3/

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  CustomUserDetailsService userDetailsService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  CorsFilter corsFilter;

//  @Autowired
//  public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//    auth.inMemoryAuthentication()
//            .withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN", "USER").and()
//            .withUser("user").password(passwordEncoder.encode("user")).roles("USER");
//  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(
                    "/auth/**", "/oauth2/**",
                    "/oauth/token",
                    "/about", "/test",
                    "/firebase/**",
                    "/swagger-ui.html",
                    "/v2/api-docs",
                    "/docs",
                    "/swagger-resources/**",
                    "/webjars/**")
            .permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(corsFilter, ChannelProcessingFilter.class);
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
