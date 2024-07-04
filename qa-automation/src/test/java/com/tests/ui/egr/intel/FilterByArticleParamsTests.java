package com.tests.ui.egr.intel;

import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.EGRMain;
import com.tests.pageobjects.egr.HomePage;
import org.testng.annotations.Test;

public class FilterByArticleParamsTests extends EGRMain
{
    @CoverageInfo(details = "Verify that articles are filtered correctly according to the selected parameters")
    @Test
    public void filterByArticleContentTypeTests()
    {
        HomePage home = navigateAndLogin();
        home.selectFilter("Topics", "Legal");
        home.verifyThatAllArticleContainsSelectedTag("Legal");
    }
}

