package com.tests.ui.platform.hedge.savedlayout;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.forms.SavedLayoutsSettingForm;
import com.tests.pageobjects.platform.forms.TopNavigationMenu;
import com.tests.pageobjects.platform.EntityListingPage;
import org.testng.annotations.Test;

import java.util.List;

public class ApplySavedListFromMyAccountTests extends HedgeMain
{

    @CoverageInfo(details = "Verify apply saved list from my account")
    @Test
    public void applySavedListFromMyAccountTest()
    {
        AssetClass assetClass = AssetClass.hedge;
        navToStart();
        SavedLayoutsSettingForm savedLayoutsSettingForm = TopNavigationMenu.getInstance().navigateToSavedLayouts(assetClass);
        savedLayoutsSettingForm.clickOnEntityToggle(EntityCard.managers);
        List<String> savedLayouts = savedLayoutsSettingForm.getSavedLayout();
        EntityListingPage mangerListing = savedLayoutsSettingForm.clickOnSavedLayout(savedLayouts.get(0), EntityCard.managers);

        mangerListing.verifyCorrectTabSelected();
        mangerListing.checkSavedLayoutIsApplied(savedLayouts.get(0));
    }
}
