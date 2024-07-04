package com.tests.ui.platform.hedge.profile;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.helpers.platform.NavigationHelper;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.forms.components.ProfileTableComponent;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.ProfilePage;
import org.testng.annotations.Test;

import java.util.List;

public class SortingInternalTableTests extends HedgeMain
{

    @CoverageInfo(details = "Verify sorting for table in profile page")
    @Test
    public void sortingInternalTableTest()
    {
        ExplorePage explorePage = NavigationHelper.loginAndNavigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);
        entityListingPage.selectFilters(List.of("Investment region"));
        entityListingPage.applyNewFilter("Investment region");
        ProfilePage profilePage = entityListingPage.getInvestorWithBlock(List.of("Known Investors"));

        profilePage.clickOnSectionLink("Known Investors");
        ProfileTableComponent profileTableComponent = new ProfileTableComponent("Known Investors");
        profileTableComponent.clickOnColumnToSort("Investor name");
        profileTableComponent.verifyThatColumnIsSorted(1, false);
        profileTableComponent.clickOnColumnToSort("Investor name");
        profileTableComponent.verifyThatColumnIsSorted(1, true);
    }
}
