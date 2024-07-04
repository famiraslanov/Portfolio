package com.tests.ui.platform.hedge.profile;

import com.library.annotations.CoverageInfo;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.ProfilePage;
import com.tests.pageobjects.platform.StartingPage;
import com.tests.pageobjects.platform.forms.CompareFindsForm;
import com.tests.pageobjects.platform.forms.RankToolForm;
import org.testng.annotations.Test;

import java.util.List;

public class CompareAndRanksFundsTests extends HedgeMain
{
    @CoverageInfo(details = "Verify compare and rank functionality available for profile")
    @Test
    public void compareAndRanksFundsTest()
    {
        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);
        entityListingPage.selectSavedLayout("Fund performance");

        int selectFundIndex = entityListingPage.findFirstRankEnabledInvestorRowIndex();
        String selectFund = entityListingPage.getInvestorName(selectFundIndex);

        ProfilePage profilePage = entityListingPage.clickOnRowByName(selectFund);
        CompareFindsForm compareFindsForm = profilePage.clickOnCompareButton();
        compareFindsForm.verifyThatFindsSelected(List.of(selectFund));
        compareFindsForm.closeCompareForm();

        RankToolForm rankToolForm = profilePage.clickOnRankButton();
        rankToolForm.verifyThatRankingToolIsOpened();
    }
}
