package com.tests.ui.platform.hedge.explore.listing;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.library.Log;
import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Objects;

public class SwitchingLayoutsTests extends HedgeMain
{
    @CoverageInfo(details = "Verify switch between layouts")
    @Test
    public void switchingLayoutsTest()
    {
        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.managers);

        List<String> layouts = entityListingPage.getSavedLayout();
        entityListingPage.selectSavedLayout(layouts.get(0));
        String savedLayoutSettings1 = entityListingPage.getSavedLayoutListOfParams();

        entityListingPage.selectSavedLayout(layouts.get(1));
        String savedLayoutSettings2 = entityListingPage.getSavedLayoutListOfParams();

        Log.object("savedLayout1", savedLayoutSettings1);
        Log.object("savedLayout2", savedLayoutSettings2);
        Verify.isTrue(!Objects.equals(savedLayoutSettings2, savedLayoutSettings1), " Saved layer is not switched! ");
    }
}
