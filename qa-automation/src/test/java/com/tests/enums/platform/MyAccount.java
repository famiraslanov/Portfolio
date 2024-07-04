package com.tests.enums.platform;

import com.library.Find;
import com.library.FindOptions;
import org.openqa.selenium.By;

public enum MyAccount
{
    settings(By.id("header-settings-link")),
    logout(By.id("header-logout-link"));

    public FindOptions findOptions;

    MyAccount(By locator)
    {
        this.findOptions = Find.options().locator(locator).clickable(true).scrollTo(true);
    }
}
