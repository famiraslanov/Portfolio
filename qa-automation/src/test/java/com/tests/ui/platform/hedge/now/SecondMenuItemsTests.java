package com.tests.ui.platform.hedge.now;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.SecondaryNavigationItemHedgeFund;
import com.tests.helpers.platform.NavigationHelper;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import org.testng.annotations.Test;

public class SecondMenuItemsTests extends HedgeMain
{
    @CoverageInfo(details = "Verify follow tag by menu box")
    @Test
    public void secondMenuItemsTests()
    {
        NavigationHelper.verifyThatAllSecondaryNavigationAreDisplayed(AssetClass.hedge, SecondaryNavigationItemHedgeFund.class);
    }
}
