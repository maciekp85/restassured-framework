package pl.javastart.restassured.tests.pet;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.javastart.restassured.main.pojo.ApiResponse;
import pl.javastart.restassured.main.pojo.pet.Pet;
import pl.javastart.restassured.main.rop.pet.CreatePetEndpoint;
import pl.javastart.restassured.main.rop.pet.DeletePetEndpoint;
import pl.javastart.restassured.main.rop.pet.GetPetEndpoint;
import pl.javastart.restassured.main.rop.pet.UpdatePetEndpoint;
import pl.javastart.restassured.main.test.data.PetTestDataGenerator;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

public class UpdatePetTests extends SuiteTestBase {

    private Pet petBeforeUpdate;

    @BeforeMethod
    public void beforeTest() {
        Pet pet = new PetTestDataGenerator().generatePet();

        petBeforeUpdate = new CreatePetEndpoint().setPet(pet).sendRequest().assertRequestSuccess().getResponseModel();
    }

    @TmsLink("ID-5")
    @Severity(SeverityLevel.CRITICAL)
    @Description("The goal of this test is to update pet and check if it was updated")
    @Test
    public void givenPetWhenPetGetsUpdatedThenPetIsUpdatedTest() {
        Pet newPet = new PetTestDataGenerator().generatePet();
        newPet.setId(petBeforeUpdate.getId());

        Pet updatedPet = new UpdatePetEndpoint().setPet(newPet).sendRequest().assertRequestSuccess().getResponseModel();

        Assertions.assertThat(updatedPet).describedAs("Updated pet was the same as create pet").usingRecursiveComparison().isNotEqualTo(petBeforeUpdate);
        Assertions.assertThat(updatedPet).describedAs("Updated pet was not the as same as update").usingRecursiveComparison().isEqualTo(newPet);

        Pet petAfterUpdate = new GetPetEndpoint().setPetId(petBeforeUpdate.getId()).sendRequest().assertRequestSuccess().getResponseModel();

        Assertions.assertThat(petAfterUpdate).describedAs("Updated pet was the same as create pet").usingRecursiveComparison().isNotEqualTo(petBeforeUpdate);
        Assertions.assertThat(petAfterUpdate).describedAs("Updated pet was not the as same as update").usingRecursiveComparison().isEqualTo(newPet);
    }

    @AfterMethod
    public void cleanUpAfterTest() {
        ApiResponse apiResponse = new DeletePetEndpoint().setPetId(petBeforeUpdate.getId()).sendRequest().assertRequestSuccess().getResponseModel();
        ApiResponse expectedApiResponse = new ApiResponse();
        expectedApiResponse.setCode(HttpStatus.SC_OK);
        expectedApiResponse.setType("unknown");
        expectedApiResponse.setMessage(petBeforeUpdate.getId().toString());

        Assertions.assertThat(apiResponse).describedAs("API Response from system was not as expected").usingRecursiveComparison().isEqualTo(expectedApiResponse);
    }

}
