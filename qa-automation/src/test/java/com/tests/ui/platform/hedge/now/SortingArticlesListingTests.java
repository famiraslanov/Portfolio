package com.tests.ui.platform.hedge.now;

import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class SortingArticlesListingTests extends HedgeMain
{

    @CoverageInfo(details = "Verify that now listing is sorted by selected order")
    @Test
    public void sortingArticlesListingTests()
    {
        StartingPage startingPage = navToStart();
        startingPage.sortListing(true);
        startingPage.verifyThatArticlesAreSortedByDesc();
    }
}
