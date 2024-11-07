package web.app.api.test;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.hasSize;

public class APITest {
    private static final int TOTAL_USERS = 10;
    private ValidatableResponse response;

    private final Properties properties = new Properties();

    @BeforeMethod(alwaysRun = true)
    public void getMethodExecute() {
        try (InputStream input = APITest.class.getClassLoader().getResourceAsStream("endpoint.properties")) {
            properties.load(input);
            //create request and use .prettyPrint() to log  request
            response = given().when().get(properties.getProperty("endpoint")).then();
            response.log();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void getUsersStatusCodeTest() {
        response.assertThat().
                statusCode(SC_OK);
    }

    @Test
    public void getUsersHeaderTest() {
        response.assertThat().
                header(CONTENT_TYPE, "application/json; charset=utf-8");
    }

    @Test
    public void getUsersBodyTest() {
        response.assertThat().
                body("users", hasSize(TOTAL_USERS));
    }
}
