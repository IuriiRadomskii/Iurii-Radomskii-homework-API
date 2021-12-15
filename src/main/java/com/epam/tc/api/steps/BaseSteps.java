package com.epam.tc.api.steps;

import com.epam.tc.api.specs.ResponseSpecs;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

public class BaseSteps {

    @Step("Checking the response for compliance with the given response specification ")
    public void checkGoodResponse(Response response) {
        response.then().spec(ResponseSpecs.GOOD_RESPONSE);
    }
}
