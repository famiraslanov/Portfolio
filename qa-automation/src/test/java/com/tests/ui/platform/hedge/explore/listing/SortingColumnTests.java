package com.tests.ui.platform.hedge.explore.listing;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.ColumnName;
import com.tests.enums.platform.EntityCard;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class SortingColumnTests extends HedgeMain
{
    @CoverageInfo(details = "Verify sort column on entity listing")
    @Test
    public void sortingColumnTest()
    {
        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.mandates);

        ColumnName columnName = ColumnName.investorName;
        entityListingPage.clickOnColumnToSort(columnName);
        entityListingPage.verifyThatColumnIsSorted(columnName, false);

        entityListingPage.clickOnColumnToSort(columnName);
        entityListingPage.verifyThatColumnIsSorted(columnName, true);
    }
}
