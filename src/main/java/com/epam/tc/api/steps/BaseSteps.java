package com.epam.tc.api.steps;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.response.Response;
import java.util.List;

public class BaseSteps {

    public <T> T getEntity(Response response, Class<T> cls) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<T>() {}.getType());
    }

    public <T> List<T> getEntities(Response response, Class<T> cls) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<List<T>>() {}.getType());
    }
}
