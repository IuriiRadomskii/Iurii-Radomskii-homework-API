package com.epam.tc.api.steps;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.service.BoardServiceObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.util.List;

public class BoardSteps {

    public Response createBoard(String boardName) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.POST)
            .setName(boardName)
            .buildRequest()
            .sendRequest(Resources.BOARDS);
    }

    public Response putBoardName(String boardID, String newName) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.PUT)
            .setName(newName)
            .buildRequest()
            .sendRequest(Resources.BOARDS, boardID);
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
        List<Board> boards = getEntities(response, Board.class);
        for (Board board : boards) {
            deleteBoard(board);
        }
    }


    public <T> T getEntity(Response response, Class<T> cls) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<T>() {}.getType());
    }

    public <T> List<T> getEntities(Response response, Class<T> cls) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<List<T>>() {}.getType());
    }

}
