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

    public static Response createTrelloList(String listName, String boardID) {
        return TrelloServiceObj
            .getRequestBuilder()
            .setMethod(Method.POST)
            .setName(listName)
            .setBoardID(boardID)
            .buildRequest()
            .sendRequest(Resources.LISTS);
    }

    public static Response putTrelloListName(String listName, String boardID, String listID) {
        return TrelloServiceObj
            .getRequestBuilder()
            .setMethod(Method.PUT)
            .setName(listName)
            .setBoardID(boardID)
            .buildRequest()
            .sendRequest(Resources.LISTS, listID);
    }

    //TODO generify get method to be used to all entities
    public static TrelloList getTrelloList(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<TrelloList>() {}.getType());
    }
}
