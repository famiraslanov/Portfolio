package com.tests.ui.egr.naTracker;

import com.library.Log;
import com.tests.enums.egr.Navigation;
import com.tests.helpers.egr.NavigationHelper;
import com.tests.pageobjects.egr.NaTrackerPage;
import com.tests.pageobjects.baseobjects.EGRMain;
import com.library.annotations.CoverageInfo;
import org.testng.annotations.Test;

public class InfogramLoadTests extends EGRMain
{
    @CoverageInfo(details = "Verify infogram page is seen")
    @Test
    public void infogramLoadTest()
    {
        Log.notRun("Content is different on Staging and Prod... not possible to test");
        navigateAndLogin();
        NavigationHelper.navigateTo(Navigation.naTracker);
        NaTrackerPage naTrackerPage = new NaTrackerPage();
        naTrackerPage.verifyThatUSMapIsDisplayed();
    }

}
