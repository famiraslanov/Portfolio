package com.tests.ui.platform.hedge.now;

import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.PlatformArticlePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

import java.util.List;

public class
FollowTagByMenuBoxTests extends HedgeMain
{
    @CoverageInfo(details = "Verify follow tag by menu box")
    @Test
    public void followTagByMenuBoxTest(){
        StartingPage startingPage = navToStart();
        PlatformArticlePage platformArticlePage = startingPage.goToFirstArticle();
        List<String> tags = platformArticlePage.getTags();
        String expectedTag = tags.get(0);
        platformArticlePage.unfollowTag(expectedTag);

        platformArticlePage.clickOnTagBox(expectedTag);
        platformArticlePage.verifyThatTagIsFollowed(expectedTag);

        platformArticlePage.clickOnTagBox(expectedTag);
        platformArticlePage.verifyThatTagIsUnfollowed(expectedTag);
    }
}
