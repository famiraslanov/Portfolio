package com.tests.ui.platform.hedge.now;

import com.tests.enums.platform.AssetClass;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.forms.TopNavigationMenu;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class LogoRedirectToNowTests extends HedgeMain
{
    @CoverageInfo(details = "Verify that website logo leads to Now tab")
    @Test
    public void logoRedirectToNowTest()
    {
        StartingPage startingPage = navToStart();
        startingPage.navigateToExplore(AssetClass.hedge);
        startingPage = TopNavigationMenu.getInstance().clickOnLogo(AssetClass.hedge);
        startingPage.verifyThatArticlesAreDisplayed();
    }
}
