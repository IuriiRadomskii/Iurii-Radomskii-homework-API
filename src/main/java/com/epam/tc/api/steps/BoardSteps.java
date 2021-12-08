package com.epam.tc.api.steps;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.service.BoardServiceObject;
import com.epam.tc.api.specs.RequestSpecifications;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.List;

public class BoardSteps {

    public Response createBoard(String boardName, RequestSpecification spec) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.POST)
            .setName(boardName)
            .addPathParam("resource", Resources.BOARD_RESOURCE)
            .buildRequest()
            .sendRequest(Resources.TEMPLATE_MAIN_RESOURCE, spec);
    }

    public Board getBoard(Board board, RequestSpecification spec) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.GET)
            .buildRequest()
            .sendRequest(Resources.TEMPLATE_MAIN_RESOURCE, spec).as(Board.class);
    }

    public Response putBoardName(Board board, RequestSpecification spec) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.PUT)
            .setName(board.getName())
            .addPathParam("resource", Resources.BOARD_RESOURCE)
            .addPathParam("ID", board.getId())
            .buildRequest()
            .sendRequest(Resources.TEMPLATE_RESOURCE_WITH_ID, spec);
    }

    public Response deleteBoard(Board board, RequestSpecification spec) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.DELETE)
            .addPathParam("resource", Resources.BOARD_RESOURCE)
            .buildRequest()
            .sendRequest(Resources.TEMPLATE_MAIN_RESOURCE, spec);
    }

    private Response delete(Board board) {
        return BoardServiceObject
            .getRequestBuilder()
            .setMethod(Method.DELETE)
            .addPathParam("resource", Resources.BOARD_RESOURCE)
            .buildRequest()
            .sendRequest(Resources.TEMPLATE_MAIN_RESOURCE, RequestSpecifications.DEFAULT_SPEC);
    }

    public void deleteAllBoards(RequestSpecification spec) {
        BoardServiceObject
                .getRequestBuilder()
                .setMethod(Method.GET)
                .buildRequest()
                .sendRequest(Resources.ALL_MEMBERS_BOARDS, spec)
                .as(new TypeRef<List<Board>>() {})
                .stream()
                .forEach(this::delete);
    }

    public <P> P responseToPojo(Response response, Class<P> cls) {
        return new Gson()
            .fromJson(response.asString().trim(), new TypeToken<P>() {
            }.getType());
    }
}
