package com.tests.ui.platform.hedge.profile;

import com.library.annotations.CoverageInfo;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.ProfilePage;
import com.tests.pageobjects.platform.StartingPage;
import com.tests.pageobjects.platform.forms.components.ProfileTableComponent;
import org.testng.annotations.Test;

import java.util.List;

public class CompareFundManagerAUMTests extends HedgeMain
{
    @CoverageInfo(details = "Verify equal of aum for fund and linked manager")
    @Test
    public void compareFundManagerAUMTest()
    {
        AssetClass assetClass = AssetClass.hedge;

        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(assetClass);

        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);
        ProfilePage fundProfile = entityListingPage.getInvestorWithBlock(List.of("Related funds"));

        ProfileTableComponent relateFundsTable = new ProfileTableComponent("Related funds");
        relateFundsTable.clickOnColumnToSort("AUM");
        String fundAum = relateFundsTable.getColumnValue(0, "AUM");
        String fundName = relateFundsTable.getColumnValue(0, "Name");

        fundProfile.clickOnLinkedProfileLink();
        ProfileTableComponent fundsTable = new ProfileTableComponent("Funds");
        fundsTable.typeSearchQuery(fundName);
        fundsTable.verifyThatCellHasValue(0, "AUM", fundAum);
    }
}
