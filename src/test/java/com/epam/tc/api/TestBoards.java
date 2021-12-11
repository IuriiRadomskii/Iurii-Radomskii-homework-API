package com.epam.tc.api;

import static com.epam.tc.api.specs.RequestSpecifications.DEFAULT_SPEC;
import static org.hamcrest.MatcherAssert.assertThat;

import com.epam.tc.api.data.TrelloDataProvider;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.specs.ResponseSpecs;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

@Feature("Board")
public class TestBoards extends BaseTest {

    @AfterMethod
    public void deleteTestBoard() {
        if (onSiteBoardID != null) {
            Board board = new Board();
            board.setId(onSiteBoardID);
            boardSteps.deleteBoard(board, DEFAULT_SPEC, creds);
            onSiteBoardID = null;
        }
    }

    @Story("POST")
    @Test(dataProviderClass = TrelloDataProvider.class, dataProvider = "boardData")
    public void checkBoardPosting(Board board) {
        Response createResponse = boardSteps.createBoard(board.getName(), DEFAULT_SPEC, creds);
        boardSteps.checkResponse(createResponse, ResponseSpecs.GOOD_RESPONSE);
        Board initBoard = boardSteps.boardToPojo(createResponse);
        assertThat("Checking initial board name", initBoard.getName(), Matchers.equalTo(board.getName()));
        onSiteBoardID = initBoard.getId();
    }

    @Story("PUT")
    @Test(dataProviderClass = TrelloDataProvider.class, dataProvider = "boardData")
    public void checkBoardUpdating(Board newBoard) {
        Response createResponse = boardSteps.createBoard("initBoardName", DEFAULT_SPEC, creds);
        Board initBoard = boardSteps.boardToPojo(createResponse);
        initBoard.setName(newBoard.getName());

        Response modifyResponse = boardSteps.putBoardName(initBoard, DEFAULT_SPEC, creds);
        boardSteps.checkResponse(modifyResponse, ResponseSpecs.GOOD_RESPONSE);
        Board board = boardSteps.boardToPojo(modifyResponse);
        assertThat("Checking put board name", board.getName(), Matchers.equalTo(newBoard.getName()));
        onSiteBoardID = board.getId();
    }

    @Story("DELETE")
    @Test(dataProviderClass = TrelloDataProvider.class, dataProvider = "boardData")
    public void checkBoardDeleting(Board board) {
        Response createResponse = boardSteps.createBoard(board.getName(), DEFAULT_SPEC, creds);
        Board initBoard = boardSteps.boardToPojo(createResponse);
        Response deleteResponse = boardSteps.deleteBoard(initBoard, DEFAULT_SPEC, creds);
        boardSteps.checkResponse(deleteResponse, ResponseSpecs.GOOD_DELETE_RESPONSE);
    }
}
