package com.tests.ui.egr.intel;

import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.EGRMain;
import com.tests.pageobjects.egr.HomePage;
import org.testng.annotations.Test;

public class TagUponLocationTests extends EGRMain
{
    @CoverageInfo(details = "Verify that all article relevant selected location")
    @Test
    public void tagUponLocationTests()
    {
        HomePage home = navigateAndLogin();
        home.selectLocation("USA");
        home.verifyThatAllArticleContainsSelectedTag("USA");
    }
}
