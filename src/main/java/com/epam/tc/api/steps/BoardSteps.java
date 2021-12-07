package com.epam.tc.api.steps;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.service.BoardServiceObject;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.util.List;

public class BoardSteps {



    public Response createBoard(String boardName) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.POST)
            .setName(boardName)
            .addPathParam("resource", "boards")
            .buildRequest()
            .sendRequest();
    }

    public Board getBoard(Board board) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.GET)
            .buildRequest()
            .sendRequest().as(Board.class);
    }

    public Response putBoardName(Board board) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.PUT)
            .setName(board.getName())
            .buildRequest()
            .sendRequest();
    }

    public Response deleteBoard(Board board) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.DELETE)
            .buildRequest()
            .sendRequest();
    }

    public void deleteAllBoards() {
        Response response =
            BoardServiceObject
                .getRequestBuilder()
                .setMethod(Method.GET)
                .buildRequest()
                .sendRequest(Resources.ALL_MEMBERS_BOARDS);
        List<Board> boards = getBoardsFromJson(response);
        for (Board board : boards) {
            deleteBoard(board);
        }
    }


}
