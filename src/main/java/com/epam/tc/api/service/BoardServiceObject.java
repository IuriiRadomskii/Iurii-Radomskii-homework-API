package com.epam.tc.api.service;

import io.restassured.http.Method;
import java.util.Map;

public class BoardServiceObject extends TrelloServiceObj {

    BoardServiceObject(Map<String, String> parameters, Method requestMethod) {
        super(parameters, requestMethod);
    }

    public static RequestBuilder getRequestBuilder() {
        return new RequestBuilder();
    }

    public static RequestBuilder getRequestBuilder(Map<String, String> params) {
        return new RequestBuilder(params);
    }
}
