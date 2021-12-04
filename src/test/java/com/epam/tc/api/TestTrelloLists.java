package com.epam.tc.api;

import static org.hamcrest.MatcherAssert.assertThat;

import com.epam.tc.api.entities.Board;
import com.epam.tc.api.entities.TrelloList;
import com.epam.tc.api.service.TrelloServiceObj;
import com.epam.tc.api.specs.ResponseSpecifications;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestTrelloLists {

    private static Board testBoard;

    @BeforeClass
    public void createBoard() {
        Response createResponse = TrelloServiceObj.createBoard("Board");
        testBoard = TrelloServiceObj.getBoard(createResponse);
    }

    @Test
    public void checkListPosting() {
        Response createResponse = TrelloServiceObj.createTrelloList("List", testBoard.getId());
        TrelloList initList = TrelloServiceObj.getTrelloList(createResponse);

        createResponse.then().assertThat().spec(ResponseSpecifications.goodResponse);
        assertThat("Checking initial list name", initList.getName(), Matchers.equalTo("List"));
        assertThat("Checking lists boardID", initList.getIdBoard(), Matchers.equalTo(testBoard.getId()));
    }

    @Test
    public void checkListModifying() {
        Response createResponse = TrelloServiceObj.createTrelloList("List", testBoard.getId());
        TrelloList initList = TrelloServiceObj.getTrelloList(createResponse);

        Response modifyResponse = TrelloServiceObj.putTrelloListName("FOO", testBoard.getId(), initList.getId());
        TrelloList modifiedList = TrelloServiceObj.getTrelloList(modifyResponse);

        modifyResponse.then().assertThat().spec(ResponseSpecifications.goodResponse);
        assertThat("Checking put list name", modifiedList.getName(), Matchers.equalTo("FOO"));
        assertThat("Checking lists boardID", modifiedList.getIdBoard(), Matchers.equalTo(testBoard.getId()));
    }

    @Test
    public void checkListArchiving() {
        Response createResponse = TrelloServiceObj.createTrelloList("List", testBoard.getId());
        TrelloList initList = TrelloServiceObj.getTrelloList(createResponse);

        Response modifyResponse = TrelloServiceObj.putTrelloListName("FOO", testBoard.getId(), initList.getId());
        TrelloList modifiedList = TrelloServiceObj.getTrelloList(modifyResponse);

        modifyResponse.then().assertThat().spec(ResponseSpecifications.goodResponse);
        assertThat("Checking put list name", modifiedList.getName(), Matchers.equalTo("FOO"));
        assertThat("Checking lists boardID", modifiedList.getIdBoard(), Matchers.equalTo(testBoard.getId()));
    }

}
