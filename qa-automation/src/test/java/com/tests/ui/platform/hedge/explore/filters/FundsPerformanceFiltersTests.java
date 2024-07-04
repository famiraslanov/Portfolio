package com.tests.ui.platform.hedge.explore.filters;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.enums.platform.filters.HedgeFundPerformanceFilters;
import com.tests.helpers.platform.NavigationHelper;
import com.library.Log;
import com.library.annotations.CoverageInfo;
import com.library.helpers.Function;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import org.testng.annotations.Test;

public class FundsPerformanceFiltersTests extends HedgeMain
{
    @CoverageInfo(details = "Verify filters list for Funds")
    @Test
    public void fundsPerformanceFiltersTests()
    {
        ExplorePage explorePage = NavigationHelper.loginAndNavigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);
        entityListingPage.selectSavedLayout("Fund performance");
        Log.knownIssue("NODEV-889", Function.getStackTrace(), true);
        entityListingPage.selectFilters(HedgeFundPerformanceFilters.getAllFilters());
        entityListingPage.verifyThatAllFiltersAreDisplayedAsDefault(HedgeFundPerformanceFilters.getAllFilters());
    }
}
