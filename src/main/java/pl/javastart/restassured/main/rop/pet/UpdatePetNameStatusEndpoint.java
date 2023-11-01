package pl.javastart.restassured.main.rop.pet;

import org.apache.http.HttpStatus;
import pl.javastart.restassured.main.pojo.ApiResponse;
import pl.javastart.restassured.main.request.configuration.RequestConfigurationBuilder;
import pl.javastart.restassured.main.rop.BaseEndpoint;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class UpdatePetNameStatusEndpoint extends BaseEndpoint<UpdatePetNameStatusEndpoint, ApiResponse> {

    private Integer petId;
    private String name;
    private String status;

    @Override
    protected Type getModelType() {
        return ApiResponse.class;
    }

    @Override
    public UpdatePetNameStatusEndpoint sendRequest() {
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification().contentType("application/x-www-form-urlencoded"))
                .when()
                .formParam("name", name)
                .formParam("status", status)
                .post("pet/{petId}", petId);
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    public UpdatePetNameStatusEndpoint setPetId(Integer petId) {
        this.petId = petId;
        return this;
    }

    public UpdatePetNameStatusEndpoint setName(String name) {
        this.name = name;
        return this;
    }

    public UpdatePetNameStatusEndpoint setStatus(String status) {
        this.status = status;
        return this;
    }
}