package com.tests.ui.platform.hedge.explore.listing;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

import java.util.List;

public class SearchByEntityTests extends HedgeMain
{
    @CoverageInfo(details = "Verify search by entity listing create")
    @Test
    public void searchByEntityTest()
    {
        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.mandates);

        String selectMandate = entityListingPage.getInvestorName(2);
        int currentSearchCount = entityListingPage.getEntityCountListing();

        entityListingPage.selectFilters(List.of("Manager name"));

        entityListingPage.enterInSearchField(selectMandate, currentSearchCount);
        int firstSearchCount = entityListingPage.getEntityCountListing();
        entityListingPage.verifySearchResults(selectMandate);

        entityListingPage.enterInSearchField("No results found", firstSearchCount);
        entityListingPage.verifyThatNoResultsFound();
    }
}
