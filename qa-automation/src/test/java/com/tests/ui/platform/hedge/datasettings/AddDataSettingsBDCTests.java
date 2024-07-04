package com.tests.ui.platform.hedge.datasettings;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.DataSettings;
import com.tests.enums.platform.EntityCard;
import com.tests.helpers.platform.ListingHelper;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import org.testng.annotations.Test;

public class AddDataSettingsBDCTests extends HedgeMain
{
    @CoverageInfo(details = "Verify set data settings BDC")
    @Test
    public void addDataSettingsBDCTest()
    {
        ListingHelper.verifyDataSettingsIsApplied(
                AssetClass.hedge,
                EntityCard.managers,
                DataSettings.BDC);
    }
}
