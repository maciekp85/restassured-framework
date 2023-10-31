package pl.javastart.restassured.main.rop;

import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.lang.reflect.Type;

// E parameter - class of endpoint
// M parameter - class of POJO model for response
public abstract class BaseEndpoint<E, M> {

    // field for storing response
    protected Response response;

    // method returns type of model (POJO class)
    protected abstract Type getModelType();

    // method sends request, returns E (class of endpoint)
    public abstract E sendRequest();

    // method returns success status code
    protected abstract int getSuccessStatusCode();

    // method returns response in model format
    public M getResponseModel() {
        return response.as(getModelType());
    }

    // method verifies if status code is success
    public E assertRequestSuccess() {
        return assertStatusCode(getSuccessStatusCode());
    }

    // method verifies any status code, returns E parameter - endpoint class
    public E assertStatusCode(int statusCode) {
        Assertions.assertThat(response.getStatusCode()).as("Status code").isEqualTo(statusCode);
        return (E) this;
    }
}