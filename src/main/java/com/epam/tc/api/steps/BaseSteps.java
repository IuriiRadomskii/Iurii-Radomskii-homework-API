package com.epam.tc.api.steps;

import com.epam.tc.api.entities.Board;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.response.Response;
import java.util.List;

public class BaseSteps {

    public <T> T getEntityFromJson(Response response, Class<T> cls) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<T>() {}.getType());
    }

    public <T> List<T> getEntitiesFromJson(Response response, Class<T> cls) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<List<T>>() {}.getType());
    }

    public Board getBoardFromJson(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<Board>() {}.getType());
    }

    public List<Board> getBoardsFromJson(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<List<Board>>() {}.getType());
    }
}
