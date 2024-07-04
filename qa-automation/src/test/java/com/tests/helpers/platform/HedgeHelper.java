package com.tests.helpers.platform;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.library.Verify;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.forms.SearchExploreForm;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;

public class HedgeHelper
{
    public static void searchOnExplore(String searchTerm)
    {
        ExplorePage explorePage = NavigationHelper.loginAndNavigateToExplore(AssetClass.hedge);

        explorePage.enterIntoSearchField(searchTerm);
        SearchExploreForm searchExploreForm = new SearchExploreForm();
        searchExploreForm.verifySearchRelevance(searchTerm);
    }

    public static void compareNumberOfEntityTest(HedgeMain hedgeMain, EntityCard entityName)
    {
        StartingPage startingPage = hedgeMain.navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);

        int numberOnEntityOnDashboard = explorePage.getEntityCountFromTab(entityName);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(entityName);

        int numberOnEntityOnListing = entityListingPage.getEntityCountListing();
        int tolerance = 3;
        Verify.isWithin(
                numberOnEntityOnDashboard,
                numberOnEntityOnListing,
                tolerance,
                "Number of entities within tolerance of " + tolerance + " Expected: " + numberOnEntityOnDashboard + " Found: " + numberOnEntityOnListing
        );
    }
}
