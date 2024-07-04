package com.tests.ui.egr.search;

import com.tests.pageobjects.egr.HomePage;
import com.tests.pageobjects.baseobjects.EGRMain;
import com.library.annotations.CoverageInfo;
import org.testng.annotations.Test;

public class SearchTests extends EGRMain
{
    @CoverageInfo(details = "Verify search by Jersey")
    @Test
    public void searchJerseyTest()
    {
        HomePage home = navigateAndLogin();
        home.searchByArticle("Jersey");
        home.verifyThatSearchResultIsRelevant("Jersey");
    }

    @CoverageInfo(details = "Verify search by Slot")
    @Test
    public void searchSlotsTest()
    {
        HomePage home = navigateAndLogin();
        home.searchByArticle("Slots");
        home.verifyThatSearchResultIsRelevant("Slots");
    }

}
