package com.tests.ui.platform.hedge.explore.listing;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.enums.platform.PrimaryNavigationItem;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.forms.CompareFindsForm;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.IndexPage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class CompareByIndexTests extends HedgeMain
{
    @CoverageInfo(details = "Verify compare by index")
    @Test
    public void compareByIndexTests()
    {
        StartingPage startingPage = navToStart();
        startingPage.primaryNavigationItem(PrimaryNavigationItem.indices);
        IndexPage indexPage = new IndexPage();
        String index = indexPage.getFirstIndustryIndicesName();

        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);
        String selectFund1 = entityListingPage.getInvestorName(1);
        entityListingPage.selectRowCheckboxByName(selectFund1);
        List<String> expectedFundList = new ArrayList<>(List.of(selectFund1, index));

        CompareFindsForm compareFindsForm = entityListingPage.clickCompareFinds();
        compareFindsForm.addIndex(index);
        compareFindsForm.verifyThatFindsSelected(expectedFundList);
        compareFindsForm.verifyThatStatisticsRowIsDisplayed(expectedFundList);
    }
}
