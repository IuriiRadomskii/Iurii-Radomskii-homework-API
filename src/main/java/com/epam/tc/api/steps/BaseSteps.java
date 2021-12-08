package com.epam.tc.api.steps;

import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

public class BaseSteps {

    public void checkResponse(Response response, ResponseSpecification spec) {
        response.then().spec(spec);
    }

}
