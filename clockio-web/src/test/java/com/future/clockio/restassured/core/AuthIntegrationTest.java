package com.future.clockio.restassured.core;

import com.future.clockio.ClockioApplication;
import com.future.clockio.restassured.helper.UserConst;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ClockioApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class AuthIntegrationTest {
  private static final String CLIENT_ID = "client";
  private static final String CLIENT_SECRET = "SuperSecret";

  @LocalServerPort
  int port;

  @Before
  public void setUp() throws JSONException {
    RestAssured.port = port;
    RestAssured.baseURI = "http://localhost";
    requestToken();
  }

  public static String requestToken() throws JSONException {
//    https://www.javacodemonk.com/oauth2-protected-resources-in-restassured-testcases-7d85a51f
    log.info("Getting OAuth Token");
    Response response = given().auth().preemptive().basic(CLIENT_ID, CLIENT_SECRET)
            .formParam("grant_type", "password")
            .formParam("username", "bebek")
            .formParam("password", "bebek")
            .when()
            .post("/oauth/token");
    JSONObject jsonObject = new JSONObject(response.getBody().asString());
    UserConst.accessToken =  jsonObject.getString("access_token");
    UserConst.refreshToken = jsonObject.getString("refresh_token");
    UserConst.tokenType = jsonObject.getString("token_type");
    log.info("Oauth Token accessToken: {} tokenType: {}", UserConst.accessToken, UserConst.tokenType);
    return UserConst.accessToken;
  }

  @Test
  public void userProfile() {
    given().header("Authorization", "Bearer " + UserConst.accessToken)
            .get("/api/profile")
            .then().assertThat()
            .statusCode(HttpStatus.OK.value())
            .body("username", equalTo("bebek"));
  }
}
