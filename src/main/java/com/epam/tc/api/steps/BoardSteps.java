package com.epam.tc.api.steps;

import static com.epam.tc.api.specs.RequestSpecifications.DEFAULT_SPEC;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.service.ServiceObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.qameta.allure.Step;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.util.Map;

public class BoardSteps extends BaseSteps {

    @Step("Create board")
    public Response createBoard(Map<String, String> creds) {
        return ServiceObject
            .builder(creds)
            .setMethod(Method.POST)
            .setName(getRandomString())
            .addPathParam("resource", Resources.BOARD_RESOURCE)
            .buildRequest()
            .sendRequest(Resources.RESOURCE, DEFAULT_SPEC);
    }


    public Response getBoardByID(Board board, Map<String, String> creds) {
        return ServiceObject
            .builder(creds)
            .setMethod(Method.GET)
            .setName(getRandomString())
            .addPathParam("resource", Resources.BOARD_RESOURCE)
            .addPathParam("ID", board.getId())
            .buildRequest()
            .sendRequest(Resources.RESOURCE_ID, DEFAULT_SPEC);
    }


    @Step("Delete board")
    public Response deleteBoard(Board board, Map<String, String> creds) {
        return ServiceObject
            .builder(creds)
            .setMethod(Method.DELETE)
            .addPathParam("resource", Resources.BOARD_RESOURCE)
            .addPathParam("ID", board.getId())
            .buildRequest()
            .sendRequest(Resources.RESOURCE_ID, DEFAULT_SPEC);
    }

    public Board boardToPojo(Response response) {
        return new Gson()
            .fromJson(response.asString().trim(), new TypeToken<Board>() {
            }.getType());
    }
}
