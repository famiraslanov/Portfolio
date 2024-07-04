package com.tests.ui.egr.intel;

import com.tests.pageobjects.egr.HomePage;
import com.tests.pageobjects.baseobjects.EGRMain;
import com.library.annotations.CoverageInfo;
import org.testng.annotations.Test;

public class AdsTests extends EGRMain
{
    @CoverageInfo(details = "Verify view ad blocks")
    @Test
    public void adsTest()
    {
        HomePage home = navigateAndLogin();
        home.verifyThatAdsBlocksAreDisplayed();
    }
}
