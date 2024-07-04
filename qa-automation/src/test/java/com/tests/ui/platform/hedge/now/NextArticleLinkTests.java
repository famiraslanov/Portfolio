package com.tests.ui.platform.hedge.now;

import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.PlatformArticlePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class NextArticleLinkTests extends HedgeMain
{
    @CoverageInfo(details = "Verify redirect to next and previous article")
    @Test
    public void nextAndPreviousArticleLinkTest()
    {
        StartingPage startingPage = navToStart();
        PlatformArticlePage platformArticlePage = startingPage.goToFirstArticle();

        String nextArticle = platformArticlePage.getNextArticleTitle();
        platformArticlePage.clickOnNextArticleLink();
        platformArticlePage.verifyThatTitleIsCorrect(nextArticle);

        String previousArticle = platformArticlePage.getPreviousArticleTitle();
        platformArticlePage.clickOnPreviousArticleLink();
        platformArticlePage.verifyThatTitleIsCorrect(previousArticle);
    }
}
