package com.tests.helpers.platform;

import com.tests.classes.FilterObject;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.pageobjects.baseobjects.AllocateMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.ProfilePage;
import com.tests.pageobjects.platform.StartingPage;
import com.tests.pageobjects.platform.forms.SearchExploreForm;

import java.util.List;

public class AllocateHelper
{
    public static void checkTableHidden(AllocateMain allocateMain, EntityCard entityCard)
    {
        StartingPage startingPage = allocateMain.navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.allocate);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(entityCard);
        entityListingPage.verifyThatTabIsHidden();
    }

    public static void verifySectionsHidden(AllocateMain allocateMain, List<String> excludeSectionsList, EntityCard entityCard, FilterObject filter)
    {
        StartingPage startingPage = allocateMain.navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.allocate);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(entityCard);
        entityListingPage.applyFilter(filter.getName(), filter.getIndexToSelect());

        String investorNameFirst = entityListingPage.getInvestorName(0);
        ProfilePage profileInvestorPage = entityListingPage.clickOnRowByName(investorNameFirst);

        for (String s : excludeSectionsList) {
            profileInvestorPage.sectionIsHiddenToView(s);
        }
        profileInvestorPage.verifyThatExportButtonIsNotDisplayed();
    }

    public static void verifyFilterIsDisplayed(AllocateMain allocateMain, EntityCard entityCard, List<String> expectedFilters)
    {
        StartingPage startingPage = allocateMain.navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.allocate);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(entityCard);
        if (entityCard.equals(EntityCard.managers) || entityCard.equals(EntityCard.funds)) {
            entityListingPage.selectFilters(expectedFilters);
        }
        entityListingPage.verifyThatAllFiltersAreDisplayedAsDefault(expectedFilters);
    }

    public static void verifySearchIsUnavailable(AllocateMain allocateMain, EntityCard entityCard, String searchTerm)
    {
        StartingPage startingPage = allocateMain.navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.allocate);

        explorePage.enterIntoSearchField(searchTerm);
        SearchExploreForm searchExploreForm = new SearchExploreForm();
        searchExploreForm.clickOnSearchTab(entityCard);
        searchExploreForm.verifyThatResultsIsUnavailable(EntityCard.consultants);
    }
}
