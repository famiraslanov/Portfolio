package com.tests.ui.platform.hedge.indices;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.library.annotations.CoverageInfo;
import com.library.helpers.DateHelper;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class ViewCustomIndexTests extends HedgeMain
{
    @CoverageInfo(details = "View custom index")
    @Test
    public void viewCustomIndexTest()
    {
        AssetClass assetClass = AssetClass.hedge;
        String newIndexName = "CustomIndex_" + DateHelper.dtInsert();

        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(assetClass);

        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);
        entityListingPage.createDefaultSaveList(newIndexName, true);

        startingPage.navigateToExplore(assetClass);
        startingPage.checkForSavedList(newIndexName);

        explorePage = startingPage.navigateToExplore(assetClass);

        explorePage.verifyIndexCarouselContainsIndex(newIndexName);
        explorePage.clickOnIndexTicker(newIndexName);

        explorePage = startingPage.navigateToExplore(assetClass);
        explorePage.deleteSavedList(newIndexName);
    }
}
