package com.tests.ui.egr.digital;

import com.library.annotations.CoverageInfo;
import com.tests.enums.egr.Navigation;
import com.tests.helpers.egr.NavigationHelper;
import com.tests.pageobjects.baseobjects.EGRMain;
import com.tests.pageobjects.egr.EgrArticlePage;
import com.tests.pageobjects.egr.CloudBookPage;
import com.tests.pageobjects.egr.DigitalEditionsPage;
import org.testng.annotations.Test;

public class DigitalArticleTests extends EGRMain
{
    @CoverageInfo(details = "Verify that digital article are displayed")
    @Test
    public void digitalArticleTests()
    {
        navigateAndLogin();
        NavigationHelper.navigateTo(Navigation.digitalEditions);
        DigitalEditionsPage digitalEditionsPage = new DigitalEditionsPage();
        digitalEditionsPage.verifyThatPageContainsArticle();

        EgrArticlePage egrArticlePage = digitalEditionsPage.goToDigitalArticle();
        CloudBookPage cloudBookPage = egrArticlePage.clickOnViewDigitalEditionButton();
        cloudBookPage.verifyOpeningCloudBook();
    }
}
