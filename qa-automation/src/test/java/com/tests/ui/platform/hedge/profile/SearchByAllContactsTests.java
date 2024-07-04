package com.tests.ui.platform.hedge.profile;

import com.library.Log;
import com.library.helpers.Function;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.helpers.platform.NavigationHelper;
import com.library.Verify;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.forms.components.ProfileTableComponent;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import org.testng.annotations.Test;

public class SearchByAllContactsTests extends HedgeMain
{
    @CoverageInfo(details = "Verify search by all contacts")
    @Test
    public void searchByAllContactsTests()
    {
        ExplorePage explorePage = NavigationHelper.loginAndNavigateToExplore(AssetClass.hedge);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(EntityCard.investors);

        // This test can be affected by Test accounts setup. For now the solution is to adjust the getInvestorName index to a real investor
        entityListingPage.applyNewFilter("Consultant name");
        String profileFirst = entityListingPage.getInvestorName(0);
        entityListingPage.clickOnRowByName(profileFirst);

        ProfileTableComponent allContacts = new ProfileTableComponent("All contacts");
        Log.knownIssue("NODEV-955", Function.getStackTrace(), true);
        String contactName = allContacts.getColumnValue(1, "Name");
        allContacts.typeSearchQuery(contactName);
        String actualContactName = allContacts.getColumnValue(1, "Name");
        Verify.isEqual(contactName, actualContactName, "Incorrect search was performed");
    }
}
