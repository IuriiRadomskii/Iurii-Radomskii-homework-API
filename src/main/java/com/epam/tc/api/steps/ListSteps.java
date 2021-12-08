package com.epam.tc.api.steps;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.entities.TrelloList;
import com.epam.tc.api.service.BoardServiceObject;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ListSteps {

    public Response createList(String listName, String boardID, RequestSpecification spec) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.POST)
            .setName(listName)
            .setID("idBoard", boardID)
            .addPathParam("resource", Resources.LIST_RESOURCE)
            .buildRequest()
            .sendRequest(Resources.TEMPLATE_RESOURCE_WITH_ID, spec);
    }

    public TrelloList getList(String idList, RequestSpecification spec) {
        return BoardServiceObject
                .getRequestBuilder()
                .setMethod(Method.GET)
                .addPathParam("ID", idList)
                .addPathParam("resource", Resources.LIST_RESOURCE)
                .buildRequest()
                .sendRequest(Resources.TEMPLATE_RESOURCE_WITH_ID, spec)
                .as(TrelloList.class);
    }

    public Response putListName(TrelloList  trelloList, RequestSpecification spec) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.PUT)
            .setName(trelloList.getName())
            .addPathParam("ID", trelloList.getId())
            .addPathParam("resource", Resources.LIST_RESOURCE)
            .buildRequest()
            .sendRequest(Resources.TEMPLATE_RESOURCE_WITH_ID, spec);
    }
    //{{baseURI}}/1/lists/61abc39d0ad010379bd3fc5a/closed?key={{apiKey}}&token={{apiToken}}&value=true

    public Response deleteList(TrelloList trelloList, RequestSpecification spec) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.DELETE)
            .addPathParam("ID", trelloList.getId())
            .addPathParam("resource", Resources.LIST_RESOURCE)
            .buildRequest()
            .sendRequest(Resources.TEMPLATE_RESOURCE_WITH_ID, spec);
    }


}
