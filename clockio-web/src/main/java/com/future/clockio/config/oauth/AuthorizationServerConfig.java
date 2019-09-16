package com.future.clockio.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.InMemoryApprovalStore;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

//http://sivatechlab.com/secure-rest-api-using-spring-security-oauth2-part-3/

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
  private static String REALM = "CLOCK_REALM";
  private static final int ONE_DAY = 60 * 60 * 24;
  private static final int THIRTY_DAYS = 60 * 60 * 24 * 30;

  @Autowired
  private TokenStore tokenStore;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  @Qualifier("userApprovalHandler")
  private UserApprovalHandler userApprovalHandler;

  @Autowired
  @Qualifier("authenticationManagerBean")
  private AuthenticationManager authenticationManager;

  private static final String clientSecret = "SuperSecret";

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
            .withClient("client1")
            .secret(passwordEncoder.encode(clientSecret))
            .authorizedGrantTypes("password", "refresh_token")
            .authorities("ROLE_ADMIN", "ROLE_USER")
            .scopes("read", "write", "trust")
            .accessTokenValiditySeconds(300)
            .refreshTokenValiditySeconds(ONE_DAY);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//    ApprovalStoreUserApprovalHandler userApprovalHandler = new ApprovalStoreUserApprovalHandler();
//    userApprovalHandler.setApprovalStore(new InMemoryApprovalStore());
    endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
            .authenticationManager(authenticationManager);
//    endpoints.tokenStore(tokenStore)
//            .authenticationManager(authenticationManager);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    oauthServer.realm(REALM);
//    https://www.baeldung.com/rest-api-spring-oauth2-angular
//    oauthServer.tokenKeyAccess("permitAll()")
//            .checkTokenAccess("isAuthenticated()");
  }
}
