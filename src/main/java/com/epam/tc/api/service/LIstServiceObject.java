package com.epam.tc.api.service;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.entities.TrelloList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.util.Map;

public class LIstServiceObject extends TrelloServiceObj {

    LIstServiceObject(Map<String, String> parameters, Method requestMethod) {
        super(parameters, requestMethod);
    }

    public static RequestBuilder getRequestBuilder() {
        return new RequestBuilder();
    }

    public static RequestBuilder getRequestBuilder(Map<String, String> params) {
        return new RequestBuilder(params);
    }

}
