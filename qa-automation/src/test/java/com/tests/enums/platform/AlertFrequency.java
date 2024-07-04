package com.tests.enums.platform;

import com.library.Find;
import com.library.FindOptions;
import org.openqa.selenium.By;

public enum AlertFrequency
{
    none("None", "none"),
    daily("Daily", "daily-alert"),
    weekly("Weekly", "weekly-alert"),
    immediately("Immediately", "immediate-alert");

    public String name;
    public FindOptions selectOptions;
    public FindOptions verifyStateOptions;

    AlertFrequency(String name, String baseLocatorIdString)
    {
        this.selectOptions = Find.options().locator(By.cssSelector("#" + baseLocatorIdString + " + div + span")).scrollTo(true);
        this.verifyStateOptions = Find.options().locator(By.id(baseLocatorIdString)).scrollTo(true);
        this.name = name;
    }
}
