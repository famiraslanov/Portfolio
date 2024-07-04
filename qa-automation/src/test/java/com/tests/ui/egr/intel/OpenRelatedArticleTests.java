package com.tests.ui.egr.intel;

import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.EGRMain;
import com.tests.pageobjects.egr.EgrArticlePage;
import com.tests.pageobjects.egr.HomePage;
import org.testng.annotations.Test;

public class OpenRelatedArticleTests extends EGRMain
{
    @CoverageInfo(details = "Verify open related article")
    @Test
    public void openRelatedArticleTests()
    {
        HomePage home = navigateAndLogin();
        home.verifyThatPageContainsArticle();
        EgrArticlePage egrArticlePage = home.goToFistArticle();
        egrArticlePage.goToFirstRelatedArticleLink();
        egrArticlePage.verifyContentIsDisplayed();
    }
}
