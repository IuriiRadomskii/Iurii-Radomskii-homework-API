package com.epam.tc.api.steps;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.entities.TrelloList;
import com.epam.tc.api.service.BoardServiceObject;
import com.epam.tc.api.service.ListServiceObject;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class ListSteps extends BaseSteps {

    public Response createList(String listName, String boardID) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.POST)
            .setName(listName)
            .setID("idList", boardID)
            .buildRequest()
            .sendRequest(Resources.LISTS);
    }

    public TrelloList getList(String idList) {
        return getEntity(
            BoardServiceObject
                .getRequestBuilder()
                .setMethod(Method.GET)
                .buildRequest()
                .sendRequest(Resources.LISTS, idList), TrelloList.class);
    }

    public Response putListName(TrelloList  trelloList) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.PUT)
            .setName(trelloList.getName())
            .buildRequest()
            .sendRequest(Resources.LISTS, trelloList.getId());
    }

    public Response deleteList(TrelloList trelloList) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.DELETE)
            .buildRequest()
            .sendRequest(Resources.LISTS, trelloList.getId());
    }


}
