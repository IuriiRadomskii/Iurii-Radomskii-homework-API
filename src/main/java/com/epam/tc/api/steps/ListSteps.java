package com.epam.tc.api.steps;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.entities.TrelloList;
import com.epam.tc.api.service.BoardServiceObject;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class ListSteps {

    public Response createList(String listName, String boardID) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.POST)
            .setName(listName)
            .setID("idBoard", boardID)
            .addPathParam("resource", Resources.LIST_RESOURCE)
            .buildRequest()
            .sendRequest(Resources.TEMPLATE_MAIN_RESOURCE);
    }

    public TrelloList getList(String idList) {
        return BoardServiceObject
                .getRequestBuilder()
                .setMethod(Method.GET)
                .addQueryParam("idList", idList)
                .buildRequest()
                .sendRequest(Resources.TEMPLATE_MAIN_RESOURCE)
                .as(TrelloList.class);
    }

    public Response putListName(TrelloList  trelloList) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.PUT)
            .setName(trelloList.getName())
            .buildRequest()
            .sendRequest(Resources.TEMPLATE_MAIN_RESOURCE);
    }

    public Response deleteList(TrelloList trelloList) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.DELETE)
            .buildRequest()
            .sendRequest(Resources.TEMPLATE_MAIN_RESOURCE);
    }


}
