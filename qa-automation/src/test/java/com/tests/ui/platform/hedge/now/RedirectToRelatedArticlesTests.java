package com.tests.ui.platform.hedge.now;

import com.library.annotations.CoverageInfo;
import com.tests.enums.platform.AssetClass;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.PlatformArticlePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class RedirectToRelatedArticlesTests extends HedgeMain
{
    @CoverageInfo(details = "Verify redirect to related article")
    @Test
    public void redirectToRelatedArticlesTests()
    {
        StartingPage startingPage = navToStart();
        startingPage.sortListing(false);
        PlatformArticlePage platformArticlePage = startingPage.getArticleWithRelatedLinks();
        String articleTitle = platformArticlePage.getAvailableRelatedArticles(AssetClass.hedge).get(0);
        PlatformArticlePage relatedArticle = platformArticlePage.clickOnRelatedArticleLink(articleTitle);
        relatedArticle.verifyThatTitleIsCorrect(articleTitle);
    }
}