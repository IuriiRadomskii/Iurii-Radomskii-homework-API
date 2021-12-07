package com.epam.tc.api.service;

import io.restassured.http.Method;
import java.util.Map;

public class ListServiceObject extends AbsctractTrelloServiceObj {

    ListServiceObject(Map<String, String> parameters, Method requestMethod) {
        super(parameters, requestMethod);
    }

    public static ListBuilder getRequestBuilder() {
        return new ListBuilder();
    }

    public static ListBuilder getRequestBuilder(Map<String, String> additionalParameters) {
        return new ListBuilder(additionalParameters);
    }

    public static class ListBuilder extends AbstractBuilder<ListBuilder, ListServiceObject> {

        ListBuilder() {

        }

        ListBuilder(Map<String, String> additionalParameters) {
            super(additionalParameters);
        }

        @Override
        public ListServiceObject buildRequest() {
            return new ListServiceObject(parameters, requestMethod);
        }
    }

}
