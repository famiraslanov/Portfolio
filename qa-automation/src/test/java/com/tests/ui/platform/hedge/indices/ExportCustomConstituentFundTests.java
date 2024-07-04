package com.tests.ui.platform.hedge.indices;

import com.library.Log;
import com.library.annotations.CoverageInfo;
import com.library.helpers.DateHelper;
import com.library.helpers.FileHelper;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.enums.platform.ExportFileFormat;
import com.tests.helpers.platform.NavigationHelper;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.IndexPage;
import com.tests.pageobjects.platform.forms.TopNavigationMenu;
import org.testng.annotations.Test;

public class ExportCustomConstituentFundTests extends HedgeMain
{
    @CoverageInfo(details = "Verify export from Custom index and check that file not empty")
    @Test
    public void exportCustomConstituentFundTests()
    {
        Log.notRun("flag NEW_CUSTOM_INDICES = false; Function is disabled");

        AssetClass assetClass = AssetClass.hedge;
        String newIndexName = "CustomIndex_" + DateHelper.dtInsert();
        ExplorePage explorePage = NavigationHelper.loginAndNavigateToExplore(assetClass);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);
        String selectPeople = entityListingPage.getInvestorName(0);
        entityListingPage.selectRowCheckboxByName(selectPeople);
        entityListingPage.createNewSavedList(newIndexName, true);

        IndexPage indexPage = TopNavigationMenu.getInstance().navigateToIndices();
        indexPage.selectIndex(newIndexName);
        indexPage.exportConstituentFundsIndex();
        int id = indexPage.getId();
        String fileName = String.format("As_of_%s_%d", DateHelper.dtInsert("yyyyMMdd"), id);
        indexPage.verifyThatFileDownload(fileName, ExportFileFormat.csv.fileExtension);
        FileHelper.isNotEmptyFile(fileName,ExportFileFormat.csv);

        explorePage = TopNavigationMenu.getInstance().navigateToExplore(assetClass);
        explorePage.deleteSavedList(newIndexName);
    }
}
