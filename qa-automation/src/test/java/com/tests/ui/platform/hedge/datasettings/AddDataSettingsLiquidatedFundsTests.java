package com.tests.ui.platform.hedge.datasettings;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.DataSettings;
import com.tests.enums.platform.EntityCard;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.ProfilePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class AddDataSettingsLiquidatedFundsTests extends HedgeMain
{
    @CoverageInfo(details = "Verify set data settings Liquidated Funds")
    @Test
    public void addDataSettingsLiquidatedFundsTests()
    {
        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);

        entityListingPage.setDataSetting(DataSettings.liquidatedFunds.dropdownOption);
        entityListingPage.setDataSetting(DataSettings.activeFunds.dropdownOption);
        String fundNameFirst = entityListingPage.getInvestorName(0);
        ProfilePage fundProfile = entityListingPage.clickOnRowByName(fundNameFirst);
        fundProfile.verifyTitleLabel(DataSettings.liquidatedFunds.label);
    }
}
