package com.epam.tc.api.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

public class BaseSteps {

    @Step("Checking the response for compliance with the given response specification ")
    public void checkResponse(Response response, ResponseSpecification spec) {
        response.then().spec(spec);
    }
}
