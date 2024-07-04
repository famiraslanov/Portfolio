package com.tests.api;

import com.library.annotations.CoverageInfo;
import com.library.helpers.ApiHelper;
import com.tests.pageobjects.baseobjects.ApiMain;
import org.testng.annotations.Test;

public class LogApiUrlsTests extends ApiMain
{
    @CoverageInfo(details = "This allows the API urls and tokens to be shared between Java code base and the QA Tools")
    @Test
    public void logApiUrlsTest()
    {
        // This will help keep the QATools bearer tokens fresh when they start expiring
        ApiHelper.writeAPIData();
    }
}
