package com.tests.ui.platform.hedge.explore.listing;

import com.library.Store;
import com.library.annotations.CoverageInfo;
import com.tests.enums.platform.AlertFrequency;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.enums.platform.UserLogin;
import com.tests.helpers.platform.NavigationHelper;
import com.tests.helpers.platform.ProfileSettingsHelper;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.forms.SavedLayoutsSettingForm;
import com.tests.pageobjects.platform.forms.TopNavigationMenu;
import org.testng.annotations.Test;

import java.util.List;

public class CopyNotSavedLayoutTests extends HedgeMain
{
    @CoverageInfo(details = "Verify copy mot saved layout")
    @Test
    public void copyNotSavedLayoutTests()
    {
        String newSavedFilterName = "Autotest_NotSavedLayout";
        AssetClass assetClass = AssetClass.hedge;
        EntityCard entityCard = EntityCard.funds;
        String filterName = "Primary strategy";

        NavigationHelper.loginAndNavigateToExplore(assetClass, Store.getUserLogin());
        ProfileSettingsHelper.deleteSavedLayoutIfExist(newSavedFilterName, EntityCard.funds, AssetClass.hedge);
        TopNavigationMenu.getInstance().logOut();

        ExplorePage explorePage = NavigationHelper.loginAndNavigateToExplore(assetClass, UserLogin.admin);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(entityCard);
        entityListingPage.selectFilters(List.of(filterName));
        String optionText = entityListingPage.applyNewFilter(filterName);
        entityListingPage.copyFiltersLayout(newSavedFilterName, AlertFrequency.daily, Store.getUserLogin());

        TopNavigationMenu.getInstance().logOut();
        NavigationHelper.loginAndNavigateToExplore(assetClass, Store.getUserLogin());

        entityListingPage = explorePage.navigateToEntityListingPage(entityCard);
        entityListingPage.selectSavedLayout(newSavedFilterName);
        entityListingPage.verifyThatFilterValueIsSelected(filterName, List.of(optionText));

        SavedLayoutsSettingForm savedLayoutsSettingForm = TopNavigationMenu.getInstance().navigateToSavedLayouts(AssetClass.hedge);
        savedLayoutsSettingForm.clickOnEntityToggle(entityCard);
        savedLayoutsSettingForm.verifyDisplayedLayout(newSavedFilterName);

        ProfileSettingsHelper.deleteSavedLayout(newSavedFilterName, EntityCard.funds, AssetClass.hedge);
    }
}
