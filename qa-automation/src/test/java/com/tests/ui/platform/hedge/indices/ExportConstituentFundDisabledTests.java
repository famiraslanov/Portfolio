package com.tests.ui.platform.hedge.indices;

import com.library.annotations.CoverageInfo;
import com.tests.enums.platform.PrimaryNavigationItem;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.IndexPage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class ExportConstituentFundDisabledTests extends HedgeMain
{
    @CoverageInfo(details = "Verify export constituent Fund is not enabled")
    @Test
    public void exportConstituentFundDisabledTests()
    {
        String index = "Asia Pacific Hedge Fund Index";
        StartingPage startingPage = navToStart();
        startingPage.primaryNavigationItem(PrimaryNavigationItem.indices);
        IndexPage indexPage = new IndexPage();
        indexPage.searchByIndex(index);
        IndexPage newIndexPage = indexPage.selectIndex(index);
        newIndexPage.verifyThatExportIsDisabled();
    }
}
