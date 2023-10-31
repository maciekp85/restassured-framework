package pl.javastart.restassured.main.rop;

import org.apache.http.HttpStatus;
import pl.javastart.restassured.main.pojo.ApiResponse;
import pl.javastart.restassured.main.request.configuration.RequestConfigurationBuilder;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class DeletePetEndpoint extends BaseEndpoint<DeletePetEndpoint, ApiResponse> {

    private Integer petId;

    @Override
    protected Type getModelType() {
        return ApiResponse.class;
    }

    @Override
    public DeletePetEndpoint sendRequest() {
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .when().delete("pet/{petId}", petId);
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    public DeletePetEndpoint setPetId(Integer petId) {
        this.petId = petId;
        return this;
    }
}
