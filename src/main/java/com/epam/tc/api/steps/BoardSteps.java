package com.epam.tc.api.steps;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.service.BoardServiceObject;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.util.List;

public class BoardSteps {

    public Response createBoard(String boardName) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.POST)
            .setName(boardName)
            .addPathParam("resource", Resources.BOARD_RESOURCE)
            .buildRequest()
            .sendRequest(Resources.TEMPLATE_MAIN_RESOURCE);
    }

    public Board getBoard(Board board) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.GET)
            .buildRequest()
            .sendRequest(Resources.TEMPLATE_MAIN_RESOURCE).as(Board.class);
    }

    public Response putBoardName(Board board) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.PUT)
            .setName(board.getName())
            .addPathParam("resource", Resources.BOARD_RESOURCE)
            .addPathParam("ID", board.getId())
            .buildRequest()
            .sendRequest(Resources.TEMPLATE_RESOURCE_WITH_ID);
    }

    public Response deleteBoard(Board board) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.DELETE)
            .addPathParam("resource", Resources.BOARD_RESOURCE)
            .buildRequest()
            .sendRequest(Resources.TEMPLATE_MAIN_RESOURCE);
    }

    public void deleteAllBoards() {
        BoardServiceObject
                .getRequestBuilder()
                .setMethod(Method.GET)
                .buildRequest()
                .sendRequest(Resources.ALL_MEMBERS_BOARDS)
                .as(new TypeRef<List<Board>>() {})
                .stream()
                .forEach(this::deleteBoard);

    }
}
