package com.tests.ui.platform.hedge.datasettings;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.DataSettings;
import com.tests.enums.platform.EntityCard;
import com.tests.helpers.platform.ListingHelper;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import org.testng.annotations.Test;

public class AddDataSettingsSingleManagerTests extends HedgeMain
{
    @CoverageInfo(details = "Verify set data settings Single Manager")
    @Test
    public void addDataSettingsSingleManagerTest()
    {
        ListingHelper.verifyDataSettingsWithExternalTypeCondition(
                AssetClass.hedge,
                EntityCard.funds,
                DataSettings.singleManager
        );
    }
}
