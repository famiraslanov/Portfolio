package com.tests.ui.platform.hedge.now;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.PrimaryNavigationItem;
import com.tests.enums.platform.SecondaryNavigationItemHedgeFund;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.SecondaryNowPage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class FilterByArticlesTests extends HedgeMain
{
    @CoverageInfo(details = "Verify filter articles")
    @Test
    public void filterByArticlesTests()
    {
        AssetClass assetClass = AssetClass.hedge;
        StartingPage startingPage = navToStart();
        startingPage.primaryNavigationItem(PrimaryNavigationItem.now);
        startingPage.selectSecondaryNavigation(SecondaryNavigationItemHedgeFund.performanceAndMarkets);
        SecondaryNowPage secondaryNowPage = new SecondaryNowPage(assetClass);
        String option = secondaryNowPage.selectFilter("Primary Strategy");
        secondaryNowPage.verifyThatResultsAreRelevantToFilter(option);
    }
}
