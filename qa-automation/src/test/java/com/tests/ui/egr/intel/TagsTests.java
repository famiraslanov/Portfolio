package com.tests.ui.egr.intel;

import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.EGRMain;
import com.tests.pageobjects.egr.HomePage;
import org.testng.annotations.Test;

public class TagsTests extends EGRMain
{
    @CoverageInfo(details = "Verify that articles relevant selected category")
    @Test
    public void tagTest()
    {
        HomePage home = navigateAndLogin();
        home.verifyThatAllTagsIsDisplayed();
        home.verifyThatAllCategoryTagsContainsArticle();
    }
}
