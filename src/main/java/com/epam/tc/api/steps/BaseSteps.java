package com.epam.tc.api.steps;

import com.epam.tc.api.specs.ResponseSpecs;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.lang3.RandomStringUtils;

public class BaseSteps {

    @Step("Checking the response for compliance with the given response specification ")
    public void checkGoodResponse(Response response) {
        response.then().spec(ResponseSpecs.GOOD_RESPONSE);
    }

    public String getRandomString() {
        int length = 10;
        return RandomStringUtils.random(10, true, true);
    }
}
