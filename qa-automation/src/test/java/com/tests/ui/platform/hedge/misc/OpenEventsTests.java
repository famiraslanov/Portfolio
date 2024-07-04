package com.tests.ui.platform.hedge.misc;

import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.EventsPage;
import com.tests.pageobjects.platform.StartingPage;
import org.testng.annotations.Test;

public class OpenEventsTests extends HedgeMain
{
    @CoverageInfo(details = "Verify open Events tab")
    @Test
    public void openEventsTests()
    {
        StartingPage startingPage = navToStart();
        EventsPage eventsPage = startingPage.navigateToEvents();
        eventsPage.verifyThatEventsCalendarIsOpen();
    }
}
