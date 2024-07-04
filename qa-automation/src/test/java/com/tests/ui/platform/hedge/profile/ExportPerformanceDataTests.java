package com.tests.ui.platform.hedge.profile;

import com.library.Log;
import com.library.annotations.CoverageInfo;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.ProfilePage;
import com.tests.pageobjects.platform.StartingPage;
import com.tests.pageobjects.platform.forms.components.ProfileComponent;
import com.tests.pageobjects.platform.forms.components.ProfileTableComponent;
import org.testng.annotations.Test;

import java.util.List;

public class ExportPerformanceDataTests extends HedgeMain
{
    @CoverageInfo(details = "Verify performance data export")
    @Test
    public void exportPerformanceDataTests()
    {
        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);
        entityListingPage.selectSavedLayout("Fund performance");
        ProfilePage profilePage = entityListingPage.getInvestorWithBlock(List.of("Service providers", "Monthly net asset values (USD/m)"));
        int fundId = profilePage.getId();

        ProfileTableComponent monthlyReturns = new ProfileTableComponent("Monthly returns");
        monthlyReturns.clickOnExport();
        monthlyReturns.verifyThatFileDownload(String.format("%d_fund_monthly_returns", fundId), "csv");

        ProfileTableComponent monthlyNetAssetValues = new ProfileTableComponent("Monthly net asset values (USD/m)");
        monthlyNetAssetValues.clickOnExport();
        monthlyNetAssetValues.verifyThatFileDownload(String.format("%d_fund_navs", fundId), "csv");

        ProfileComponent serviceProviders = new ProfileComponent("Service providers");
        serviceProviders.clickOnExport();
        serviceProviders.verifyThatFileDownload(String.format("%d_fund_service_providers", fundId), "csv");
    }
}
