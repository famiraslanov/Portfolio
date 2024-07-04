package com.tests.enums.platform;

import com.library.Find;
import com.library.FindOptions;
import com.library.classes.FindByText;
import org.openqa.selenium.By;

public enum SecondaryNavigationItemRealEstate implements NavigationItem
{
    yourFeed("Your feed"),
    assetRaising("Asset raising"),
    fundManagers("Fund managers"),
    research("Research"),
    performance("Performance");

    public final FindOptions findOptions;

    SecondaryNavigationItemRealEstate(String text)
    {
        this.findOptions = Find.options().locator(By.cssSelector("[class*='SecondaryNavigationstyled__ItemTitle']"))
                .findByText(FindByText.by().equals(text)).checkForNoSpinner(true);
    }

    @Override
    public FindOptions getFindOptions() {return findOptions;}
}
