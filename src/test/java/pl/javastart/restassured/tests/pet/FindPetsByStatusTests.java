package pl.javastart.restassured.tests.pet;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.javastart.restassured.main.pojo.pet.Pet;
import pl.javastart.restassured.main.rop.pet.CreatePetEndpoint;
import pl.javastart.restassured.main.rop.pet.DeletePetEndpoint;
import pl.javastart.restassured.main.rop.pet.FindPetByStatusEndpoint;
import pl.javastart.restassured.main.test.data.PetTestDataGenerator;
import pl.javastart.restassured.main.test.data.pet.PetStatus;
import pl.javastart.restassured.tests.testbases.SuiteTestBase;

import java.util.LinkedList;
import java.util.List;

public class FindPetsByStatusTests extends SuiteTestBase {

    private List<Pet> availablePetsList = new LinkedList<>();

    @BeforeMethod
    public void createPets() {
        for (int i = 0; i < 1; i++) {
            Pet pet = new PetTestDataGenerator().generatePet();
            pet.setStatus(PetStatus.AVAILABLE.getStatus());
            Pet createdPet = new CreatePetEndpoint().setPet(pet).sendRequest().assertRequestSuccess().getResponseModel();
            availablePetsList.add(createdPet);
        }
    }

    @TmsLink("ID-7")
    @Severity(SeverityLevel.NORMAL)
    @Description("The goal of this test is to find pets by status available")
    @Test
    public void givenPetWithStatusAvailableWhenFindByStatusAvailableThenPetsAreFound() {
        Pet[] actualPets = new FindPetByStatusEndpoint().setStatus(PetStatus.AVAILABLE.getStatus()).sendRequest().assertRequestSuccess().getResponseModel();

        Assertions.assertThat(actualPets)
                .describedAs("Actual pets does not contains pet with status available")
                .usingRecursiveFieldByFieldElementComparator()
                .containsAll(availablePetsList);
    }

    @AfterMethod
    public void cleanUpAfterTest() {
        availablePetsList.forEach(pet -> new DeletePetEndpoint().setPetId(pet.getId()).sendRequest().assertRequestSuccess());
    }
}