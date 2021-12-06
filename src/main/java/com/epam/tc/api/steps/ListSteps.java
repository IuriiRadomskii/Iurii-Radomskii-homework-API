package com.epam.tc.api.steps;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.service.AbsctractTrelloServiceObj;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class ListSteps extends BaseSteps {

    public static Response createTrelloList(String listName, String boardID) {
        return AbsctractTrelloServiceObj
            .getRequestBuilder()
            .setMethod(Method.POST)
            .setName(listName)
            .setBoardID(boardID)
            .buildRequest()
            .sendRequest(Resources.LISTS);
    }

    public static Response putTrelloListName(String listName, String boardID, String listID) {
        return AbsctractTrelloServiceObj
            .getRequestBuilder()
            .setMethod(Method.PUT)
            .setName(listName)
            .setBoardID(boardID)
            .buildRequest()
            .sendRequest(Resources.LISTS, listID);
    }


}
