package com.tests.ui.platform.hedge.now;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.PrimaryNavigationItem;
import com.tests.enums.platform.SecondaryNavigationItemHedgeFund;
import com.library.annotations.CoverageInfo;
import com.tests.helpers.platform.SearchApiHelper;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.ProfilePage;
import com.tests.pageobjects.platform.SecondaryNowPage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

import java.util.List;

public class NavigateToTrendingTests extends HedgeMain
{

    @CoverageInfo(details = "Verify navigate to Trending entity")
    @Test
    public void navigateToTrendingTests()
    {
        StartingPage startingPage = navToStart();
        startingPage.primaryNavigationItem(PrimaryNavigationItem.now);
        startingPage.selectSecondaryNavigation(SecondaryNavigationItemHedgeFund.performanceAndMarkets);

        SecondaryNowPage secondaryNowPage = new SecondaryNowPage(AssetClass.hedge);

        List<String> trendingLinks = SearchApiHelper.getTrendingEntity(AssetClass.hedge, "funds");
        secondaryNowPage.verifyThatCorrectTrendLinksAreDisplayed(trendingLinks);

        String title = secondaryNowPage.getFirstTrendingEntity();
        ProfilePage profilePage = secondaryNowPage.navigateToTrendingLink(title);
        profilePage.verifyThatSelectedProfileIsOpen(title);
    }
}
