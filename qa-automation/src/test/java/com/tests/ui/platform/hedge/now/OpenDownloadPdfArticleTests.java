package com.tests.ui.platform.hedge.now;

import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.PlatformArticlePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class OpenDownloadPdfArticleTests extends HedgeMain
{
    @CoverageInfo(details = "Verify that download pdf button opens print window")
    @Test
    public void openDownloadPdfArticleTests()
    {
        StartingPage startingPage = navToStart();
        PlatformArticlePage platformArticlePage = startingPage.goToFirstArticle();
        platformArticlePage.clickOnDownloadPdfButton();
    }
}
