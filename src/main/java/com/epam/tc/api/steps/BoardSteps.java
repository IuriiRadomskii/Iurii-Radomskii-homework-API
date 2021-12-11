package com.epam.tc.api.steps;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.service.ServiceObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.qameta.allure.Step;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;

public class BoardSteps extends BaseSteps {

    @Step("Create board")
    public Response createBoard(String boardName, RequestSpecification spec, Map<String, String> creds) {
        return ServiceObject
            .builder(creds)
            .setMethod(Method.POST)
            .setName(boardName)
            .addPathParam("resource", Resources.BOARD_RESOURCE)
            .buildRequest()
            .sendRequest(Resources.RESOURCE, spec);
    }

   @Step("Put board name")
    public Response putBoardName(Board board, RequestSpecification spec, Map<String, String> creds) {
        return ServiceObject
            .builder(creds)
            .setMethod(Method.PUT)
            .setName(board.getName())
            .addPathParam("resource", Resources.BOARD_RESOURCE)
            .addPathParam("ID", board.getId())
            .buildRequest()
            .sendRequest(Resources.RESOURCE_ID, spec);
    }

    @Step("Delete board")
    public Response deleteBoard(Board board, RequestSpecification spec, Map<String, String> creds) {
        return ServiceObject
            .builder(creds)
            .setMethod(Method.DELETE)
            .addPathParam("resource", Resources.BOARD_RESOURCE)
            .addPathParam("ID", board.getId())
            .buildRequest()
            .sendRequest(Resources.RESOURCE_ID, spec);
    }

    public Board boardToPojo(Response response) {
        return new Gson()
            .fromJson(response.asString().trim(), new TypeToken<Board>() {
            }.getType());
    }
}
