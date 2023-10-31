package pl.javastart.restassured.tests.pet;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pl.javastart.restassured.main.pojo.ApiResponse;
import pl.javastart.restassured.main.pojo.pet.Pet;
import pl.javastart.restassured.main.test.data.PetTestDataGenerator;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class CreatePetTests extends SuiteTestBase {

    private Pet actualPet;

    @Test
    public void givenPetWhenPostPetThenPetIsCreatedTest() {

        Pet pet = new PetTestDataGenerator().generatePet();

        actualPet = given().body(pet).contentType("application/json")
                .when().post("pet")
                .then().statusCode(200).extract().as(Pet.class);

        assertEquals(actualPet.getId(), pet.getId(), "Pet id");
        assertEquals(actualPet.getName(), pet.getName(), "Pet name");
    }

    @AfterMethod
    public void cleanUpAfterTest() {
        ApiResponse apiResponse = given().contentType("application/json")
                .when().delete("pet/{petId}", actualPet.getId())
                .then().statusCode(200).extract().as(ApiResponse.class);
        assertEquals(apiResponse.getCode(), Integer.valueOf(200), "Code");
        assertEquals(apiResponse.getType(), "unknown", "Type");
        assertEquals(apiResponse.getMessage(), actualPet.getId().toString(), "Message");
    }
}
