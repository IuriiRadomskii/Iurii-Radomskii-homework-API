package com.epam.tc.api.specs;

import com.epam.tc.api.util.ApiKeysInit;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.net.URI;

public class RequestSpecifications {

    private static final URI BASE_URI = URI.create(ApiKeysInit.getApiKey("baseURI"));
    public static final RequestSpecification DEFAULT_SPEC =
        new RequestSpecBuilder()
            .addQueryParam("key", ApiKeysInit.getApiKey("key"))
            .addQueryParam("token", ApiKeysInit.getApiKey("token"))
            .setAccept(ContentType.JSON)
            .setBaseUri(BASE_URI)
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build();

}
