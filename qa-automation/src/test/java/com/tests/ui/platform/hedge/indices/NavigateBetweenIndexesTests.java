package com.tests.ui.platform.hedge.indices;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.IndicesFilter;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class NavigateBetweenIndexesTests extends HedgeMain
{
    @CoverageInfo(details = "Verify user is able to navigate between Indexes")
    @Test
    public void navigateBetweenIndexesTest()
    {
        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);

        explorePage.selectIndexFilter(IndicesFilter.lastYear);
        explorePage.selectIndexFilter(IndicesFilter.YTD);
        explorePage.selectIndexFilter(IndicesFilter.rolling3year);

        explorePage.clickOnRightScrollIndex();
        explorePage.clickOnLeftScrollIndex();

        explorePage.clickOnIndexTicker(explorePage.fetchTickerIndiciesName(1));
    }
}
