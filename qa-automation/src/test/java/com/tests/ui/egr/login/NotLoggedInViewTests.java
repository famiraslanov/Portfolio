package com.tests.ui.egr.login;

import com.tests.pageobjects.egr.EgrArticlePage;
import com.tests.pageobjects.egr.HomePage;
import com.tests.pageobjects.baseobjects.EGRMain;
import com.library.annotations.CoverageInfo;
import org.testng.annotations.Test;

public class NotLoggedInViewTests extends EGRMain
{
    @CoverageInfo(details = "Verify that user cannot view article without login")
    @Test
    public void notLoggedInViewTests()
    {
        HomePage home = navToStart();
        EgrArticlePage egrArticlePage = home.goToFistArticle();
        egrArticlePage.verifyViewLoginForm();
    }
}
