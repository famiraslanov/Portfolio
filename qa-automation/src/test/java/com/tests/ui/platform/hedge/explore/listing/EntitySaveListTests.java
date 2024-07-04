package com.tests.ui.platform.hedge.explore.listing;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.library.annotations.CoverageInfo;
import com.library.helpers.DateHelper;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class EntitySaveListTests extends HedgeMain
{
    @CoverageInfo(details = "Verify investor entity create saved lists")
    @Test
    public void entityListingInvestorSavedListsTest()
    {
        AssetClass assetClass = AssetClass.hedge;

        String newListName = "NewList" + DateHelper.dtInsert();

        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(assetClass);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.investors);
        entityListingPage.createDefaultSaveList(newListName, false);

        startingPage.navigateToExplore(assetClass);
        startingPage.checkForSavedList(newListName);
        explorePage.deleteSavedList(newListName);
    }
}
