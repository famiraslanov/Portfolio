package com.tests.ui.platform.hedge.explore.listing;

import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.helpers.FileHelper;
import com.tests.enums.platform.*;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class ExportListingTests extends HedgeMain
{
    @CoverageInfo(details = "Verify export listing")
    @Test
    public void exportListingTest()
    {
        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.investors);

        int preSearchCount = entityListingPage.getEntityCountListing();
        entityListingPage.enterInSearchField(EntityCard.investors.value, preSearchCount);
        int numberOnEntityOnListing = entityListingPage.getEntityCountListing();

        ExportFileFormat exportFileFormat = ExportFileFormat.excel;
        ExportDateFormat exportDateFormat = ExportDateFormat.monthFirst;
        ExportTemplates exportTemplate = ExportTemplates.basicDetails;

        entityListingPage.exportListingInvestor(exportTemplate, exportDateFormat, exportFileFormat);
        String fileName = FileHelper.getExportFileName(exportDateFormat, EntityCard.investors, exportTemplate);

        int countOfRowsInFile = FileHelper.getCountEntityInFile(fileName, exportFileFormat);
        Verify.isEqual(numberOnEntityOnListing, countOfRowsInFile, "Count of exported rows not equal");
    }
}
