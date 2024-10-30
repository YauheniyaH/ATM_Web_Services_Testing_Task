package web.app.api.test;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

public class APITest {
    private static final String ENDPOINT = "https://jsonplaceholder.typicode.com/users";

    private ValidatableResponse response;

    @BeforeMethod(alwaysRun = true)
    public void getMethodExecute() {
        response = given().when().get(ENDPOINT).then();
    }

    @Test
    public void getUsersStatusCodeTest() {
        response.log().body();
        response.assertThat().
                statusCode(200);
    }

    @Test
    public void getUsersHeaderTest() {
        response.assertThat().
                header("Content-Type", "application/json; charset=utf-8");
    }

    @Test
    public void getUsersBodyTest() {
        response.assertThat().
                body("users", hasSize(10));
    }
}
