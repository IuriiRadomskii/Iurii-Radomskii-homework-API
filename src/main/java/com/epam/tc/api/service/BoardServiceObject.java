package com.epam.tc.api.service;

import com.epam.tc.api.entities.Board;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.util.Map;

public class BoardServiceObject extends AbsctractTrelloServiceObj {

    BoardServiceObject(Map<String, String> queryParams, Map<String, String> pathParams, Method requestMethod) {
        super(queryParams, pathParams, requestMethod);
    }

    public static BoardBuilder getRequestBuilder() {
        return new BoardBuilder();
    }

    public static BoardBuilder getRequestBuilder(Map<String, String> additionalParameters) {
        return new BoardBuilder(additionalParameters);
    }

    public static class BoardBuilder extends AbstractBuilder<BoardBuilder, BoardServiceObject> {

        BoardBuilder() {
        }

        BoardBuilder(Map<String, String> additionalParameters) {
            super(additionalParameters);
        }

        @Override
        public BoardServiceObject buildRequest() {
            return new BoardServiceObject(queryParams, pathParams, requestMethod);
        }
    }
}
