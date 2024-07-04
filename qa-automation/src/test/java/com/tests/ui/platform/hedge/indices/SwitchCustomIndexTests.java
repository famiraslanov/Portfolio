package com.tests.ui.platform.hedge.indices;

import com.library.Log;
import com.library.annotations.CoverageInfo;
import com.library.helpers.DateHelper;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.enums.platform.IndicesFilter;
import com.tests.helpers.platform.NavigationHelper;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.IndexPage;
import com.tests.pageobjects.platform.forms.TopNavigationMenu;
import com.tests.pageobjects.platform.forms.components.MonthlyReturnsIndexTable;
import org.testng.annotations.Test;

import java.util.Map;

public class SwitchCustomIndexTests extends HedgeMain
{
    @CoverageInfo(details = "Verify switch between custom indices on Indices Page")
    @Test
    public void switchCustomIndexTest()
    {
        EntityCard entityCard = EntityCard.funds;
        AssetClass assetClass = AssetClass.hedge;
        String newIndexName1 = "CustomIndex_1" + DateHelper.dtInsert();
        String newIndexName2 = "CustomIndex_2" + DateHelper.dtInsert();

        ExplorePage explorePage = NavigationHelper.loginAndNavigateToExplore(assetClass);
        explorePage.verifyIndexCarouseLoaded();
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.funds);
        entityListingPage.createDefaultSaveList(newIndexName1, true);
        entityListingPage.createNewSavedList(newIndexName2, true);
        explorePage = TopNavigationMenu.getInstance().navigateToExplore(assetClass);

        explorePage.selectSavedListTableFilter(entityCard);
        String firstIndexName = explorePage.getSavedListName(0, true, entityCard);
        String secondIndexName = explorePage.getSavedListName(1, true, entityCard);
        Log.debug("First index name: " + firstIndexName);
        Log.debug("Second index name: " + secondIndexName);
        explorePage.selectIndexFilter(IndicesFilter.rolling3year);

        IndexPage indexPage = explorePage.clickOnIndexTicker(firstIndexName);
        IndexPage secondIndexPage = indexPage.selectIndex(secondIndexName);
        secondIndexPage.verifyThatIndexStatsDataIsDisplayed();
        secondIndexPage.verifyThatMonthlyReturnsIsDisplayed();

        MonthlyReturnsIndexTable monthlyReturns = new MonthlyReturnsIndexTable();
        String ytd = monthlyReturns.getColumnValues( "YTD").get(0);
        indexPage.verifyOverviewTableValueByKey("Year to date", ytd);

        Map.Entry<String, String> lastYtd = monthlyReturns.getYTDForLastFullYear();
        indexPage.verifyOverviewTableValueByKey(lastYtd.getKey(), lastYtd.getValue());

        explorePage = TopNavigationMenu.getInstance().navigateToExplore(assetClass);
        explorePage.deleteSavedList(newIndexName1);
        explorePage.deleteSavedList(newIndexName2);
    }
}
