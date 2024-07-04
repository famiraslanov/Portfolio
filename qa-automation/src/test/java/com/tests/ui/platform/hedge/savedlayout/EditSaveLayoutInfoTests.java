package com.tests.ui.platform.hedge.savedlayout;

import com.library.annotations.CoverageInfo;
import com.library.helpers.DateHelper;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.forms.SavedLayoutsSettingForm;
import com.tests.pageobjects.platform.forms.TopNavigationMenu;
import org.testng.annotations.Test;

import java.util.List;

public class EditSaveLayoutInfoTests extends HedgeMain
{
    @CoverageInfo(details = "Verify edit saved layout from ")
    @Test
    public void editSaveLayoutInfoTest()
    {
        AssetClass assetClass = AssetClass.hedge;
        String newSavedFilterName = "Autotest_" + DateHelper.dtInsert();
        String originalSavedLayoutName;

        navToStart();
        SavedLayoutsSettingForm savedLayoutsSettingForm = TopNavigationMenu.getInstance().navigateToSavedLayouts(assetClass);
        savedLayoutsSettingForm.clickOnEntityToggle(EntityCard.funds);
        List<String> savedLayouts = savedLayoutsSettingForm.getSavedLayout();
        originalSavedLayoutName = savedLayouts.get(0);
        savedLayoutsSettingForm.editLayout(originalSavedLayoutName, newSavedFilterName);
        savedLayoutsSettingForm.verifyDisplayedLayout(newSavedFilterName);
        savedLayoutsSettingForm.editLayout(newSavedFilterName, originalSavedLayoutName);
    }
}
