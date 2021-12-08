package com.epam.tc.api;

import static org.hamcrest.MatcherAssert.assertThat;

import com.epam.tc.api.entities.Board;
import com.epam.tc.api.specs.RequestSpecifications;
import com.epam.tc.api.specs.ResponseSpecifications;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class TestBoards extends BaseTest {

    @AfterClass
    public void deleteAllBoards() {
        boardSteps.deleteAllBoards(RequestSpecifications.DEFAULT_SPEC);
    }

    @Test
    public void checkBoardPosting() {
        Response createResponse = boardSteps.createBoard("Board", RequestSpecifications.DEFAULT_SPEC);
        Board initBoard = boardSteps.responseToPojo(createResponse);
        createResponse
            .then()
            .assertThat()
            .spec(ResponseSpecifications.goodResponse);
        assertThat("Checking initial board name", initBoard.getName(), Matchers.equalTo("Board"));
    }

    @Test
    public void checkBoardModifying() {
        Response createResponse = boardSteps.createBoard("Board", RequestSpecifications.DEFAULT_SPEC);
        Board initBoard = boardSteps.responseToPojo(createResponse);

        initBoard.setName("newName");
        Response modifyResponse = boardSteps.putBoardName(initBoard, RequestSpecifications.DEFAULT_SPEC);
        Board board = boardSteps.responseToPojo(createResponse);

        modifyResponse
            .then()
            .assertThat()
            .spec(ResponseSpecifications.goodResponse);
        assertThat("Checking put board name", board.getName(), Matchers.equalTo("newName"));
    }

    @Test
    public void checkBoardDeleting() {
        Response createResponse = boardSteps.createBoard("Board", RequestSpecifications.DEFAULT_SPEC);
        Board initBoard = boardSteps.responseToPojo(createResponse);
        boardSteps
            .deleteBoard(initBoard, RequestSpecifications.DEFAULT_SPEC)
            .then()
            .spec(ResponseSpecifications.goodDeleteResponse);
    }

}
