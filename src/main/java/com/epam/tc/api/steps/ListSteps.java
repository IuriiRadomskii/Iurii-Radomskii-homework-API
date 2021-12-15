package com.epam.tc.api.steps;

import static com.epam.tc.api.specs.RequestSpecifications.DEFAULT_SPEC;

import com.epam.tc.api.data.ParametersName;
import com.epam.tc.api.data.Resources;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.entities.TrelloList;
import com.epam.tc.api.service.ServiceObject;
import io.qameta.allure.Step;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;

public class ListSteps extends BaseSteps {

    @Step("Create list")
    public Response createListOnBoard(Board board, Map<String, String> creds) {
        return ServiceObject
            .builder(creds)
            .setMethod(Method.POST)
            .setName(getRandomString())
            .addQueryParam("idBoard", board.getId())
            .addPathParam("resource", Resources.LIST_RESOURCE)
            .buildRequest()
            .sendRequest(Resources.RESOURCE, DEFAULT_SPEC);
    }

    @Step("Put list name")
    public Response getListFromBoard(TrelloList trelloList, Map<String, String> creds) {
        return ServiceObject
            .builder(creds)
            .setMethod(Method.GET)
            .addPathParam("resource", Resources.LIST_RESOURCE)
            .addPathParam("ID", trelloList.getId())
            .buildRequest()
            .sendRequest(Resources.RESOURCE_ID, DEFAULT_SPEC);
    }


    @Step("Put list name")
    public Response putListName(TrelloList trelloList, RequestSpecification spec, Map<String, String> creds) {
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
}
