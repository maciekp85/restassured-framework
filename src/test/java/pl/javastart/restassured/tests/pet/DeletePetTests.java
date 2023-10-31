package pl.javastart.restassured.tests.pet;

import com.github.javafaker.Faker;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.javastart.restassured.main.rop.DeletePetEndpoint;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

public class DeletePetTests extends SuiteTestBase {

    private int nonExistingPetId;

    @BeforeMethod
    public void beforeTest() {
        nonExistingPetId = new Faker().number().numberBetween(1000, 10000);
        new DeletePetEndpoint().setPetId(nonExistingPetId).sendRequest();
    }

    @Test
    public void givenNonExistingPetWhenDeletingPetThenPetNotFoundTest() {
        int nonExistingPetId = new Faker().number().numberBetween(1000, 10000);
        new DeletePetEndpoint().setPetId(nonExistingPetId).sendRequest().assertStatusCode(HttpStatus.SC_NOT_FOUND);
    }

}
