package pl.javastart.restassured.tests.pet;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.javastart.restassured.main.pojo.ApiResponse;
import pl.javastart.restassured.main.pojo.pet.Pet;
import pl.javastart.restassured.main.rop.pet.CreatePetEndpoint;
import pl.javastart.restassured.main.rop.pet.DeletePetEndpoint;
import pl.javastart.restassured.main.rop.pet.GetPetEndpoint;
import pl.javastart.restassured.main.rop.pet.UpdatePetNameStatusEndpoint;
import pl.javastart.restassured.main.test.data.PetTestDataGenerator;
import pl.javastart.restassured.main.test.data.pet.PetStatus;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

public class UpdatePetStatusTests extends SuiteTestBase {

    private Pet petBeforeUpdate;

    @BeforeMethod
    public void beforeTest() {
        Pet pet = new PetTestDataGenerator().generatePet();

        petBeforeUpdate = new CreatePetEndpoint().setPet(pet).sendRequest().assertRequestSuccess().getResponseModel();
    }

    @TmsLink("ID-6")
    @Severity(SeverityLevel.CRITICAL)
    @Description("The goal of this test is to update pet name or/and status")
    @Test(dataProvider = "petStatusAndName")
    public void givenPetWhenPetGetsUpdatedNameOrStatusThenPetIsUpdatedTest(PetStatus petStatus) {
        new UpdatePetNameStatusEndpoint().setPetId(petBeforeUpdate.getId()).setStatus(petStatus.getStatus()).setName(petBeforeUpdate.getName()).sendRequest().assertRequestSuccess();

        Pet petAfterUpdate = new GetPetEndpoint().setPetId(petBeforeUpdate.getId()).sendRequest().assertRequestSuccess().getResponseModel();

        Assertions.assertThat(petAfterUpdate.getStatus()).describedAs("Pet Status").isEqualTo(petStatus.getStatus());
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

    @DataProvider
    public Object[][] petStatusAndName() {
        return new Object[][]
                {
                        {PetStatus.AVAILABLE},
                        {PetStatus.PENDING},
                        {PetStatus.SOLD}
                };
    }

}