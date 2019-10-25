package com.future.clockio.config.oauth;

import com.future.clockio.service.helper.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

// http://sivatechlab.com/secure-rest-api-using-spring-security-oauth2-part-3/
//https://www.tutorialspoint.com/spring_boot/spring_boot_oauth2_with_jwt.htm
@Configuration
public class OAuthTokenConfig {
  private String privateKey = "private-key";
  private String publicKey = "public-key";
  private static final String SIGN_KEY = "my-secret-key";

  @Autowired
  private ClientDetailsService clientDetailsService;

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(tokenEnhancer());
  }

  @Bean
  public JwtAccessTokenConverter tokenEnhancer() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey(SIGN_KEY);
    ((DefaultAccessTokenConverter) converter.getAccessTokenConverter())
            .setUserTokenConverter(userAuthenticationConverter());
    return converter;
  }

//  https://stackoverflow.com/questions/54279755/getprincipal-method-returning-username-instead-of-userdetails
  @Bean
  public UserAuthenticationConverter userAuthenticationConverter() {
    DefaultUserAuthenticationConverter defaultUserAuthenticationConverter = new DefaultUserAuthenticationConverter();
    defaultUserAuthenticationConverter.setUserDetailsService(userDetailsService);
    return defaultUserAuthenticationConverter;
  }


  @Bean
  @Autowired
  public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore token) {
    TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
    handler.setTokenStore(token);
    handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
    handler.setClientDetailsService(clientDetailsService);
    return handler;
  }

  @Bean
  @Autowired
  public ApprovalStore approvalStore(TokenStore token) throws Exception {
    TokenApprovalStore store = new TokenApprovalStore();
    store.setTokenStore(token);
    return store;
  }
}
