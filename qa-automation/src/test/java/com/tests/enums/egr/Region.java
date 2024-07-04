package com.tests.enums.egr;

import com.library.Find;
import com.library.FindOptions;
import com.library.classes.FindByText;
import org.openqa.selenium.By;

public enum Region
{
    global("Global"),
    northAmerica("North America");

    public final FindOptions findOptions;

    Region(String text)
    {
        this.findOptions = Find.options().locator(By.cssSelector("[class='dropdown-container'] [class='checkbox-checktext']")).findByText(FindByText.by().contains(text));
    }
}
