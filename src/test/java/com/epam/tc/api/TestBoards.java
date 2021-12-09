package com.epam.tc.api;

import static org.hamcrest.MatcherAssert.assertThat;

import com.epam.tc.api.data.TrelloDataProvider;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.specs.RequestSpecifications;
import com.epam.tc.api.specs.ResponseSpecs;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestBoards extends BaseTest {

    @BeforeClass
    public void setup() {
        setCreds();
    }

    @AfterClass
    public void deleteAllBoards() {
        boardSteps.deleteAllBoards(creds);
    }

    @Test(dataProviderClass = TrelloDataProvider.class, dataProvider = "boardData")
    public void checkBoardPosting(Board board) {
        Response createResponse = boardSteps.createBoard(board.getName(), RequestSpecifications.DEFAULT_SPEC, creds);
        Board initBoard = boardSteps.boardToPojo(createResponse);
        assertThat("Checking initial board name", initBoard.getName(), Matchers.equalTo(board.getName()));
    }

    @Test(dataProviderClass = TrelloDataProvider.class, dataProvider = "boardData")
    public void checkBoardUpdating(Board newBoard) {
        Response createResponse = boardSteps.createBoard("initBoardName", RequestSpecifications.DEFAULT_SPEC, creds);
        Board initBoard = boardSteps.boardToPojo(createResponse);

        initBoard.setName(newBoard.getName());

        Response modifyResponse = boardSteps.putBoardName(initBoard, RequestSpecifications.DEFAULT_SPEC, creds);
        Board board = boardSteps.boardToPojo(modifyResponse);
        assertThat("Checking put board name", board.getName(), Matchers.equalTo(newBoard.getName()));
    }

    @Test(dataProviderClass = TrelloDataProvider.class, dataProvider = "boardData")
    public void checkBoardDeleting(Board board) {
        Response createResponse = boardSteps.createBoard(board.getName(), RequestSpecifications.DEFAULT_SPEC, creds);
        Board initBoard = boardSteps.boardToPojo(createResponse);
        Response deleteResponse = boardSteps.deleteBoard(initBoard, RequestSpecifications.DEFAULT_SPEC, creds);
        boardSteps.checkResponse(deleteResponse, ResponseSpecs.goodDeleteResponse);
    }
}
