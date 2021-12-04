package com.epam.tc.api.service;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.entities.TrelloList;
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

    public static RequestBuilder getRequestBuilder(Map<String, String> params) {
        return new RequestBuilder(params);
    }


    public Response sendRequest(Resources resource) {
        return RestAssured
            .given(RequestSpecifications.DEFAULT_SPEC).log().all()
            .queryParams(parameters)
            .request(requestMethod, resource.toString())
            .prettyPeek();
    }

    public Response sendRequest(Resources resource, String additional) {
        return RestAssured
            .given(RequestSpecifications.DEFAULT_SPEC).log().all()
            .queryParams(parameters)
            .request(requestMethod, resource.toString() + additional + "/")
            .prettyPeek();
    }

    public static Response createBoard(String boardName) {
        return TrelloServiceObj
            .getRequestBuilder()
            .setMethod(Method.POST)
            .setName(boardName)
            .buildRequest()
            .sendRequest(Resources.BOARDS);
    }

    public static Response putBoardName(String boardID, String newName) {
        return TrelloServiceObj
                .getRequestBuilder()
                .setMethod(Method.PUT)
                .setName(newName)
                .buildRequest()
                .sendRequest(Resources.BOARDS, boardID);
    }

    public static Response deleteBoard(Board board) {
        return TrelloServiceObj
            .getRequestBuilder()
            .setMethod(Method.DELETE)
            .buildRequest()
            .sendRequest(Resources.BOARDS, board.getId());
    }

    public static void deleteAllBoards() {
        Response response =
            TrelloServiceObj
                .getRequestBuilder()
                .setMethod(Method.GET)
                .buildRequest()
                .sendRequest(Resources.ALL_MEMBERS_BOARDS);
        List<Board> boards = TrelloServiceObj.getBoards(response);
        for (Board board : boards) {
            deleteBoard(board);
        }
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
    public static Board getBoard(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<Board>() {}.getType());
    }

    public static List<Board> getBoards(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<List<Board>>() {}.getType());
    }

    public static TrelloList getTrelloList(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<TrelloList>() {}.getType());
    }
}
