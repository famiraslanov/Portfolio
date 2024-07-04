package com.tests.ui.platform.hedge.indices;

import com.library.annotations.CoverageInfo;
import com.library.helpers.DateHelper;
import com.library.helpers.FileHelper;
import com.tests.enums.platform.ExportFileFormat;
import com.tests.enums.platform.PrimaryNavigationItem;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.IndexPage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class ExportConstituentIndexTests extends HedgeMain
{
    @CoverageInfo(details = "Verify export Industry index stats to csv files")
    @Test
    public void exportConstituentIndustryIndexTests()
    {
        String index = "Relative Value Hedge Fund Index";
        StartingPage startingPage = navToStart();
        startingPage.primaryNavigationItem(PrimaryNavigationItem.indices);

        IndexPage indexPage = new IndexPage();
        indexPage.searchByIndex(index);

        IndexPage newIndexPage = indexPage.selectIndex(index);
        int id = newIndexPage.getId();
        newIndexPage.exportConstituentFundsIndex();
        String fileName = String.format("As_of_%s_%d", DateHelper.dtInsert("yyyyMMdd"), id);
        newIndexPage.verifyThatFileDownload(String.format("As_of_%s_%d", DateHelper.dtInsert("yyyyMMdd"), id), ExportFileFormat.csv.fileExtension);
        FileHelper.isNotEmptyFile(fileName, ExportFileFormat.csv);

        newIndexPage.exportMonthlyReturns();
        newIndexPage.verifyThatFileDownload(String.format("%d_fund_monthly_returns", newIndexPage.getId()), ExportFileFormat.csv.fileExtension);
        FileHelper.isNotEmptyFile(fileName, ExportFileFormat.csv);
    }
}
