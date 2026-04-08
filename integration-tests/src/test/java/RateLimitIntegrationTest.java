import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RateLimitIntegrationTest {

  private static final String BASE_URI = System.getProperty("gateway.base-uri",
      System.getenv().getOrDefault("GATEWAY_BASE_URI", "http://localhost:4004"));

  @BeforeAll
  static void setUp() {
    RestAssured.baseURI = BASE_URI;
  }

  @Test
  void shouldReturn429WhenAuthBurstCapacityIsExceeded() {
    String loginPayload = """
        {
          "email": "testuser@test.com",
          "password": "password123"
        }
        """;

    Response throttledResponse = null;

    for (int attempt = 0; attempt < 40; attempt++) {
      Response response = given()
          .contentType("application/json")
          .body(loginPayload)
          .when()
          .post("/auth/login")
          .andReturn();

      if (response.statusCode() == 429) {
        throttledResponse = response;
        break;
      }
    }

    assertNotNull(throttledResponse,
        "Expected the auth route to return 429 after repeated requests");

    throttledResponse.then()
        .statusCode(429)
        .contentType("application/json")
        .header("Retry-After", notNullValue())
        .body("status", equalTo(429))
        .body("error", equalTo("Too Many Requests"))
        .body("message", equalTo("You have exceeded the rate limit. Retry later."))
        .body("path", equalTo("/auth/login"));
  }
}
