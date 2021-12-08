package com.epam.tc.api.service;

import com.epam.tc.api.data.ParametersName;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.entities.TrelloList;
import com.epam.tc.api.specs.RequestSpecifications;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;

public class ServiceObject {

    protected Method requestMethod;
    protected Map<String, String> queryParams;
    protected Map<String, String> pathParams;

    ServiceObject(Map<String, String> queryParams, Map<String, String> pathParams, Method requestMethod) {
        this.queryParams = queryParams;
        this.pathParams = pathParams;
        this.requestMethod = requestMethod;
    }

    public static RequestBuilder builder() {
        return new RequestBuilder();
    }

    public static RequestBuilder builder(Map<String, String> queryParams) {
        return new RequestBuilder(queryParams);
    }

    public static RequestBuilder builder(Map<String, String> queryParams, Map<String, String> pathParams) {
        return new RequestBuilder(queryParams, pathParams);
    }

    public Response sendRequestWithDefaultSpec(String pathTemplate) {
        return RestAssured
            .given(RequestSpecifications.DEFAULT_SPEC)
            .log().all()
            .pathParams(pathParams)
            .queryParams(queryParams)
            .request(requestMethod, pathTemplate)
            .prettyPeek();
    }

    public Response sendRequest(String pathTemplate, RequestSpecification spec) {
        return RestAssured
            .given(spec)
            .log().all()
            .pathParams(pathParams)
            .queryParams(queryParams)
            .request(requestMethod, pathTemplate)
            .prettyPeek();
    }

    public static Board jsonBoardToPojo(Response response) {
        return new Gson()
            .fromJson(response.asString().trim(), new TypeToken<Board>() {
            }.getType());
    }

    public static TrelloList jsonListToPojo(Response response) {
        return new Gson()
            .fromJson(response.asString().trim(), new TypeToken<TrelloList>() {
            }.getType());
    }

    public static class RequestBuilder {

        protected Map<String, String> queryParams = new HashMap<>();
        protected Map<String, String> pathParams = new HashMap<>();

        protected Method requestMethod;

        public RequestBuilder() {
        }

        public RequestBuilder(Map<String, String> queryParams) {
            this.queryParams.putAll(queryParams);
        }

        public RequestBuilder(Map<String, String> queryParams, Map<String, String> pathParams) {
            this.queryParams.putAll(queryParams);
            this.pathParams.putAll(pathParams);
        }

        public RequestBuilder setMethod(Method method) {
            requestMethod = method;
            return this;
        }

        public RequestBuilder setName(String name) {
            queryParams.put(ParametersName.NAME, name);
            return this;
        }

        public RequestBuilder setIdBoard(String boardID) {
            queryParams.put(ParametersName.ID_BOARD, boardID);
            return this;
        }

        public RequestBuilder setIdList(String boardID) {
            queryParams.put(ParametersName.ID_BOARD, boardID);
            return this;
        }

        public RequestBuilder addQueryParam(String paramName, String paramValue) {
            queryParams.put(paramName, paramValue);
            return this;
        }

        public RequestBuilder addPathParam(String paramName, String paramValue) {
            pathParams.put(paramName, paramValue);
            return this;
        }

        public ServiceObject buildRequest() {
            return new ServiceObject(queryParams, pathParams, requestMethod);
        }

    }
}
