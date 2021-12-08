package com.epam.tc.api.steps;

import com.epam.tc.api.data.ParametersName;
import com.epam.tc.api.data.Resources;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.entities.TrelloList;
import com.epam.tc.api.service.ServiceObject;
import com.epam.tc.api.specs.RequestSpecifications;
import com.epam.tc.api.util.ApiKeysInit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListSteps extends BaseSteps {

    public Response createList(String listName, Board board, RequestSpecification spec, Map<String, String> creds) {
        return ServiceObject
            .builder(creds)
            .setMethod(Method.POST)
            .setName(listName)
            .addQueryParam("idBoard", board.getId())
            .addPathParam("resource", Resources.LIST_RESOURCE)
            .buildRequest()
            .sendRequest(Resources.RESOURCE, spec);
    }

    public Response getList(TrelloList trelloList, RequestSpecification spec, Map<String, String> creds) {
        return ServiceObject
            .builder(creds)
                .setMethod(Method.GET)
                .addPathParam("ID", trelloList.getId())
                .addPathParam("resource", Resources.LIST_RESOURCE)
                .buildRequest()
                .sendRequest(Resources.RESOURCE_ID, spec);
    }

    public Response putListName(TrelloList  trelloList, RequestSpecification spec, Map<String, String> creds) {
        return ServiceObject
            .builder(creds)
            .setMethod(Method.PUT)
            .setName(trelloList.getName())
            .addPathParam("ID", trelloList.getId())
            .addPathParam("resource", Resources.LIST_RESOURCE)
            .buildRequest()
            .sendRequest(Resources.RESOURCE_ID, spec);
    }

    public Response deleteList(TrelloList trelloList, RequestSpecification spec, Map<String, String> creds) {
        return ServiceObject
            .builder(creds)
            .setMethod(Method.PUT)
            .addQueryParam(ParametersName.CLOSED_VALUE, "true")
            .addPathParam("resource", Resources.LIST_RESOURCE)
            .addPathParam("ID", trelloList.getId())
            .addPathParam("resource_1", Resources.CLOSED)
            .buildRequest()
            .sendRequest(Resources.RESOURCE_ID_RESOURCE, spec);
    }

    public void deleteAllListsFromBoard(Board board) {
        Map<String, String> creds = new HashMap<>();
        creds.put("key", ApiKeysInit.getApiKey());
        creds.put("token", ApiKeysInit.getApiToken());
        Response response = ServiceObject
            .builder(creds)
            .setMethod(Method.GET)
            .addPathParam("resource", Resources.BOARD_RESOURCE)
            .addPathParam("ID", board.getId())
            .addPathParam("resource_1", Resources.LIST_RESOURCE)
            .buildRequest()
            .sendRequest(Resources.RESOURCE_ID_RESOURCE, RequestSpecifications.DEFAULT_SPEC);
        listsToList(response).stream().forEach(l -> this.deleteList(l, RequestSpecifications.DEFAULT_SPEC, creds));
    }

    public List<TrelloList> listsToList(Response response) {
        return new Gson()
            .fromJson(response.asString().trim(), new TypeToken<List<TrelloList>>() {
            }.getType());
    }
}
