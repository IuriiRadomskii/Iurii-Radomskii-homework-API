package com.epam.tc.api.service;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.specs.RequestSpecifications;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public abstract class AbsctractTrelloServiceObj {

    protected Method requestMethod;
    protected Map<String, String> parameters;

    AbsctractTrelloServiceObj(Map<String, String> parameters, Method requestMethod) {
        this.parameters = parameters;
        this.requestMethod = requestMethod;
    }

    public abstract static class AbstractBuilder<B extends AbstractBuilder<B, T>, T extends AbsctractTrelloServiceObj> {

        public static final String NAME = "name";
        public static final String BOARD_ID = "idBoard";
        public static final String LIST_ID = "idList";

        protected Map<String, String> parameters = new HashMap<>();
        protected Method requestMethod;

        public AbstractBuilder() {
        }

        public AbstractBuilder(Map<String, String> params) {
            this.parameters.putAll(params);
        }

        protected B self() {
            return (B) this;
        }

        public B setMethod(Method method) {
            requestMethod = method;
            return self();
        }

        public B setName(String boardName) {
            parameters.put(NAME, boardName);
            return self();
        }

        public B setID(String idName, String boardID) {
            parameters.put(idName, boardID);
            return self();
        }

        abstract T buildRequest();

    }

    public Response sendRequest(Resources resource) {
        return RestAssured
            .given(RequestSpecifications.DEFAULT_SPEC).log().all()
            .queryParams(parameters)
            .request(requestMethod, resource.toString())
            .prettyPeek();
    }

    public Response sendRequest(Resources resource, String additional) {
        return RestAssured
            .given(RequestSpecifications.DEFAULT_SPEC).log().all()
            .queryParams(parameters)
            .request(requestMethod, resource.toString() + additional + "/")
            .prettyPeek();
    }
}
