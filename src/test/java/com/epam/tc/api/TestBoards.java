package com.epam.tc.api;

import static org.hamcrest.MatcherAssert.assertThat;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.service.TrelloServiceObj;
import com.epam.tc.api.specs.RequestSpecifications;
import com.epam.tc.api.specs.ResponseSpecifications;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.util.List;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class TestBoards {

    @AfterMethod
    public void deleteAllBoards() {
        Response response =
            TrelloServiceObj
                .getRequestBuilder()
                .buildRequest()
                .sendRequest(Resources.MEMBERS_BOARDS);
        List<Board> boards = TrelloServiceObj.getBoards(response);
        for (Board board : boards) {

        }
    }


    @Test
    public void checkBoardPosting() {
        Response response =
            TrelloServiceObj
            .getRequestBuilder()
            .setMethod(Method.POST)
            .setBoardName("Board")
            .buildRequest()
            .sendRequest(Resources.BOARDS);
        response.then().assertThat().spec(ResponseSpecifications.goodResponse);
        Board board = TrelloServiceObj.getBoard(response);
        assertThat("Checking initial board name", board.getName(), Matchers.equalTo("Board"));
    }

    @Test
    public void checkBoardModifying() {
        Response response =
            TrelloServiceObj
                .getRequestBuilder()
                .setMethod(Method.POST)
                .setBoardName("Board")
                .buildRequest()
                .sendRequest(Resources.BOARDS);
        response
            .then()
            .assertThat()
            .statusCode(200);
        Board board = TrelloServiceObj.getBoard(response);
        assertThat("Checking initial board name", board.getName(), Matchers.equalTo("Board"));
    }


}
