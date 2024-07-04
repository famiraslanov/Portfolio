package com.tests.ui.egr.events;

import com.library.Log;
import com.library.helpers.Function;
import com.tests.enums.egr.Navigation;
import com.tests.helpers.egr.NavigationHelper;
import com.tests.pageobjects.egr.EventsPage;
import com.tests.pageobjects.baseobjects.EGRMain;
import com.library.annotations.CoverageInfo;
import org.testng.annotations.Test;

public class EventCategoryTests extends EGRMain
{

    @CoverageInfo(details = "Verify that events displayed regarding selected category")
    @Test
    public void eventCategoryTest()
    {
        navigateAndLogin();
        NavigationHelper.navigateTo(Navigation.events);
        EventsPage eventsPage = new EventsPage();
        Log.knownIssue("OBP-366", Function.getStackTrace(), true);
        eventsPage.verifyThatAllCategoryTagsContainsArticle();
    }
}
