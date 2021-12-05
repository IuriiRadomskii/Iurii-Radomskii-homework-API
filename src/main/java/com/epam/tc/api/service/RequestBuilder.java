package com.epam.tc.api.service;

import io.restassured.http.Method;
import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {

    public static final String NAME = "name";
    public static final String BOARD_ID = "idBoard";

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
