package com.tests.ui.platform.hedge.explore.listing;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.helpers.platform.ProfileSettingsHelper;
import com.library.annotations.CoverageInfo;
import com.library.helpers.DateHelper;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class FilterListingTests extends HedgeMain
{
    @CoverageInfo(details = "Verify creation of a filter")
    @Test
    public void createFilterTest()
    {
        String newSavedFilterName = "Automation-" + DateHelper.dtInsert();

        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.investors);
        entityListingPage.setupLayouts("Primary strategy", newSavedFilterName);

        ProfileSettingsHelper.deleteSavedLayout(newSavedFilterName, EntityCard.investors, AssetClass.hedge);
    }
}
