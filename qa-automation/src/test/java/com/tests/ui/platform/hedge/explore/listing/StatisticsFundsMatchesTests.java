package com.tests.ui.platform.hedge.explore.listing;

import com.library.annotations.CoverageInfo;
import com.library.helpers.NumberHelper;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.enums.platform.RFR;
import com.tests.enums.platform.SinceInception;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.ProfilePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

import java.util.List;

public class StatisticsFundsMatchesTests extends HedgeMain
{
    @CoverageInfo(details = "Verify matches between table and profile return/risk stats")
    @Test
    public void statisticsFundsMatchesTest()
    {
        int rowToUse = 0;

        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);
        entityListingPage.selectSavedLayout("Fund performance");
        entityListingPage.selectFilters(List.of("Sharpe ratio", "Annualized returns", "Worst month", "Annualised standard deviation", "Maximum drawdown"));

        entityListingPage.selectOnSinceInception(SinceInception.last3months);
        entityListingPage.selectRfr(RFR.rfr5);

        String selectFund = entityListingPage.getInvestorName(rowToUse);
        Double sharpeRatio = NumberHelper.extractDouble(entityListingPage.getCellValues(rowToUse, "Sharpe ratio"));
        Double annualisedStandardDeviation = NumberHelper.extractDouble(entityListingPage.getCellValues(rowToUse, "Annualised standard deviation"));
        Double maximumDrawdown = NumberHelper.extractDouble(entityListingPage.getCellValues(rowToUse, "Maximum drawdown"));

        ProfilePage profilePage = entityListingPage.clickOnRowByName(selectFund);
        profilePage.selectSinceInception(SinceInception.last3months);
        profilePage.selectRfrPercentages(RFR.rfr5);
        profilePage.verifyValuesInRiskTable(sharpeRatio, annualisedStandardDeviation, maximumDrawdown);
    }
}
