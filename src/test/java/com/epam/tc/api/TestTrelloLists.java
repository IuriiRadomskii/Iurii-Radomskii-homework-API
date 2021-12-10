package com.epam.tc.api;

import static com.epam.tc.api.specs.RequestSpecifications.DEFAULT_SPEC;
import static com.epam.tc.api.specs.ResponseSpecs.GOOD_RESPONSE;
import static org.hamcrest.MatcherAssert.assertThat;

import com.epam.tc.api.data.TrelloDataProvider;
import com.epam.tc.api.entities.Board;
import com.epam.tc.api.entities.TrelloList;
import com.epam.tc.api.service.ServiceObject;
import com.epam.tc.api.specs.RequestSpecifications;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestTrelloLists extends BaseTest {

    private static Board testBoard;

    @BeforeClass
    public void createTestBoard() {
        testBoard = ServiceObject
            .jsonBoardToPojo(boardSteps
                .createBoard("testBoard", RequestSpecifications.DEFAULT_SPEC, creds));
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

    @Test(dataProviderClass = TrelloDataProvider.class, dataProvider = "listData")
    public void checkListPosting(TrelloList trelloList) {
        Response createResponse = listSteps
            .createList(trelloList.getName(), testBoard, RequestSpecifications.DEFAULT_SPEC, creds);
        listSteps.checkResponse(createResponse, GOOD_RESPONSE);

        TrelloList initList = ServiceObject.jsonListToPojo(createResponse);
        assertThat("Checking initial list name", initList.getName(), Matchers.equalTo(trelloList.getName()));
        assertThat("Checking lists boardID", initList.getIdBoard(), Matchers.equalTo(testBoard.getId()));
        onSiteListID = initList.getId();

    }

    @Test(dataProviderClass = TrelloDataProvider.class, dataProvider = "listData")
    public void checkListModifying(TrelloList trelloList) {
        Response createResponse = listSteps
            .createList(trelloList.getName(), testBoard, RequestSpecifications.DEFAULT_SPEC, creds);
        TrelloList initList = ServiceObject.jsonListToPojo(createResponse);

        initList.setName("newName");
        Response modifyResponse = listSteps.putListName(initList, RequestSpecifications.DEFAULT_SPEC, creds);
        listSteps.checkResponse(modifyResponse, GOOD_RESPONSE);
        TrelloList modifiedList = ServiceObject.jsonListToPojo(modifyResponse);

        assertThat("Checking put list name", modifiedList.getName(), Matchers.equalTo("newName"));
        assertThat("Checking lists boardID", modifiedList.getIdBoard(), Matchers.equalTo(testBoard.getId()));
        onSiteListID = initList.getId();
    }

    @Test(dataProviderClass = TrelloDataProvider.class, dataProvider = "listData")
    public void checkListArchiving(TrelloList trelloList) {
        Response createResponse = listSteps
            .createList(trelloList.getName(), testBoard, RequestSpecifications.DEFAULT_SPEC, creds);
        TrelloList initList = ServiceObject.jsonListToPojo(createResponse);

        Response archiveResponse = listSteps.deleteList(initList, RequestSpecifications.DEFAULT_SPEC, creds);
        listSteps.checkResponse(archiveResponse, GOOD_RESPONSE);

        TrelloList archivedList = ServiceObject.jsonListToPojo(archiveResponse);
        assertThat("Checking put list name", archivedList.getName(), Matchers.equalTo(trelloList.getName()));
        assertThat("Checking lists boardID", archivedList.getIdBoard(), Matchers.equalTo(testBoard.getId()));
        assertThat("Checking lists status", archivedList.getClosed(), Matchers.equalTo(true));
        onSiteListID = initList.getId();
    }
}
