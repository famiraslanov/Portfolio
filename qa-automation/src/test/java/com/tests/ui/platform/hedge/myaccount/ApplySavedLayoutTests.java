package com.tests.ui.platform.hedge.myaccount;

import com.library.Store;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.library.annotations.CoverageInfo;
import com.tests.helpers.platform.NavigationHelper;
import com.tests.helpers.platform.ProfileSettingsHelper;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.forms.TopNavigationMenu;
import org.testng.annotations.Test;

import java.util.List;

public class ApplySavedLayoutTests extends HedgeMain
{
    @CoverageInfo(details = "Verify apply filter from Explore dashboard")
    @Test
    public void applySavedLayoutTest()
    {
        AssetClass assetClass = AssetClass.hedge;
        EntityCard entityCard = EntityCard.consultants;
        String newSavedFilterName = "Autotest_ApplyLayout";

        NavigationHelper.loginAndNavigateToExplore(assetClass, Store.getUserLogin());
        ProfileSettingsHelper.deleteSavedLayoutIfExist(newSavedFilterName, entityCard, assetClass);
        ExplorePage explorePage = TopNavigationMenu.getInstance().navigateToExplore(assetClass);

        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(entityCard);
        String filterValue = entityListingPage.setupLayouts("Consultant location", newSavedFilterName);

        entityListingPage.resetLayout();
        entityListingPage.selectSavedLayout(newSavedFilterName);
        entityListingPage.verifyThatFilterSelected(newSavedFilterName);

        ExplorePage newExplorePage = TopNavigationMenu.getInstance().navigateToExplore(assetClass);
        newExplorePage.selectSavedLayoutTableFilter(entityCard);

        newExplorePage.applyFilter(newSavedFilterName);
        EntityListingPage newEntityListingPage = new EntityListingPage(entityCard);
        newEntityListingPage.checkSavedLayoutIsApplied(newSavedFilterName);
        entityListingPage.verifyThatFilterValueIsSelected("Consultant location", List.of(filterValue));
    }
}
