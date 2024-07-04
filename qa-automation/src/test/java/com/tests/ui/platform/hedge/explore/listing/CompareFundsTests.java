package com.tests.ui.platform.hedge.explore.listing;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.ColumnName;
import com.tests.enums.platform.DataSettings;
import com.tests.enums.platform.EntityCard;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.forms.CompareFindsForm;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class CompareFundsTests extends HedgeMain
{
    @CoverageInfo(details = "Verify compare finds")
    @Test
    public void compareFundsTest()
    {
        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);
        entityListingPage.setDataSetting(DataSettings.activelyReporting.dropdownOption);

        String selectFund1 = entityListingPage.getInvestorName(0);
        String selectFund2 = entityListingPage.getInvestorName(1);
        String selectFund3 = entityListingPage.getInvestorName(2);
        entityListingPage.selectRowCheckboxByName(selectFund1);
        entityListingPage.selectRowCheckboxByName(selectFund2);

        List<String> expectedFundList = new ArrayList<>(List.of(selectFund1, selectFund2));
        CompareFindsForm compareFindsForm = entityListingPage.clickCompareFinds();

        compareFindsForm.verifyThatFindsSelected(expectedFundList);
        compareFindsForm.verifyInfoIsDisplayed(expectedFundList);

        compareFindsForm.addFund(selectFund3);
        expectedFundList.add(selectFund3);
        compareFindsForm.verifyThatFindsSelected(expectedFundList);
        compareFindsForm.verifyInfoIsDisplayed(expectedFundList);
        compareFindsForm.verifyThatStatisticsRowIsDisplayed(expectedFundList);
    }
}
