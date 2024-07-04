package com.tests.ui.egr.intel;

import com.library.Log;
import com.library.annotations.CoverageInfo;
import com.library.helpers.Function;
import com.tests.enums.egr.Region;
import com.tests.pageobjects.baseobjects.EGRMain;
import com.tests.pageobjects.egr.HomePage;
import org.testng.annotations.Test;

public class HomeArticleTests extends EGRMain
{

    @CoverageInfo(details = "Verify that home page has article")
    @Test
    public void homeArticleTests()
    {
        HomePage home = navigateAndLogin();
        Log.knownIssue("ENG-1465", Function.getStackTrace());
        home.verifyWidthOfColumns();
        home.selectRegion(Region.northAmerica);
        home.verifyThatPageContainsArticle();
        home.verifyWidthOfColumns();
        home.verifyThatAllCategoryTagsContainsArticle();
    }
}
