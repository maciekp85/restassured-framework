package pl.javastart.restassured.tests.pet;

import com.github.javafaker.Faker;
import io.qameta.allure.*;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.javastart.restassured.main.rop.pet.DeletePetEndpoint;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

public class DeletePetTests extends SuiteTestBase {

    private int nonExistingPetId;

    @BeforeMethod
    public void beforeTest() {
        nonExistingPetId = new Faker().number().numberBetween(1000, 10000);
        new DeletePetEndpoint().setPetId(nonExistingPetId).sendRequest();
    }

    @Issue("DEFECT-2")
    @TmsLink("ID-2")
    @Severity(SeverityLevel.CRITICAL)
    @Description("The goal of this test is to fail to delete non existing pet")
    @Test
    public void givenNonExistingPetWhenDeletingPetThenPetNotFoundTest() {
        int nonExistingPetId = new Faker().number().numberBetween(1000, 10000);
        new DeletePetEndpoint().setPetId(nonExistingPetId).sendRequest().assertStatusCode(HttpStatus.SC_NOT_FOUND);
    }

}
