package pl.javastart.restassured.main.request.configuration;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;

public class RequestConfigurationBuilder {

    public RequestSpecBuilder getRequestSpecBuilder() {
        return new RequestSpecBuilder()
                .setConfig(RestAssured.config.objectMapperConfig(objectMapperConfig().defaultObjectMapperType(ObjectMapperType.GSON)))
                .setContentType(ContentType.JSON);
    }

    public static RequestSpecification getDefaultRequestSpecification() {
        return new RequestConfigurationBuilder().getRequestSpecBuilder().build();
    }
}
