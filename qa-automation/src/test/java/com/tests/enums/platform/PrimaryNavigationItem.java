package com.tests.enums.platform;

import com.library.Find;
import com.library.FindOptions;
import com.library.classes.FindByText;
import org.openqa.selenium.By;

public enum PrimaryNavigationItem
{
    now("Now"),
    explore("Explore"),
    indices("Indices"),
    events("Events"),
    search("Search"),
    theAllocator("The Allocator"),
    performance("Performance");

    public final FindOptions findOptions;

    PrimaryNavigationItem(String text)
    {
        if (text.equals("Search")) {
            this.findOptions = Find.options().locator(By.cssSelector("[data-testid='globalSearch-input']")).checkForNoSpinner(true);
        } else {
            this.findOptions = Find.options()
                    .locator(By.cssSelector("[class^='Navigationstyled__Menu-sc-'] a")).checkForNoSpinner(true)
                    .findByText(FindByText.by().equals(text));
        }
    }
}
