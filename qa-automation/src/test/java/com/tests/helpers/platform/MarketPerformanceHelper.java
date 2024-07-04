package com.tests.helpers.platform;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.PrimaryNavigationItem;
import com.tests.enums.platform.SecondaryNavigationItemHedgeFund;
import com.tests.enums.platform.ThirdLevelNavigationItem;
import com.tests.pageobjects.platform.MarketsPage;
import com.tests.pageobjects.platform.StartingPage;

public class MarketPerformanceHelper
{
    public static void runner(AssetClass assetClass)
    {
        StartingPage startingPage = NavigationHelper.loginAndNavigateTo(assetClass);
        startingPage.primaryNavigationItem(PrimaryNavigationItem.now);
        startingPage.selectSecondaryNavigation(SecondaryNavigationItemHedgeFund.performanceAndMarkets);
        startingPage.selectThirdLevelNavigation(ThirdLevelNavigationItem.markets);

        MarketsPage marketsPage = new MarketsPage(assetClass);
        marketsPage.verifyAtLeast(10);
    }
}
