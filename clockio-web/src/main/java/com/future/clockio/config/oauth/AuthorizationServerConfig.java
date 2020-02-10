package com.future.clockio.config.oauth;

import com.future.clockio.service.helper.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

//http://sivatechlab.com/secure-rest-api-using-spring-security-oauth2-part-3/

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
  private static final String clientId = "client";
  private static final String clientSecret = "SuperSecret";
  private static final int ONE_HOUR = 60 * 60;
  private static final int ONE_DAY = ONE_HOUR * 24;
  private static final int ONE_WEEK = ONE_DAY * 7;
  private static final int THIRTY_DAYS = ONE_DAY * 30;

  @Autowired
  private TokenStore tokenStore;

  @Autowired
  private JwtAccessTokenConverter tokenEnhancer;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserApprovalHandler userApprovalHandler;

  @Autowired
  @Qualifier("authenticationManagerBean")
  private AuthenticationManager authenticationManager;

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
            .withClient(clientId)
            .secret(passwordEncoder.encode(clientSecret))
            .authorizedGrantTypes("password", "refresh_token")
            .authorities("ROLE_ADMIN", "ROLE_USER")
            .scopes("read", "write", "trust")
            .accessTokenValiditySeconds(ONE_WEEK)
            .refreshTokenValiditySeconds(THIRTY_DAYS);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
            .tokenStore(tokenStore)
            .tokenEnhancer(tokenEnhancer)
            .userApprovalHandler(userApprovalHandler)
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
//    https://www.baeldung.com/rest-api-spring-oauth2-angular
    oauthServer.tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()");
  }
}
