package com.tests.ui.platform.hedge.explore.listing;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.forms.RankToolForm;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class AddToRankTests extends HedgeMain
{
    @CoverageInfo(details = "Verify get rank info for fund")
    @Test
    public void addToRankTest()
    {
        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);

        String selectFund1 = entityListingPage.getInvestorName(0);
        entityListingPage.selectRowCheckboxByName(selectFund1);
        RankToolForm rankToolForm = entityListingPage.clickOnRankButton();
        rankToolForm.verifyThatRankingToolIsOpened();
    }
}
