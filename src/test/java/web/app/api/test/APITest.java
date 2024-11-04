package web.app.api.test;

import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

public class APITest {

    private ValidatableResponse response;

    private final Properties properties = new Properties();

    @BeforeMethod(alwaysRun = true)
    public void getMethodExecute() {
        try (InputStream input = APITest.class.getClassLoader().getResourceAsStream("endpoint.properties")) {
            properties.load(input);
            response = given().when().get(properties.getProperty("endpoint")).then();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void getUsersStatusCodeTest() {
        response.log().status();
        response.assertThat().
                statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void getUsersHeaderTest() {
        response.log().headers();
        response.assertThat().
                header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
    }

    @Test
    public void getUsersBodyTest() {
        response.log().body();
        response.assertThat().
                body("users", hasSize(10));
    }
}
