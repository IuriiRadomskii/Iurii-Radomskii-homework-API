package com.epam.tc.api.service;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.specs.RequestSpecifications;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.util.Map;

public class TrelloServiceObj {

    protected Method requestMethod;
    protected Map<String, String> parameters;

    TrelloServiceObj(Map<String, String> parameters, Method requestMethod) {
        this.parameters = parameters;
        this.requestMethod = requestMethod;
    }

    public static RequestBuilder getRequestBuilder() {
        return new RequestBuilder();
    }

    public static RequestBuilder getRequestBuilder(Map<String, String> params) {
        return new RequestBuilder(params);
    }

    public Response sendRequest(Resources resource) {
        return RestAssured
            .given(RequestSpecifications.DEFAULT_SPEC).log().all()
            .queryParams(parameters)
            .request(requestMethod, resource.toString())
            .prettyPeek();
    }

    public Response sendRequest(Resources resource, String additional) {
        return RestAssured
            .given(RequestSpecifications.DEFAULT_SPEC).log().all()
            .queryParams(parameters)
            .request(requestMethod, resource.toString() + additional + "/")
            .prettyPeek();
    }
}
