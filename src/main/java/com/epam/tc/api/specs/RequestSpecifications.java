package com.epam.tc.api.specs;

import com.epam.tc.api.util.ApiKeysInit;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.net.URI;

public class RequestSpecifications {

    private static final URI BASE_URI = URI.create("https://api.trello.com");
    public static final RequestSpecBuilder SPEC_WITH_API_KEYS_PARAMS = new RequestSpecBuilder()
        .addQueryParam("key", ApiKeysInit.getApiKey("key"))
        .addQueryParam("token", ApiKeysInit.getApiKey("token"));

    public static final RequestSpecification DEFAULT_SPEC =
        SPEC_WITH_API_KEYS_PARAMS
            .setAccept(ContentType.JSON)
            .setBaseUri(BASE_URI)
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build();

}
