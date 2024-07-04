package com.tests.helpers.platform;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.ColumnName;
import com.tests.enums.platform.DataSettings;
import com.tests.enums.platform.EntityCard;
import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.tests.pageobjects.platform.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListingHelper
{
    public static void verifyDataSettingsIsApplied(AssetClass assetClass, EntityCard entityCard, DataSettings dataSetting)
    {
        ExplorePage explorePage = NavigationHelper.loginAndNavigateToExplore(assetClass);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(entityCard);

        entityListingPage.setDataSetting(dataSetting.dropdownOption);
        entityListingPage.verifySearchResults(dataSetting.label);
    }

    public static void verifyDataSettingsWithExternalTypeCondition(AssetClass assetClass, EntityCard entityCard, DataSettings dataSetting)
    {
        ExplorePage explorePage = NavigationHelper.loginAndNavigateToExplore(assetClass);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(entityCard);

        entityListingPage.setDataSetting(dataSetting.dropdownOption);
        String fundNameFirst = entityListingPage.getInvestorName(0);
        ProfilePage fundProfile = entityListingPage.clickOnRowByName(fundNameFirst);
        ProfilePage managerProfile = fundProfile.clickOnLinkedProfileLink();
        managerProfile.verifyInfoLabel("Type", dataSetting.label);
    }

    public static void verifyThatColumnIsSorted(ColumnName columnName, boolean descending)
    {
        Log.story("Verify that table is sorted by " + (descending ? "DESC" : "ASC") + " for column " + columnName);

        List<String> values = new ArrayList<>(Collections.unmodifiableList(Find.getTexts(columnName.columnContentLocator)));
        List<String> actualValues = List.copyOf(values);
        if (descending) {
            values.sort(Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
            Log.object("Found", actualValues);
            Log.object("Sorted", values);
            Verify.isTrue(actualValues.equals(values), "Column is not sorted by DESC");
        } else {
            Collections.sort(values, String.CASE_INSENSITIVE_ORDER);

            Log.object("Found", actualValues);
            Log.object("Sorted", values);
            Verify.isTrue(actualValues.equals(values), "Column is not sorted by ASC");
        }
    }
}
