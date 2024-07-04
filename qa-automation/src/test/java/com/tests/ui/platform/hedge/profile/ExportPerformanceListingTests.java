package com.tests.ui.platform.hedge.profile;

import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.helpers.FileHelper;
import com.tests.enums.platform.*;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class ExportPerformanceListingTests extends HedgeMain
{
    @CoverageInfo(details = "Verify performance listing export")
    @Test
    public void exportPerformanceListingTests()
    {
        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);
        entityListingPage.selectSavedLayout("Fund performance");

        int preSearchCount = entityListingPage.getEntityCountListing();
        entityListingPage.enterInSearchField(EntityCard.funds.value, preSearchCount);
        int numberOnEntityOnListing = entityListingPage.getEntityCountListing();

        ExportFileFormat exportFileFormat = ExportFileFormat.excel;
        ExportDateFormat exportDateFormat = ExportDateFormat.dayFirst;
        ExportTemplates exportTemplate = ExportTemplates.fundOverview;

        entityListingPage.exportListingInvestor(exportTemplate, exportDateFormat, exportFileFormat);
        String fileName = FileHelper.getExportFileName(exportDateFormat, EntityCard.funds, exportTemplate);

        int countOfRowsInFile = FileHelper.getCountEntityInFile(fileName, exportFileFormat);
        Verify.isEqual(numberOnEntityOnListing, countOfRowsInFile, "Count of exported rows not equal");
    }

}
