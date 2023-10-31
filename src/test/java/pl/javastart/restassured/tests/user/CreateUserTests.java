package pl.javastart.restassured.tests.user;

import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pl.javastart.restassured.main.pojo.ApiResponse;
import pl.javastart.restassured.main.pojo.user.User;
import pl.javastart.restassured.main.request.configuration.RequestConfigurationBuilder;
import pl.javastart.restassured.main.rop.CreateUserEndpoint;
import pl.javastart.restassured.main.test.data.UserTestDataGenerator;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

import static io.restassured.RestAssured.given;

public class CreateUserTests extends SuiteTestBase {

    private User user;

    @Test
    public void givenUserWhenPostUserThenUserIsCreatedTest() {
        UserTestDataGenerator userTestDataGenerator = new UserTestDataGenerator(); // Create generator
        user = userTestDataGenerator.generateUser(); // Generate User object

        ApiResponse apiResponse = new CreateUserEndpoint().setUser(user).sendRequest().assertRequestSuccess().getResponseModel();

        ApiResponse expectedApiResponse = new ApiResponse();
        expectedApiResponse.setCode(HttpStatus.SC_OK);
        expectedApiResponse.setType("unknown");
        expectedApiResponse.setMessage(user.getId().toString());

        Assertions.assertThat(apiResponse).describedAs("Created User was not created by API").usingRecursiveComparison().isEqualTo(expectedApiResponse);
    }

    @AfterMethod
    public void cleanUpAfterTest() {
        ApiResponse apiResponse = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .when().delete("user/{username}", user.getUsername())
                .then().statusCode(HttpStatus.SC_OK).extract().as(ApiResponse.class);

        ApiResponse expectedApiResponse = new ApiResponse();
        expectedApiResponse.setCode(HttpStatus.SC_OK);
        expectedApiResponse.setType("unknown");
        expectedApiResponse.setMessage(user.getUsername());

        Assertions.assertThat(apiResponse).describedAs("User was not deleted").usingRecursiveComparison().isEqualTo(expectedApiResponse);
    }
}
