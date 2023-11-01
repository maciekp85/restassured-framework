package pl.javastart.restassured.main.rop.pet;

import org.apache.http.HttpStatus;
import pl.javastart.restassured.main.pojo.pet.Pet;
import pl.javastart.restassured.main.request.configuration.RequestConfigurationBuilder;
import pl.javastart.restassured.main.rop.BaseEndpoint;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class GetPetEndpoint extends BaseEndpoint<GetPetEndpoint, Pet> {

    private Integer petId;

    @Override
    protected Type getModelType() {
        return Pet.class;
    }

    @Override
    public GetPetEndpoint sendRequest() {
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .when().get("pet/{petId}", petId);
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    public GetPetEndpoint setPetId(Integer petId) {
        this.petId = petId;
        return this;
    }
}