package com.epam.tc.api.service;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.specs.RequestSpecifications;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;

public class TrelloServiceObj {

    private Method requestMethod;
    private Map<String, String> parameters;

    TrelloServiceObj(Map<String, String> parameters, Method requestMethod) {
        this.parameters = parameters;
        this.requestMethod = requestMethod;
    }

    public static RequestBuilder getRequestBuilder() {
        return new RequestBuilder();
    }

    //TODO add overloaded sendRequest(Resources res, String additionalPart)
    public Response sendRequest(Resources resource) {
        return RestAssured
            .given(RequestSpecifications.DEFAULT_SPEC).log().all()
            .queryParams(parameters)
            .request(requestMethod, resource.toString())
            .prettyPeek();
    }

    public static Board getBoard(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<Board>() {}.getType());
    }

    public static List<Board> getBoards(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<List<Board>>() {}.getType());
    }



}
