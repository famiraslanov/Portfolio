package com.tests.helpers.platform;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.PrimaryNavigationItem;
import com.tests.enums.platform.RFR;
import com.tests.enums.platform.SinceInception;
import com.library.helpers.DateHelper;
import com.tests.pageobjects.platform.IndexPage;
import com.tests.pageobjects.platform.StartingPage;

import java.util.Date;

public class IndicesHelper
{
    public static IndexPage selectDateAndRiskFreeRate(AssetClass assetClass)
    {
        Date today = DateHelper.getTodayDate();
        Date prevDate = DateHelper.changeDate(today, -1, -1, 0);

        StartingPage startingPage = NavigationHelper.loginAndNavigateTo(assetClass);
        startingPage.primaryNavigationItem(PrimaryNavigationItem.indices);
        IndexPage indexPage = new IndexPage();
        indexPage.verifyThatIndexStatsDataIsDisplayed();

        indexPage.selectSinceInception(prevDate, today);
        indexPage.verifyThatIndexStatsDataIsDisplayed();

        indexPage.selectSinceInception(SinceInception.last4years);
        indexPage.verifyThatIndexStatsDataIsDisplayed();

        indexPage.selectRfrPercentages(RFR.rfr3);
        indexPage.verifyThatIndexStatsDataIsDisplayed();
        return indexPage;
    }
}
