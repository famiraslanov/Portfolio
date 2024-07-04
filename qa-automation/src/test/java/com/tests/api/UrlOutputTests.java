package com.tests.api;

import com.library.Log;
import com.library.annotations.CoverageInfo;
import com.tests.enums.api.ApiUrl;
import com.tests.pageobjects.baseobjects.ApiMain;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class UrlOutputTests extends ApiMain
{
    private final int lineLength = 90;

    @CoverageInfo(details = "Simple output of the Api URLs covered by the suite")
    @Test
    public void apiUrlDisplayTest()
    {
        System.out.println("*".repeat(lineLength));
        System.out.println("API Url Output only");
        System.out.println("*".repeat(lineLength));
        for (ApiUrl apiUrl : ApiUrl.values()) {
            toConsole(apiUrl);
            toLog(apiUrl);
        }
    }

    private void toConsole(ApiUrl apiUrl)
    {
        System.out.println("Service: " + apiUrl);
        System.out.println("Url: " + apiUrl.url);
        System.out.println("GraphQL: " + apiUrl.graphQl);
        System.out.println("AuthToken: " + apiUrl.authToken);
        System.out.println("_".repeat(lineLength));
        System.out.println();
    }

    private void toLog(ApiUrl apiUrl)
    {
        Map<String, String> simpleLog = new HashMap<>();
        simpleLog.put("Service: ", apiUrl.toString());
        simpleLog.put("Url: ", apiUrl.url);
        simpleLog.put("GraphQL: ", apiUrl.graphQl);
        simpleLog.put("AuthToken: ", apiUrl.authToken);

        Log.object(apiUrl.toString(), simpleLog);
    }
}
