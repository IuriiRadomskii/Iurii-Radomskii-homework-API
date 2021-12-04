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


    public static Board getBoard(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<Board>() {}.getType());
    }

    public static List<Board> getBoards(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<List<Board>>() {}.getType());
    }

    public static Response createBoard(String boardName) {
        return TrelloServiceObj
            .getRequestBuilder()
            .setMethod(Method.POST)
            .setBoardName(boardName)
            .buildRequest()
            .sendRequest(Resources.BOARDS);
    }

    public static Response putBoardName(String boardID, String newName) {
        return TrelloServiceObj
                .getRequestBuilder()
                .setMethod(Method.PUT)
                .setBoardName(newName)
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

}
