package com.tests.helpers.platform;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;

import java.util.List;

public class FilterHelper
{
    public static void verifyFilterIsDisplayed(AssetClass assetClass, EntityCard entityCard, List<String> expectedFilters)
    {
        ExplorePage explorePage = NavigationHelper.loginAndNavigateToExplore(assetClass);
        EntityListingPage entityListingPage = null;
        if (!entityCard.equals(EntityCard.documentLibrary)) {
            entityListingPage = explorePage.navigateToEntityListingPage(entityCard);
            entityListingPage.selectFilters(expectedFilters);
        } else {
            entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.investors);
            entityListingPage.clickOnEntityTab(EntityCard.documentLibrary);
        }
        entityListingPage.verifyThatAllFiltersAreDisplayedAsDefault(expectedFilters);
    }
}
