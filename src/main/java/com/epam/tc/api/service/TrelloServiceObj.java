package com.epam.tc.api.service;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.specs.RequestSpecifications;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public class TrelloServiceObj {

    protected Method requestMethod;
    protected Map<String, String> parameters;

    TrelloServiceObj(Map<String, String> parameters, Method requestMethod) {
        this.parameters = parameters;
        this.requestMethod = requestMethod;
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

    public static RequestBuilder getRequestBuilder() {
        return new RequestBuilder();
    }

    public static RequestBuilder getRequestBuilder(Map<String, String> additionalParameters) {
        return new RequestBuilder(additionalParameters);
    }

    public static class RequestBuilder {

        public static final String NAME = "name";
        public static final String BOARD_ID = "idBoard";
        public static final String LIST_ID = "idList";

        private Map<String, String> parameters = new HashMap<>();
        private Method requestMethod;

        public RequestBuilder() {
        }

        public RequestBuilder(Map<String, String> params) {
            this.parameters.putAll(params);
        }

        public RequestBuilder setMethod(Method method) {
            requestMethod = method;
            return this;
        }

        public RequestBuilder setName(String boardName) {
            parameters.put(NAME, boardName);
            return this;
        }

        public RequestBuilder setBoardID(String boardID) {
            parameters.put(BOARD_ID, boardID);
            return this;
        }

        public TrelloServiceObj buildRequest() {
            return new TrelloServiceObj(parameters, requestMethod);
        }

    }
}
