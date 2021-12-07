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
    protected Map<String, String> queryParams;
    protected Map<String, String> pathParams;

    AbsctractTrelloServiceObj(Map<String, String> queryParams, Map<String, String> pathParams, Method requestMethod) {
        this.queryParams = queryParams;
        this.pathParams = pathParams;
        this.requestMethod = requestMethod;
    }

    public abstract static class AbstractBuilder<B extends AbstractBuilder<B, T>, T extends AbsctractTrelloServiceObj> {

        public static final String NAME = "name";
        public static final String BOARD_ID = "idBoard";
        public static final String LIST_ID = "idList";

        protected Map<String, String> queryParams = new HashMap<>();
        protected Map<String, String> pathParams = new HashMap<>();
        protected Method requestMethod;

        public AbstractBuilder() {
        }

        public AbstractBuilder(Map<String, String> params) {
            this.queryParams.putAll(params);
        }

        protected B self() {
            return (B) this;
        }

        public B setMethod(Method method) {
            requestMethod = method;
            return self();
        }

        public B setName(String name) {
            queryParams.put(NAME, name);
            return self();
        }

        public B setID(String idName, String boardID) {
            queryParams.put(idName, boardID);
            return self();
        }

        public B addQueryParam(String paramName, String paramValue) {
            queryParams.put(paramName, paramValue);
            return self();
        }

        public B addPathParam(String paramName, String paramValue) {
            pathParams.put(paramName, paramValue);
            return self();
        }

        abstract T buildRequest();

    }

    public Response sendRequest() {
        return RestAssured
            .given(RequestSpecifications.DEFAULT_SPEC).log().all()
            .pathParams(pathParams)
            .queryParams(queryParams)
            .request(requestMethod, String.valueOf(Resources.TEMPLATE))
            .prettyPeek();
    }

    public Response sendRequest(Resources resources) {
        return RestAssured
            .given(RequestSpecifications.DEFAULT_SPEC).log().all()
            .pathParams(pathParams)
            .queryParams(queryParams)
            .request(requestMethod, String.valueOf(resources))
            .prettyPeek();
    }

    public abstract  <P> P mapResponseToPojo(Response response);

}
