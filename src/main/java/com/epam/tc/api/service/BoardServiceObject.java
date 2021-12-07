package com.epam.tc.api.service;

import io.restassured.http.Method;
import java.util.Map;

public class BoardServiceObject extends AbsctractTrelloServiceObj {

    BoardServiceObject(Map<String, String> parameters, Method requestMethod) {
        super(parameters, requestMethod);
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
            return new BoardServiceObject(parameters, requestMethod);
        }
    }

}
