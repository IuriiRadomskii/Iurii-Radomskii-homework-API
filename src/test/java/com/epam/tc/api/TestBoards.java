package com.epam.tc.api;

import static com.epam.tc.api.specs.RequestSpecifications.DEFAULT_SPEC;
import static com.epam.tc.api.specs.ResponseSpecs.GOOD_DELETE_RESPONSE;
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
        //Create test board on trello
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

    public void checkBoardUpdating() {
        //Create test board on trello
        Response createResponse = boardSteps.createBoard(creds);
        boardSteps.checkGoodResponse(createResponse);
        Board board = boardSteps.boardToPojo(createResponse);

        //Put new name to trello board
        String newName = boardSteps.getRandomString();
        Response modifyResponse = ServiceObject
            .builder(creds)
                     .addPathParam("resource", Resources.BOARD_RESOURCE)
                     .addPathParam("ID", board.getId())
                     .setMethod(Method.PUT)
                     .addQueryParam(ParametersName.NAME, newName)
                     .buildRequest()
                     .sendRequest(Resources.RESOURCE_ID, DEFAULT_SPEC);
        boardSteps.checkGoodResponse(modifyResponse);
        Board newBoard = boardSteps.boardToPojo(modifyResponse);

        assertThat("Checking put board name", newName, Matchers.equalTo(newBoard.getName()));
        onSiteBoardID = board.getId();
    }

    public void checkBoardDeleting() {
        //Create test board on trello
        Response createResponse = boardSteps.createBoard(creds);
        boardSteps.checkGoodResponse(createResponse);
        Board board = boardSteps.boardToPojo(createResponse);

        //Delete board
        Response deleteResponse = boardSteps.deleteBoard(board, creds);
        boardSteps.checkGoodResponse(deleteResponse, GOOD_DELETE_RESPONSE);
    }
}
