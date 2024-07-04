package com.tests.ui.platform.hedge.explore.listing;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.ColumnName;
import com.tests.enums.platform.DataSettings;
import com.tests.enums.platform.EntityCard;
import com.library.annotations.CoverageInfo;
import com.tests.helpers.platform.SearchApiHelper;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.forms.components.ProfileTableComponent;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.ProfilePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class FundYTDComplianceTests extends HedgeMain
{
    @CoverageInfo(details = "Verify compliance of уtd")
    @Test
    public void fundYTDComplianceTest()
    {
        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);
        entityListingPage.selectSavedLayout("Fund performance");
        entityListingPage.setDataSetting(DataSettings.masterShareclasses.dropdownOption);
        entityListingPage.clickOnColumnToSort(ColumnName.fundYTD);

        String fundName = entityListingPage.getInvestorName(0);
        ProfilePage profilePage = entityListingPage.clickOnRowByName(fundName);
        int fundId = profilePage.getId();
        String expectedFundYTD = profilePage.converYTDToString(SearchApiHelper.getYDT(fundId));

        profilePage.verifyInfoLabel("YTD", expectedFundYTD);

        ProfileTableComponent monthlyReturns = new ProfileTableComponent("Monthly returns");
        monthlyReturns.verifyThatCellHasValue(1, "YTD", expectedFundYTD);

        ProfileTableComponent relatedFunds = new ProfileTableComponent("Related Funds");
        relatedFunds.typeSearchQuery(fundName);
        relatedFunds.verifyThatCellHasValue(1, "YTD", expectedFundYTD);

        explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);
        entityListingPage.selectSavedLayout("Fund performance");
        entityListingPage.enterInSearchField(fundName, 1);
        entityListingPage.verifyThatCellContainsValue(0, "YTD", expectedFundYTD);
    }
}
