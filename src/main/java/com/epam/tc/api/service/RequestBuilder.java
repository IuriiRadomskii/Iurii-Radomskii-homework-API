package com.epam.tc.api.service;

import com.epam.tc.api.data.ParametersName;
import io.restassured.http.Method;
import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {

    private Map<String, String> parameters = new HashMap<>();
    private Method requestMethod;

    public RequestBuilder() {}

    public RequestBuilder(Map<String, String> params) {
        this.parameters.putAll(params);
    }

    public RequestBuilder setMethod(Method method) {
        requestMethod = method;
        return this;
    }

    public RequestBuilder setBoardName(String name) {
        parameters.put(ParametersName.NAME, name);
        return this;
    }

    public TrelloServiceObj buildRequest() {
        return new TrelloServiceObj(parameters, requestMethod);
    }

}
