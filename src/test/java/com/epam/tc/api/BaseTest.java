package com.epam.tc.api;

import com.epam.tc.api.steps.BoardSteps;
import com.epam.tc.api.steps.ListSteps;
import com.epam.tc.api.util.ApiKeysInit;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.AfterClass;

public class BaseTest {

    protected BoardSteps boardSteps = new BoardSteps();
    protected ListSteps listSteps = new ListSteps();
    Map<String, String> creds = new HashMap<>();

    protected String onSiteBoardID;
    protected String onSiteListID;

    @AfterClass
    public void deleteAllBoards() {
        boardSteps.deleteAllBoards(creds);
    }


    protected void setCreds() {
        creds.put("key", ApiKeysInit.getApiKey());
        creds.put("token", ApiKeysInit.getApiToken());
    }

}
