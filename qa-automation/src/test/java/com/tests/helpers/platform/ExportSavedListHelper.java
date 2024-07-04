package com.tests.helpers.platform;

import com.library.Verify;
import com.library.helpers.DateHelper;
import com.library.helpers.FileHelper;
import com.library.helpers.Function;
import com.tests.enums.platform.*;
import com.tests.pageobjects.platform.forms.TopNavigationMenu;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;

import java.time.Duration;


public class ExportSavedListHelper
{
    public static void runner(AssetClass assetClass, EntityCard entityCard, ExportTemplates exportTemplates, ExportDateFormat exportDateFormat, ExportFileFormat exportFileFormat)
    {
        ExplorePage explorePage = NavigationHelper.loginAndNavigateToExplore(assetClass);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(entityCard);
        String listName = DateHelper.dtInsert("yyMMddHHmmss") + "-new-" + entityCard;
        entityListingPage.createDefaultSaveList(listName, false);

        TopNavigationMenu.getInstance().navigateToExplore(assetClass);

        explorePage.selectSavedListTableFilter(entityCard);
        String firstSavedList = explorePage.getSavedListName(0, false, entityCard);
        explorePage.clickOnSavedList(firstSavedList);
        entityListingPage = new EntityListingPage(entityCard);

        int countOfEntity = entityListingPage.getEntityCountListing();

        ExplorePage newExplorePage = TopNavigationMenu.getInstance().navigateToExplore(assetClass);
        newExplorePage.selectSavedListTableFilter(entityCard);
        Function.slowEnvironmentWait(Duration.ofSeconds(1), "Changing the dropdown causes a reload and clicking too soon causes inaction");
        newExplorePage.exportSavedList(firstSavedList, exportTemplates, exportDateFormat, exportFileFormat);

        String fileName = FileHelper.getExportFileName(exportDateFormat, entityCard, exportTemplates);
        Verify.isEqual(countOfEntity, FileHelper.getCountEntityInFile(fileName, exportFileFormat), "Count of exported rows not equal");
        FileHelper.clearFolder();
        newExplorePage.deleteSavedList(listName);
    }
}
