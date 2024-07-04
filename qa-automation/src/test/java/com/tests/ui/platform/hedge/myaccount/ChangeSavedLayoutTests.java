package com.tests.ui.platform.hedge.myaccount;

import com.tests.enums.platform.AlertFrequency;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.helpers.platform.ProfileSettingsHelper;
import com.library.annotations.CoverageInfo;
import com.library.helpers.DateHelper;
import com.library.helpers.Function;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

import java.util.List;

public class ChangeSavedLayoutTests extends HedgeMain
{
    String savedFilterName = "Autotest_" + DateHelper.dtInsert();
    String newSavedFilterName = "NewAuto_" + DateHelper.dtInsert();

    @CoverageInfo(details = "Verify apply filter from Explore dashboard")
    @Test
    public void changeSavedLayoutTest()
    {
        AssetClass assetClass = AssetClass.hedge;
        EntityCard entityCard = EntityCard.consultants;

        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(assetClass);

        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(entityCard);
        entityListingPage.selectFilters(List.of("Consultant location"));
        entityListingPage.applyNewFilter("Consultant location");
        entityListingPage.saveFiltersLayout(savedFilterName, AlertFrequency.immediately);
        Function.slowEnvironmentWait();
        ExplorePage newExplorePage = startingPage.navigateToExplore(assetClass);
        newExplorePage.selectSavedLayoutTableFilter(entityCard);
        newExplorePage.editSavedLayout(entityCard, savedFilterName, newSavedFilterName, AlertFrequency.daily);
        newExplorePage.verifyThatSavedLayoutContainedInList(entityCard, newSavedFilterName, AlertFrequency.daily);

        ProfileSettingsHelper.deleteSavedLayout(newSavedFilterName, entityCard, assetClass);
    }
}
