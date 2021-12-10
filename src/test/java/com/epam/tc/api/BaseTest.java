package com.epam.tc.api;

import com.epam.tc.api.steps.BoardSteps;
import com.epam.tc.api.steps.ListSteps;
import com.epam.tc.api.util.ApiKeysInit;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected BoardSteps boardSteps = new BoardSteps();
    protected ListSteps listSteps = new ListSteps();
    Map<String, String> creds = new HashMap<>();

    protected String onSiteBoardID;
    protected String onSiteListID;

    @BeforeClass
    public void setup() {
        setCreds();
    }

    protected void setCreds() {
        creds.put("key", ApiKeysInit.getApiKey());
        creds.put("token", ApiKeysInit.getApiToken());
    }

}
