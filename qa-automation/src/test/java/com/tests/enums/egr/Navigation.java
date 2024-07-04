package com.tests.enums.egr;

import com.library.Find;
import com.library.FindOptions;
import com.library.classes.FindByText;
import org.openqa.selenium.By;

public enum Navigation
{
    home("Home"),
    digitalEditions("Digital Editions"),
    events("Events"),
    naTracker("NA Tracker"),
    rankings("Rankings"),
    partners("Partners"),
    directory("Directory");

    public final FindOptions findOptions;

    Navigation(String text)
    {
        this.findOptions = Find.options().locator(By.cssSelector("[class='menu'] nav[class='menu__container'] a")).findByText(FindByText.by().contains(text));
    }
}
