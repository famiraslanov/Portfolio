package com.tests.ui.platform.hedge.explore.listing;

import com.tests.enums.platform.AlertFrequency;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.DataSettings;
import com.tests.enums.platform.EntityCard;
import com.tests.helpers.platform.ProfileSettingsHelper;
import com.library.annotations.CoverageInfo;
import com.library.helpers.DateHelper;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

import java.util.List;

public class DynamicFiltersRelationshipTests extends HedgeMain
{
    @CoverageInfo(details = "Verify dynamic filters/columns relationship functionality")
    @Test
    public void dynamicFiltersRelationshipTests()
    {
        String newSavedFilterName = "Automation-" + DateHelper.dtInsert();
        EntityCard entityCard = EntityCard.investors;

        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(entityCard);

        entityListingPage.setDataSetting(DataSettings.iAndP.dropdownOption);
        entityListingPage.selectFilters(List.of("Primary strategy", "Attribute"));
        entityListingPage.applyNewFilter("Primary strategy");
        entityListingPage.applyNewFilter("Attribute");
        entityListingPage.saveFiltersLayout(newSavedFilterName, AlertFrequency.immediately);

        entityListingPage.resetLayout();
        entityListingPage.verifyThatFilterReset();

        entityListingPage = explorePage.navigateToEntityListingPage(entityCard);

        entityListingPage.selectSavedLayout(newSavedFilterName);
        entityListingPage.verifyThatFilterSelected(newSavedFilterName);
        entityListingPage.verifyThatAllFiltersAreDisplayedAsDefault(List.of("Primary strategy", "Attribute"));
        entityListingPage.verifyThatColumnIsDisplayed(List.of("Primary strategy", "Attribute"));
        entityListingPage.verifyDataSettingsStatus(DataSettings.iAndP, true);

        ProfileSettingsHelper.deleteSavedLayout(newSavedFilterName, entityCard, AssetClass.hedge);
    }
}
