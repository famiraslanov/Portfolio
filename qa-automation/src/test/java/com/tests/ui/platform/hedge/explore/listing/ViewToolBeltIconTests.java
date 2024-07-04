package com.tests.ui.platform.hedge.explore.listing;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.enums.platform.ToolbeltIcon;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class ViewToolBeltIconTests extends HedgeMain
{
    @CoverageInfo(details = "Verify view tool-belt icons and verify their behavior")
    @Test
    public void viewToolBeltIconTest(){

        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);
        entityListingPage.verifyThatToolbeltIconDisabled(ToolbeltIcon.addToList);
        entityListingPage.verifyThatToolbeltIconDisabled(ToolbeltIcon.rankButton);
        entityListingPage.verifyThatToolbeltIconDisabled(ToolbeltIcon.compareButton);

        String selectFund1 = entityListingPage.getInvestorName(1);
        String selectFund2 = entityListingPage.getInvestorName(2);

        entityListingPage.selectRowCheckboxByName(selectFund1);
        entityListingPage.verifyThatToolbeltIconEnabled(ToolbeltIcon.addToList);
        entityListingPage.verifyThatToolbeltIconEnabled(ToolbeltIcon.rankButton);
        entityListingPage.verifyThatToolbeltIconEnabled(ToolbeltIcon.compareButton);

        entityListingPage.selectRowCheckboxByName(selectFund2);
        entityListingPage.verifyThatToolbeltIconEnabled(ToolbeltIcon.addToList);
        entityListingPage.verifyThatToolbeltIconDisabled(ToolbeltIcon.rankButton);
        entityListingPage.verifyThatToolbeltIconEnabled(ToolbeltIcon.compareButton);
    }
}
