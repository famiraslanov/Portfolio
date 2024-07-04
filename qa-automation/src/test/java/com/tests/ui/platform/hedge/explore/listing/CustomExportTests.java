package com.tests.ui.platform.hedge.explore.listing;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.enums.platform.ExportDateFormat;
import com.tests.enums.platform.ExportFileFormat;
import com.tests.helpers.platform.NavigationHelper;
import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.library.helpers.DateHelper;
import com.library.helpers.FileHelper;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

public class CustomExportTests extends HedgeMain
{
    @CoverageInfo(details = "Verify create and export custom template")
    @Test
    public void customExportTest()
    {
        ExplorePage explorePage = NavigationHelper.loginAndNavigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);
        int preSearchCount = entityListingPage.getEntityCountListing();
        entityListingPage.enterInSearchField(EntityCard.funds.value, preSearchCount);
        int numberOnEntityOnListing = entityListingPage.getEntityCountListing();

        ExportFileFormat exportFileFormat = ExportFileFormat.csv;
        ExportDateFormat exportDateFormat = ExportDateFormat.dayFirst;
        String customTemplateName = "Template-" + DateHelper.dtInsert();
        Date today = DateHelper.getTodayDate();
        Date prevDate = DateHelper.changeDate(today, -1, -1, 0);

        entityListingPage.exportNewCustomTemplate(
                customTemplateName,
                List.of("Fund Name", "Telephone", "Auditor", "Currency", "Fund Type"),
                exportDateFormat,
                exportFileFormat,
                prevDate,
                today);
        String fileName = FileHelper.getExportFileName(exportDateFormat, EntityCard.funds, customTemplateName);

        int countOfRowsInFile = FileHelper.getCountEntityInFile(fileName, exportFileFormat);
        Verify.isEqual(numberOnEntityOnListing, countOfRowsInFile, "Count of exported rows not equal");

        entityListingPage.editCustomTemplate(
                customTemplateName,
                customTemplateName + "-updated",
                List.of("Fund Name"));

        entityListingPage.closeExportPopup();
        entityListingPage.deleteCustomTemplate(customTemplateName + "-updated");
    }
}
