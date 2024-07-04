package com.tests.helpers.platform;

import com.tests.enums.platform.AssetClass;
import com.tests.pageobjects.platform.PlatformArticlePage;
import com.tests.pageobjects.platform.ExplorePage;

public class ReportsAvailabilityHelper
{
    public static void runner(AssetClass assetClass)
    {
        ExplorePage explorePage = NavigationHelper.loginAndNavigateToExplore(assetClass);
        explorePage.verifyReportsAreSortedByDateDesc();

        String reportName = explorePage.getReportName(0);
        PlatformArticlePage reportPage = explorePage.clickOnReport(reportName);
        reportPage.verifyThatTitleIsCorrect(reportName);
    }
}
