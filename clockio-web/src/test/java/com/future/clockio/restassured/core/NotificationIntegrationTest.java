package com.future.clockio.restassured.core;

import com.future.clockio.ClockioApplication;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static io.restassured.RestAssured.get;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ClockioApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotificationIntegrationTest {
  @LocalServerPort
  private int port;

  @Before
  public void setUp() {
    RestAssured.port = port;
  }

  @Test
  @Ignore
  public void hello() {
    get("/about").then()
            .assertThat()
            .statusCode(200);
  }
}
