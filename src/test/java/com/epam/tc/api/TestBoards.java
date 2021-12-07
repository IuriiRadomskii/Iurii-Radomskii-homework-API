package com.epam.tc.api;

import static org.hamcrest.MatcherAssert.assertThat;

import com.epam.tc.api.entities.Board;
import com.epam.tc.api.specs.ResponseSpecifications;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class TestBoards extends BaseTest {

    @Test
    public void checkBoardPosting() {
        Response createResponse = boardSteps.createBoard("Board");
        Board initBoard = boardSteps.getBoardFromJson(createResponse);

        createResponse.then().assertThat().spec(ResponseSpecifications.goodResponse);
        assertThat("Checking initial board name", initBoard.getName(), Matchers.equalTo("Board"));
        boardSteps.deleteAllBoards();
    }

    @Test
    public void checkBoardModifying() {
        Response createResponse = boardSteps.createBoard("Board");
        Board initBoard = boardSteps.getEntityFromJson(createResponse, Board.class);
        initBoard.setName("newName");

        Response modifyResponse = boardSteps.putBoardName(initBoard);
        Board board = boardSteps.getEntityFromJson(modifyResponse, Board.class);

        modifyResponse.then().assertThat().spec(ResponseSpecifications.goodResponse);
        assertThat("Checking put board name", board.getName(), Matchers.equalTo("newName"));
        boardSteps.deleteAllBoards();
    }

    @Test
    public void checkBoardDeleting() {
        Response createResponse = boardSteps.createBoard("Board");
        Board initBoard = boardSteps.getEntityFromJson(createResponse, Board.class);
        boardSteps
            .deleteBoard(initBoard)
            .then()
            .spec(ResponseSpecifications.goodDeleteResponse);
    }

}
