package com.epam.tc.api;

import static org.hamcrest.MatcherAssert.assertThat;

import com.epam.tc.api.data.TrelloDataProvider;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.entities.TrelloList;
import com.epam.tc.api.service.ServiceObject;
import com.epam.tc.api.specs.RequestSpecifications;
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
        testBoard = ServiceObject
            .jsonBoardToPojo(boardSteps.createBoard("testBoard", RequestSpecifications.DEFAULT_SPEC, creds));
    }

    @AfterClass
    public void deleteAllBoards() {
        boardSteps.deleteAllBoards(creds);
    }

    @AfterMethod
    public void deleteAllLists() {
        listSteps.deleteAllListsFromBoard(testBoard);
    }

    @Test(dataProviderClass = TrelloDataProvider.class, dataProvider = "listData")
    public void checkListPosting(TrelloList trelloList) {
        Response createResponse = listSteps
            .createList(trelloList.getName(), testBoard, RequestSpecifications.DEFAULT_SPEC, creds);
        TrelloList initList = ServiceObject.jsonListToPojo(createResponse);
        assertThat("Checking initial list name", initList.getName(), Matchers.equalTo(trelloList.getName()));
        assertThat("Checking lists boardID", initList.getIdBoard(), Matchers.equalTo(testBoard.getId()));
    }

    @Test(dataProviderClass = TrelloDataProvider.class, dataProvider = "listData")
    public void checkListModifying(TrelloList trelloList) {
        Response createResponse = listSteps
            .createList(trelloList.getName(), testBoard, RequestSpecifications.DEFAULT_SPEC, creds);
        TrelloList initList = ServiceObject.jsonListToPojo(createResponse);

        initList.setName("newName");
        Response modifyResponse = listSteps.putListName(initList, RequestSpecifications.DEFAULT_SPEC, creds);
        TrelloList modifiedList = ServiceObject.jsonListToPojo(modifyResponse);

        assertThat("Checking put list name", modifiedList.getName(), Matchers.equalTo("newName"));
        assertThat("Checking lists boardID", modifiedList.getIdBoard(), Matchers.equalTo(testBoard.getId()));
    }

    @Test(dataProviderClass = TrelloDataProvider.class, dataProvider = "listData")
    public void checkListArchiving(TrelloList trelloList) {
        Response createResponse = listSteps
            .createList(trelloList.getName(), testBoard, RequestSpecifications.DEFAULT_SPEC, creds);
        TrelloList initList = ServiceObject.jsonListToPojo(createResponse);
        Response archiveResponse = listSteps.deleteList(initList, RequestSpecifications.DEFAULT_SPEC, creds);
        TrelloList closedList = ServiceObject.jsonListToPojo(archiveResponse);
        assertThat("Checking put list name", closedList.getName(), Matchers.equalTo(trelloList.getName()));
        assertThat("Checking lists boardID", closedList.getIdBoard(), Matchers.equalTo(testBoard.getId()));
        assertThat("Checking lists status", closedList.getClosed(), Matchers.equalTo(true));
    }
}
