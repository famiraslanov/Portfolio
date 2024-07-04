package com.tests.ui.platform.hedge.now;

import com.library.annotations.CoverageInfo;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.PrimaryNavigationItem;
import com.tests.enums.platform.SecondaryNavigationItemHedgeFund;
import com.tests.enums.platform.ThirdLevelNavigationItem;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.SecondaryNowPage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class NoEmptyFiltersTests extends HedgeMain
{
    @CoverageInfo(details = "Verify that no empty filters in second navigation menu")
    @Test
    public void noEmptyFiltersTests()
    {
        AssetClass assetClass = AssetClass.hedge;
        StartingPage startingPage = navToStart();
        startingPage.primaryNavigationItem(PrimaryNavigationItem.now);
        startingPage.selectSecondaryNavigation(SecondaryNavigationItemHedgeFund.performanceAndMarkets);
        SecondaryNowPage secondaryNowPage = new SecondaryNowPage(assetClass);

        secondaryNowPage.verifyThatNoEmptyFilters();
        startingPage.selectThirdLevelNavigation(ThirdLevelNavigationItem.shorting);
        secondaryNowPage.verifyThatNoEmptyFilters();
    }
}
