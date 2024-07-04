package com.tests.helpers.platform;

import com.library.Log;
import com.library.Store;
import com.library.Verify;
import com.library.helpers.Function;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.ProfilePage;
import com.tests.pageobjects.platform.StartingPage;

public class LoadProfileSmokeHelper
{
    public static void loadProfileSmokeHelper(AssetClass assetClass, EntityCard[] entityCards)
    {
        if (!Store.getSettings().isSimpleSmoke()) {
            Log.notRun("These are duplicated in normal main run so should not be run unless part of a simple Smoke");
        }

        StartingPage startingPage = navToStart(assetClass);

        for (EntityCard entityCard : entityCards) {
            try {

                ExplorePage explorePage = startingPage.navigateToExplore(assetClass);
                EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(entityCard);
                String selectItem = entityListingPage.getInvestorName(1);

                ProfilePage profilePage = entityListingPage.clickOnRowByName(selectItem);
                Function.closeCurrentTab();
                Function.switchWindow(null);
            } catch (Exception exception) {
                Log.exception(exception);
                Verify.isTrue(false, "Unable to load profile for " + assetClass + " " + entityCard.tab, true);
            }
        }
    }

    public static StartingPage navToStart(AssetClass assetClass)
    {
        return NavigationHelper.loginAndNavigateTo(assetClass);
    }
}
