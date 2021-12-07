package com.epam.tc.api.steps;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.service.BoardServiceObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.util.List;

public class BoardSteps extends BaseSteps {

    public Response createBoard(String boardName) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.POST)
            .setName(boardName)
            .buildRequest()
            .sendRequest(Resources.BOARDS);
    }

    public Board getBoard(String boardID) {
        return getEntity(
            BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.GET)
            .buildRequest()
            .sendRequest(Resources.BOARDS, boardID), Board.class);
    }

    public Response putBoardName(Board board) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.PUT)
            .setName(board.getName())
            .buildRequest()
            .sendRequest(Resources.BOARDS, board.getId());
    }

    public Response deleteBoard(Board board) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.DELETE)
            .buildRequest()
            .sendRequest(Resources.BOARDS, board.getId());
    }

    public void deleteAllBoards() {
        Response response =
            BoardServiceObject
                .getRequestBuilder()
                .setMethod(Method.GET)
                .buildRequest()
                .sendRequest(Resources.ALL_MEMBERS_BOARDS);
        List<Board> boards = getBoards(response);
        for (Board board : boards) {
            deleteBoard(board);
        }
    }


}
