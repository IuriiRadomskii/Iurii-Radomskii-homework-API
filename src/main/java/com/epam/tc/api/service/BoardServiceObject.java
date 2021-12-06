package com.epam.tc.api.service;

import io.restassured.http.Method;
import java.util.HashMap;
import java.util.Map;

public class BoardServiceObject extends AbsctractTrelloServiceObj {

    BoardServiceObject(Map<String, String> parameters, Method requestMethod) {
        super(parameters, requestMethod);
    }

    public BoardBuilder getRequestBuilder() {
        return new BoardBuilder();
    }

    public BoardBuilder getRequestBuilder(Map<String, String> additionalParameters) {
        return new BoardBuilder(additionalParameters);
    }

    public static class BoardBuilder extends AbstractBuilder<BoardBuilder, BoardServiceObject> {

        public static final String NAME = "name";
        public static final String BOARD_ID = "idBoard";
        public static final String LIST_ID = "idList";

        private Map<String, String> parameters = new HashMap<>();
        private Method requestMethod;

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
