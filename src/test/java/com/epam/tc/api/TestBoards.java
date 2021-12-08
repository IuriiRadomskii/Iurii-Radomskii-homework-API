package com.epam.tc.api;

import static org.hamcrest.MatcherAssert.assertThat;

import com.epam.tc.api.entities.Board;
import com.epam.tc.api.specs.RequestSpecifications;
import com.epam.tc.api.specs.ResponseSpecifications;
import com.epam.tc.api.util.ApiKeysInit;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestBoards extends BaseTest {

    @AfterClass
    public void deleteAllBoards() {
        boardSteps.deleteAllBoards();
    }

    @Test
    public void checkBoardPosting() {
        Response createResponse = boardSteps.createBoard("Board", RequestSpecifications.DEFAULT_SPEC, creds);
        Board initBoard = boardSteps.boardToPojo(createResponse);
        createResponse
            .then()
            .assertThat()
            .spec(ResponseSpecifications.goodResponse);
        assertThat("Checking initial board name", initBoard.getName(), Matchers.equalTo("Board"));
    }

    @Test
    public void checkBoardModifying() {
        Response createResponse = boardSteps.createBoard("Board", RequestSpecifications.DEFAULT_SPEC, creds);
        Board initBoard = boardSteps.boardToPojo(createResponse);

        initBoard.setName("newName");
        Response modifyResponse = boardSteps.putBoardName(initBoard, RequestSpecifications.DEFAULT_SPEC, creds);
        Board board = boardSteps.boardToPojo(modifyResponse);

        modifyResponse
            .then()
            .assertThat()
            .spec(ResponseSpecifications.goodResponse);
        assertThat("Checking put board name", board.getName(), Matchers.equalTo("newName"));
    }

    @Test
    public void checkBoardDeleting() {
        Response createResponse = boardSteps.createBoard("Board", RequestSpecifications.DEFAULT_SPEC, creds);
        Board initBoard = boardSteps.boardToPojo(createResponse);
        boardSteps
            .deleteBoard(initBoard, RequestSpecifications.DEFAULT_SPEC, creds)
            .then()
            .spec(ResponseSpecifications.goodDeleteResponse);
    }
}
