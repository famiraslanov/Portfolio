package com.tests.ui.platform.hedge.myaccount;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.library.annotations.CoverageInfo;
import com.library.helpers.DateHelper;
import com.library.helpers.Function;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class UpdateSavedListTests extends HedgeMain
{
    String newListName = "NewList" + DateHelper.dtInsert();
    @CoverageInfo(details = "Verify update saved list")
    @Test
    public void updateSavedListTests()
    {
        AssetClass assetClass = AssetClass.hedge;

        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(assetClass);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.managers);

        String selectManager1 = entityListingPage.getInvestorName(0);
        entityListingPage.selectRowCheckboxByName(selectManager1);
        entityListingPage.createNewSavedList(newListName, false);
        Function.slowEnvironmentWait(Duration.ofMillis(500));

        String selectManager2 = entityListingPage.getInvestorName(1);
        entityListingPage.selectRowCheckboxByName(selectManager2);
        entityListingPage.updateSaveList(List.of(newListName));
        Function.slowEnvironmentWait(Duration.ofMillis(500));

        explorePage = startingPage.navigateToExplore(assetClass);
        explorePage.selectSavedListTableFilter(EntityCard.managers);
        explorePage.clickOnSavedList(newListName);
        entityListingPage = new EntityListingPage(EntityCard.managers);
        entityListingPage.verifyThatEntityIsDisplayedOnListing(List.of(selectManager1, selectManager2));

        explorePage = startingPage.navigateToExplore(assetClass);
        explorePage.deleteSavedList(newListName);
    }
}
