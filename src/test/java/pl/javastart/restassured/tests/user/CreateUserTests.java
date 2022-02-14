package pl.javastart.restassured.tests.user;

import org.testng.annotations.Test;
import pl.javastart.restassured.main.pojo.ApiResponse;
import pl.javastart.restassured.main.pojo.user.User;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class CreateUserTests extends SuiteTestBase {

    @Test
    public void givenUserWhenPostUserThenUserIsCreatedTest() {
        User user = new User();
        user.setId(445);
        user.setUsername("firstuser");
        user.setFirstName("Krzysztof");
        user.setLastName("Kowalski");
        user.setEmail("krzysztof@test.com");
        user.setPassword("password");
        user.setPhone("+123456789");
        user.setUserStatus(123);

        ApiResponse apiResponse = given().contentType("application/json")
                .body(user)
                .when().post("user")
                .then().statusCode(200).extract().as(ApiResponse.class);

        assertEquals(apiResponse.getCode(), Integer.valueOf(200), "Code is not as expected");
        assertEquals(apiResponse.getType(), "unknown", "Type is not as expected");
        assertEquals(apiResponse.getMessage(), "445", "Message is not as expected");

    }
}
