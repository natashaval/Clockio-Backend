package com.future.clockio.restassured.core;

import com.future.clockio.ClockioApplication;
import com.future.clockio.entity.core.User;
import com.future.clockio.request.core.UserRequest;
import com.future.clockio.restassured.helper.UserConst;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static com.future.clockio.controller.helper.EntityMock.USER_PASSWORD;
import static com.future.clockio.controller.helper.EntityMock.USER_USERNAME;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ClockioApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class UserIntegrationTest {

  @LocalServerPort
  int port;

  private static final String USER_API = "/api/user";

  @Before
  public void setUp() throws JSONException {
    RestAssured.port = port;
    AuthIntegrationTest.requestToken();
    log.info("isAccess Token {}, refreshToken {}", UserConst.accessToken, UserConst.refreshToken);
  }

  @Test
  @Ignore
  public void createUser() {
    UserRequest userRequest = new UserRequest(USER_USERNAME, USER_PASSWORD, 1);
    given().header(UserConst.headerAuth, "Bearer " + UserConst.accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .body(userRequest)
            .when().post(USER_API)
            .then().log().all()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .body("username", equalTo(USER_USERNAME));
  }
}
