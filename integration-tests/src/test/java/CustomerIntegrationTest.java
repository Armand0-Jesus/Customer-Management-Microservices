import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CustomerIntegrationTest {

  private static final String BASE_URI = System.getProperty("gateway.base-uri",
      System.getenv().getOrDefault("GATEWAY_BASE_URI", "http://localhost:4004"));

  @BeforeAll
  static void setUp(){
    RestAssured.baseURI = BASE_URI;
  }

  @Test
  public void shouldReturnCustomersWithValidToken () {
    String loginPayload = """
          {
            "email": "testuser@test.com",
            "password": "password123"
          }
        """;

    String token = given()
        .contentType("application/json")
        .body(loginPayload)
        .when()
        .post("/auth/login")
        .then()
        .statusCode(200)
        .extract()
        .jsonPath()
        .get("token");

    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/api/customers")
        .then()
        .statusCode(200)
        .body("size()", greaterThan(0));
  }
}
