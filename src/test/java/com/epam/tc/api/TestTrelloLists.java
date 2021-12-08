package com.epam.tc.api;

import static org.hamcrest.MatcherAssert.assertThat;

import com.epam.tc.api.entities.Board;
import com.epam.tc.api.entities.TrelloList;
import com.epam.tc.api.service.ServiceObject;
import com.epam.tc.api.specs.RequestSpecifications;
import com.epam.tc.api.specs.ResponseSpecs;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestTrelloLists extends BaseTest {

    private static Board testBoard;

    @BeforeClass
    public void createTestBoard() {
        setCreds();
        Response createResponse = boardSteps.createBoard("testBoard",
            RequestSpecifications.DEFAULT_SPEC,
            creds);
        testBoard = ServiceObject.jsonBoardToPojo(createResponse);
    }

    @AfterClass
    public void deleteAllBoards() {
        boardSteps.deleteAllBoards();
    }

    @AfterMethod
    public void deleteAllLists() {
        listSteps.deleteAllListsFromBoard(testBoard);
    }

    @Test
    public void checkListPosting() {
        Response createResponse = listSteps
            .createList("List", testBoard, RequestSpecifications.DEFAULT_SPEC, creds);
        TrelloList initList = ServiceObject.jsonListToPojo(createResponse);
        boardSteps.checkResponse(createResponse, ResponseSpecs.goodResponse);

        assertThat("Checking initial list name", initList.getName(), Matchers.equalTo("List"));
        assertThat("Checking lists boardID", initList.getIdBoard(), Matchers.equalTo(testBoard.getId()));
    }

    @Test
    public void checkListModifying() {
        Response createResponse = listSteps
            .createList("List", testBoard, RequestSpecifications.DEFAULT_SPEC, creds);
        TrelloList trelloList = ServiceObject.jsonListToPojo(createResponse);

        trelloList.setName("newName");
        Response modifyResponse = listSteps.putListName(trelloList, RequestSpecifications.DEFAULT_SPEC, creds);
        TrelloList modifiedList = ServiceObject.jsonListToPojo(modifyResponse);
        boardSteps.checkResponse(modifyResponse, ResponseSpecs.goodResponse);

        assertThat("Checking put list name", modifiedList.getName(), Matchers.equalTo("newName"));
        assertThat("Checking lists boardID", modifiedList.getIdBoard(), Matchers.equalTo(testBoard.getId()));
    }

    @Test
    public void checkListArchiving() {
        Response createResponse = listSteps
            .createList("List", testBoard, RequestSpecifications.DEFAULT_SPEC, creds);
        TrelloList initList = ServiceObject.jsonListToPojo(createResponse);
        Response archiveResponse = listSteps.deleteList(initList, RequestSpecifications.DEFAULT_SPEC, creds);
        boardSteps.checkResponse(archiveResponse, ResponseSpecs.goodResponse);
        TrelloList closedList = ServiceObject.jsonListToPojo(archiveResponse);
        assertThat("Checking put list name", closedList.getName(), Matchers.equalTo("List"));
        assertThat("Checking lists boardID", closedList.getIdBoard(), Matchers.equalTo(testBoard.getId()));
        assertThat("Checking lists status", closedList.getClosed(), Matchers.equalTo(true));
    }
}
