package com.epam.tc.api;

import static org.hamcrest.MatcherAssert.assertThat;

import com.epam.tc.api.data.Resources;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.service.TrelloServiceObj;
import com.epam.tc.api.specs.ResponseSpecifications;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.util.List;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class TestBoards {



    @Test
    public void checkBoardPosting() {
        Response createResponse = TrelloServiceObj.createBoard("Board");
        Board initBoard = TrelloServiceObj.getBoard(createResponse);

        createResponse.then().assertThat().spec(ResponseSpecifications.goodResponse);
        assertThat("Checking initial board name", initBoard.getName(), Matchers.equalTo("Board"));
        TrelloServiceObj.deleteAllBoards();
    }

    @Test
    public void checkBoardModifying() {
        Response createResponse = TrelloServiceObj.createBoard("Board");
        Board initBoard = TrelloServiceObj.getBoard(createResponse);

        Response modifyResponse = TrelloServiceObj.putBoardName(initBoard.getId(), "newName");
        Board board = TrelloServiceObj.getBoard(createResponse);

        modifyResponse.then().assertThat().spec(ResponseSpecifications.goodResponse);
        assertThat("Checking new board name", board.getName(), Matchers.equalTo("newName"));
        TrelloServiceObj.deleteAllBoards();
    }

    @Test
    public void checkBoardDeleting() {
        Response createResponse = TrelloServiceObj.createBoard("Board");
        Board initBoard = TrelloServiceObj.getBoard(createResponse);


        assertThat("Checking new board name", board.getName(), Matchers.equalTo("newName"));
        TrelloServiceObj.deleteAllBoards();
    }

}
