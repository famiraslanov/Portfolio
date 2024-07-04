package com.tests.ui.platform.hedge.indices;

import com.tests.enums.platform.PrimaryNavigationItem;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.IndexPage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class ExportIndexViewTests extends HedgeMain
{
    @CoverageInfo(details = "Verify export index views result in downloaded pdf and csv files")
    @Test
    public void exportIndexViewTest()
    {
        StartingPage startingPage = navToStart();
        startingPage.primaryNavigationItem(PrimaryNavigationItem.indices);

        IndexPage indexPage = new IndexPage();
        String index = indexPage.getFirstIndustryIndicesName();
        indexPage.searchByIndex(index);

        IndexPage newIndexPage = indexPage.selectIndex(index);
        newIndexPage.exportIndex();
        int indexId = newIndexPage.getId();
        newIndexPage.verifyThatFileDownload(String.format("%d_fund_monthly_returns", indexId), "csv");
    }
}
