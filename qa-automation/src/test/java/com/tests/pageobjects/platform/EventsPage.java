package com.tests.pageobjects.platform;

import com.library.Find;
import com.library.FindOptions;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.library.helpers.Function;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

public class EventsPage extends BasePageObject
{
    private final FindOptions pageLoadIdentifier = Find.options().locator(By.cssSelector(".fl-module-heading")).findByText(FindByText.by().equals("Events"));

    public EventsPage()
    {
        super();
        correctPage(pageLoadIdentifier);
    }

    public void verifyThatEventsCalendarIsOpen()
    {
        Log.story("Verify that Events calendar page is open");
        Verify.isEqual("https://www.withintelligence.com/events-calendar/", Function.getCurrentUrl(), "Events page is not opened ");
    }
}