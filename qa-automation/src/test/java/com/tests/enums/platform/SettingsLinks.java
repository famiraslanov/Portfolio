package com.tests.enums.platform;

import com.library.Find;
import com.library.FindOptions;
import org.openqa.selenium.By;

public enum SettingsLinks
{
    topics("Topics", By.cssSelector("a[data-testid='navLink-topics']")),
    profiles("Profiles", By.cssSelector("a[data-testid='navLink-profiles']")),
    saveLayouts("Saved layouts", By.cssSelector("a[data-testid='navLink-savedLayouts']")),
    notification("Notifications center", By.cssSelector("a[data-testid='navLink-notificationsCenter']")),
    newlettrers("Newsletters", By.cssSelector("a[data-testid='navLink-newsletters']")),
    opportunities("All opportunities", By.cssSelector("a[data-testid='navLink-notificationsCenter']"));

    public String name;
    public FindOptions findOptions;

    SettingsLinks(String name, By locator)
    {
        this.name = name;
        this.findOptions = Find.options().locator(locator);
    }
}
