package com.epam.tc.api.specs;

import static org.hamcrest.Matchers.lessThan;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class ResponseSpecifications {

    public static final ResponseSpecification goodResponse = new ResponseSpecBuilder()
        .expectContentType(ContentType.JSON)
        .expectStatusCode(HttpStatus.SC_OK)
        .expectResponseTime(lessThan(5000L))
        .build();

    public static final ResponseSpecification goodDeleteResponse = new ResponseSpecBuilder()
        .expectBody("_value", Matchers.nullValue())
        .addResponseSpecification(goodResponse)
        .build();

    public static final ResponseSpecification badResponse = new ResponseSpecBuilder()
        .expectContentType(ContentType.TEXT)
        .expectResponseTime(lessThan(5000L))
        .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
        .build();

}
