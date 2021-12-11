package com.epam.tc.api.specs;

import static org.hamcrest.Matchers.lessThan;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

public class ResponseSpecs {

    public static final ResponseSpecification GOOD_RESPONSE = new ResponseSpecBuilder()
        .expectContentType(ContentType.JSON)
        .expectStatusCode(HttpStatus.SC_OK)
        .expectResponseTime(lessThan(5000L))
        .build();

    public static final ResponseSpecification GOOD_DELETE_RESPONSE = new ResponseSpecBuilder()
        .expectBody("_value", Matchers.nullValue())
        .addResponseSpecification(GOOD_RESPONSE)
        .build();
}
