package pl.javastart.restassured.tests.user;

import com.github.javafaker.Faker;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.javastart.restassured.main.rop.DeleteUserEndpoint;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

public class DeleteUserTests extends SuiteTestBase {

    private String nonExistingUsername;

    @BeforeMethod
    public void beforeTest() {
        nonExistingUsername = new Faker().name().username();
        new DeleteUserEndpoint().setUsername(nonExistingUsername).sendRequest();
    }

    @Test
    public void givenNonExistingUserWhenDeletingUserThenUserNotFoundTest() {
        new DeleteUserEndpoint().setUsername(nonExistingUsername).sendRequest().assertStatusCode(HttpStatus.SC_NOT_FOUND);
    }
}
