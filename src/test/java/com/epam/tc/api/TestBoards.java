package com.epam.tc.api;

import static com.epam.tc.api.specs.RequestSpecifications.DEFAULT_SPEC;
import static org.hamcrest.MatcherAssert.assertThat;

import com.epam.tc.api.data.ParametersName;
import com.epam.tc.api.data.Resources;
import com.epam.tc.api.data.TrelloDataProvider;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.service.ServiceObject;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class TestBoards extends BaseTest {

    @AfterMethod
    public void deleteTestBoard() {
        if (onSiteBoardID != null) {
            Board board = new Board();
            board.setId(onSiteBoardID);
            boardSteps.deleteBoard(board, creds);
            onSiteBoardID = null;
        }
    }

    @Test
    public void checkBoardPosting() {
        //Create test board
        Response createResponse = boardSteps.createBoard(creds);
        boardSteps.checkGoodResponse(createResponse);
        Board board = boardSteps.boardToPojo(createResponse);

        //Get board from trello
        Response getResponse = boardSteps.getBoardByID(board, creds);
        boardSteps.checkGoodResponse(getResponse);
        Board gotBoard = boardSteps.boardToPojo(createResponse);

        assertThat("Compare board from POST response and GET response",
            board, Matchers.equalTo(gotBoard));
        onSiteBoardID = gotBoard.getId();
    }

    @Test(dataProviderClass = TrelloDataProvider.class, dataProvider = "boardData")
    public void checkBoardUpdating(Board board) {
        Response createResponse = boardSteps.createBoard(creds);
        Board initBoard = boardSteps.boardToPojo(createResponse);
        initBoard.setName(board.getName());

        Response modifyResponse = ServiceObject
            .builder(creds)
                     .addPathParam("resource", Resources.BOARD_RESOURCE)
                     .addPathParam("ID", initBoard.getId())
                     .setMethod(Method.PUT)
                     .addQueryParam(ParametersName.NAME, initBoard.getName())
                     .buildRequest()
                     .sendRequest(Resources.RESOURCE_ID, DEFAULT_SPEC);

        boardSteps.checkGoodResponse(modifyResponse);
        Board f = boardSteps.boardToPojo(modifyResponse);
        assertThat("Checking put board name", board.getName(), Matchers.equalTo(board.getName()));
        onSiteBoardID = board.getId();
    }

    @Test(dataProviderClass = TrelloDataProvider.class, dataProvider = "boardData")
    public void checkBoardDeleting(Board board) {
        Response createResponse = boardSteps.createBoard(creds);
        Board initBoard = boardSteps.boardToPojo(createResponse);
        Response deleteResponse = boardSteps.deleteBoard(initBoard, creds);
        boardSteps.checkGoodResponse(deleteResponse);
    }
}
