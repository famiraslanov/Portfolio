package com.tests.ui.platform.hedge.myaccount;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.library.annotations.CoverageInfo;
import com.library.helpers.DateHelper;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.forms.CompareFindsForm;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class CompareSavedListTests extends HedgeMain
{
    @CoverageInfo(details = "Verify compare saved list")
    @Test
    public void compareSavedListTest()
    {
        AssetClass assetClass = AssetClass.hedge;
        EntityCard entityCard = EntityCard.funds;

        String newIndexName1 = "CustomIndex_" + DateHelper.dtInsert();
        StartingPage startingPage = navToStart();
        ExplorePage explorePage = startingPage.navigateToExplore(assetClass);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(entityCard);

        String[] selectedFunds = entityListingPage.createDefaultSaveList(newIndexName1, false);
        String newIndexName2 = "CustomIndex_" + DateHelper.dtInsert();
        entityListingPage.createNewSavedList(newIndexName2, true);

        List<String> selectedFundsList = new ArrayList<>(List.of(selectedFunds[0], selectedFunds[1]));

        explorePage = startingPage.navigateToExplore(assetClass);
        explorePage.clearAutotestsSavedList();
        explorePage.selectSavedListTableFilter(entityCard);

        String firstSavedList = explorePage.getSavedListName(0, false, entityCard);
        String firstCustomIndex = explorePage.getSavedListName(0, true, entityCard);
        CompareFindsForm compareFindsForm = explorePage.compareSavedList(firstSavedList);
        compareFindsForm.verifyThatFindsSelected(selectedFundsList);
        compareFindsForm.verifyInfoIsDisplayed(selectedFundsList);

        compareFindsForm.closeCompareForm();
        explorePage.clickToAsIndexIcon(newIndexName1);
        compareFindsForm = explorePage.compareSavedList(firstSavedList);
        compareFindsForm.verifyThatFindsSelected(selectedFundsList);

        compareFindsForm.addCustomIndex(firstCustomIndex);
        selectedFundsList.add(firstCustomIndex);
        compareFindsForm.verifyThatStatisticsRowIsDisplayed(selectedFundsList);
        compareFindsForm.verifyThatFindsSelected(selectedFundsList);

        compareFindsForm.closeCompareForm();
        explorePage.deleteSavedList(newIndexName1);
        explorePage.deleteSavedList(newIndexName2);
    }
}
