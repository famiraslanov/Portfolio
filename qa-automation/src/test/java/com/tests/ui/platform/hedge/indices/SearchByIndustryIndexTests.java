package com.tests.ui.platform.hedge.indices;

import com.tests.enums.platform.PrimaryNavigationItem;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.IndexPage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class SearchByIndustryIndexTests extends HedgeMain
{
    @CoverageInfo(details = "Verify search and switch between Industry indices on Indices Page")
    @Test
    public void searchByIndustryIndexTest()
    {
        StartingPage startingPage = navToStart();
        startingPage.primaryNavigationItem(PrimaryNavigationItem.indices);
        IndexPage indexPage = new IndexPage();

        String firstIndex = indexPage.getFirstIndustryIndicesName();

        indexPage.searchByIndex(firstIndex);

        IndexPage newIndexPage = indexPage.selectIndex(firstIndex);
        newIndexPage.verifyThatIndexStatsDataIsDisplayed();
        newIndexPage.verifyThatMonthlyReturnsIsDisplayed();
    }
}
