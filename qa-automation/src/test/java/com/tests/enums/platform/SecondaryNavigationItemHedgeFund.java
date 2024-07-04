package com.tests.enums.platform;

import com.library.Find;
import com.library.FindOptions;
import com.library.classes.FindByText;
import org.openqa.selenium.By;

public enum SecondaryNavigationItemHedgeFund implements NavigationItem
{
    assetRaising("Asset raising"),
    funds("Fund managers"),
    performanceAndMarkets("Performance & Markets"),
    billionDollarClub("Billion Dollar Club"),
    reports("Reports"),
    research("Research");

    public final FindOptions findOptions;

    SecondaryNavigationItemHedgeFund(String text)
    {
        this.findOptions = Find.options().locator(By.cssSelector("[class*='SecondaryNavigationstyled__ItemTitle']"))
                .findByText(FindByText.by().equals(text)).checkForNoSpinner(true);
    }

    @Override
    public FindOptions getFindOptions()
    {
        return findOptions;
    }
}
