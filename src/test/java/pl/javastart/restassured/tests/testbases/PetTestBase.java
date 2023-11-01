package pl.javastart.restassured.tests.testbases;

import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import pl.javastart.restassured.main.pojo.ApiResponse;
import pl.javastart.restassured.main.rop.pet.DeletePetEndpoint;

import java.util.Optional;

public class PetTestBase extends SuiteTestBase {

    protected Integer petIdToDelete;

    @AfterMethod
    public void cleanUpAfterTest() {
        Optional.ofNullable(petIdToDelete).ifPresent(pet -> {
            ApiResponse apiResponse = new DeletePetEndpoint().setPetId(petIdToDelete).sendRequest().assertRequestSuccess().getResponseModel();

            ApiResponse expectedApiResponse = new ApiResponse();
            expectedApiResponse.setCode(HttpStatus.SC_OK);
            expectedApiResponse.setType("unknown");
            expectedApiResponse.setMessage(petIdToDelete.toString());

            Assertions.assertThat(apiResponse).describedAs("API Response from system was not as expected").usingRecursiveComparison().isEqualTo(expectedApiResponse);
        });
     }
}
