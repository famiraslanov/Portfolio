package com.tests.ui.platform.hedge.explore.listing;

import com.library.annotations.CoverageInfo;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.DataSettings;
import com.tests.enums.platform.EntityCard;
import com.tests.helpers.platform.SearchApiHelper;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.ProfilePage;
import com.tests.pageobjects.platform.StartingPage;
import com.tests.pageobjects.platform.forms.RankToolForm;
import org.testng.annotations.Test;

import java.util.List;

public class ViewRankToolTests extends HedgeMain
{
    @CoverageInfo(details = "Verify get rank info for fund")
    @Test
    public void viewRankToolTests()
    {
        List<String> funds = SearchApiHelper.getEkhFunds(AssetClass.hedge);
        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);
        entityListingPage.setDataSetting(DataSettings.liquidatedFunds.dropdownOption);
        ProfilePage fundPage = entityListingPage.selectFundWithPerformanceFrom2018(funds);
        RankToolForm rankToolForm = fundPage.clickOnRankButton();
        rankToolForm.verifyThatRankingToolIsOpened();
    }
}
