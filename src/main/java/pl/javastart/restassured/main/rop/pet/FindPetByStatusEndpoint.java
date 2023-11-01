package pl.javastart.restassured.main.rop.pet;

import org.apache.http.HttpStatus;
import pl.javastart.restassured.main.pojo.pet.Pet;
import pl.javastart.restassured.main.request.configuration.RequestConfigurationBuilder;
import pl.javastart.restassured.main.rop.BaseEndpoint;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class FindPetByStatusEndpoint extends BaseEndpoint<FindPetByStatusEndpoint, Pet[]> {

    private String status;

    @Override
    protected Type getModelType() {
        return Pet[].class;
    }

    @Override
    public FindPetByStatusEndpoint sendRequest() {
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .when()
                .queryParam("status", status)
                .get("pet/findByStatus");
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    public FindPetByStatusEndpoint setStatus(String status) {
        this.status = status;
        return this;
    }
}