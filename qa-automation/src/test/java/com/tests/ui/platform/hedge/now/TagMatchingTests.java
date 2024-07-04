package com.tests.ui.platform.hedge.now;

import com.library.annotations.CoverageInfo;
import com.tests.enums.platform.AssetClass;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.PlatformArticlePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

import java.util.List;

public class TagMatchingTests extends HedgeMain
{
    @CoverageInfo(details = "Verify that tags from now page is equal with Article page")
    @Test
    public void tagMatchingTest(){
        StartingPage startingPage = navToStart();
        String articleName = startingPage.getArticleWithTags(AssetClass.hedge);
        List<String> expectedTags = startingPage.getArticleTags(articleName);

        PlatformArticlePage platformArticlePage = startingPage.goToArticle(articleName);
        platformArticlePage.verifyMatchingTagsFromNowPage(expectedTags);
    }
}
