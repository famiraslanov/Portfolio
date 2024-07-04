package com.tests.ui.platform.hedge.indices;

import com.tests.enums.platform.PrimaryNavigationItem;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.IndexPage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class ChangeMonthlyReturnsViewTests extends HedgeMain
{
    @CoverageInfo(details = "Verify change Monthly Returns View table")
    @Test
    public void changeMonthlyReturnsViewTest()
    {
        StartingPage startingPage = navToStart();
        startingPage.primaryNavigationItem(PrimaryNavigationItem.indices);
        IndexPage indexPage = new IndexPage();
        indexPage.verifyThatMonthlyReturnsIsDisplayed();

        indexPage.changeMonthlyReturnsView();
        indexPage.verifyThatMonthlyReturnsViewIsChart();

        indexPage.changeMonthlyReturnsView();
        indexPage.verifyThatMonthlyReturnsViewIsTable();
    }
}
