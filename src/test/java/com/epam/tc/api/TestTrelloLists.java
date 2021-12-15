package com.epam.tc.api;

import static com.epam.tc.api.specs.RequestSpecifications.DEFAULT_SPEC;
import static org.hamcrest.MatcherAssert.assertThat;

import com.epam.tc.api.data.ParametersName;
import com.epam.tc.api.data.Resources;
import com.epam.tc.api.data.TrelloDataProvider;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.entities.TrelloList;
import com.epam.tc.api.service.ServiceObject;
import com.epam.tc.api.specs.RequestSpecifications;
import io.restassured.http.Method;
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
        testBoard = ServiceObject
            .jsonBoardToPojo(boardSteps
                .createBoard(creds));
    }

    @AfterClass
    public void deleteTestBoard() {
        boardSteps.deleteBoard(testBoard, creds);
    }

    @AfterMethod
    public void deleteTestList() {
        if (onSiteListID != null) {
            TrelloList trelloList = new TrelloList();
            trelloList.setId(onSiteListID);
            listSteps.deleteList(trelloList, DEFAULT_SPEC, creds);
            onSiteListID = null;
        }
    }

    @Test
    public void checkListPosting() {
        //Create test list
        Response createResponse = listSteps.createListOnBoard(testBoard, creds);
        listSteps.checkGoodResponse(createResponse);
        TrelloList initList = ServiceObject.jsonListToPojo(createResponse);

        //Get list from board
        Response getResponse = listSteps.getListFromBoard(initList, creds);
        listSteps.checkGoodResponse(getResponse);
        TrelloList gotList = ServiceObject.jsonListToPojo(getResponse);

        assertThat("Checking initial list name", initList.getName(), Matchers.equalTo(gotList.getName()));
        assertThat("Checking lists boardID", gotList.getIdBoard(), Matchers.equalTo(testBoard.getId()));
        onSiteListID = initList.getId();
    }

    @Test
    public void checkListModifying() {
        //Create test list
        Response createResponse = listSteps.createListOnBoard(testBoard, creds);
        listSteps.checkGoodResponse(createResponse);
        TrelloList initList = ServiceObject.jsonListToPojo(createResponse);

        //Put new list name
        String newName = boardSteps.getRandomString();
        Response modifyResponse = ServiceObject
            .builder(creds)
            .setMethod(Method.PUT)
            .setName(newName)
            .addPathParam("ID", initList.getId())
            .addPathParam("resource", Resources.LIST_RESOURCE)
            .buildRequest()
            .sendRequest(Resources.RESOURCE_ID, DEFAULT_SPEC);
        listSteps.checkGoodResponse(modifyResponse);
        TrelloList newList = ServiceObject.jsonListToPojo(modifyResponse);

        assertThat("Checking put list name", newList.getName(), Matchers.equalTo(newName));
        assertThat("Checking lists boardID", newList.getIdBoard(), Matchers.equalTo(testBoard.getId()));
        onSiteListID = initList.getId();
    }

    @Test(dataProviderClass = TrelloDataProvider.class, dataProvider = "listData")
    public void checkListArchiving(TrelloList trelloList) {
        Response createResponse = listSteps
            .createListOnBoard(testBoard, creds);
        TrelloList initList = ServiceObject.jsonListToPojo(createResponse);

        Response archiveResponse = listSteps.deleteList(initList, RequestSpecifications.DEFAULT_SPEC, creds);
        listSteps.checkGoodResponse(archiveResponse);

        TrelloList archivedList = ServiceObject.jsonListToPojo(archiveResponse);
        assertThat("Checking put list name", archivedList.getName(), Matchers.equalTo(trelloList.getName()));
        assertThat("Checking lists boardID", archivedList.getIdBoard(), Matchers.equalTo(testBoard.getId()));
        assertThat("Checking lists status", archivedList.getClosed(), Matchers.equalTo(true));
    }
}
