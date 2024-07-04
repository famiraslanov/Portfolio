package com.tests.ui.platform.hedge.explore.listing;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.library.Log;
import com.library.annotations.CoverageInfo;
import com.library.helpers.Function;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

import java.util.List;

public class ResetLayoutTests extends HedgeMain
{
    @CoverageInfo(details = "Verify reset layout")
    @Test
    public void resetLayoutTest()
    {
        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.mandates);

        List<String> nonTickedSavedLayouts = entityListingPage.getSavedLayout();
        entityListingPage.selectSavedLayout(nonTickedSavedLayouts.get(1));
        entityListingPage.resetLayout();
        entityListingPage.verifyThatFilterReset();

        entityListingPage.selectFilters(List.of("Primary strategy"));
        entityListingPage.applyNewFilter("Primary strategy");

        entityListingPage.resetLayout();
        entityListingPage.verifyThatFilterReset();
    }
}
