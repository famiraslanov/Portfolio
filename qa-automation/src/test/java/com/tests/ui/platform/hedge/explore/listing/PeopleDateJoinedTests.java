package com.tests.ui.platform.hedge.explore.listing;

import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.helpers.DateHelper;
import com.library.helpers.FileHelper;
import com.library.helpers.SettingsHelper;
import com.tests.enums.platform.*;
import com.tests.helpers.platform.ProfileSettingsHelper;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.baseobjects.TraditionalMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;
import com.tests.pageobjects.platform.forms.SavedLayoutsSettingForm;
import org.testng.annotations.Test;

import java.util.List;

public class PeopleDateJoinedTests extends TraditionalMain
{

    @CoverageInfo(details = "Verify date joined column for people")
    @Test
    public void peopleDateJoinedTests()
    {
        String newSavedFilterName = "Automation-" + DateHelper.dtInsert();
        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.traditional);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.people);
        entityListingPage.selectFilters(List.of("Date joined"));
        entityListingPage.applyRangeFilter("Date joined", "Last 12 Months");
        entityListingPage.saveFiltersLayout(newSavedFilterName, AlertFrequency.daily);
        entityListingPage.resetLayout();
        entityListingPage.selectSavedLayout(newSavedFilterName);
        entityListingPage.verifyThatFilterValueIsSelected("Date joined", List.of("Last 12 Months"));

        ProfileSettingsHelper.deleteSavedLayout(newSavedFilterName, EntityCard.people, AssetClass.traditional);
    }
}
