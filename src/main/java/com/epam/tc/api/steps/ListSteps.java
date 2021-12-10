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
import io.qameta.allure.Step;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListSteps extends BaseSteps {

    @Step("Create list")
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

    @Step("Get list")
    public Response getList(TrelloList trelloList, RequestSpecification spec, Map<String, String> creds) {
        return ServiceObject
            .builder(creds)
                .setMethod(Method.GET)
                .addPathParam("ID", trelloList.getId())
                .addPathParam("resource", Resources.LIST_RESOURCE)
                .buildRequest()
                .sendRequest(Resources.RESOURCE_ID, spec);
    }

    @Step("Put list name list")
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

    @Step("Delete list")
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

    public List<TrelloList> listsToList(Response response) {
        return new Gson()
            .fromJson(response.asString().trim(), new TypeToken<List<TrelloList>>() {
            }.getType());
    }
}
