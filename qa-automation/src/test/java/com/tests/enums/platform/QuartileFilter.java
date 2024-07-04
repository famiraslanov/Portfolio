package com.tests.enums.platform;

import com.library.Find;
import com.library.FindOptions;
import com.library.classes.FindByText;
import org.openqa.selenium.By;

public enum QuartileFilter
{
    quartile1("1st quartile"),
    quartile2("2nd quartile"),
    quartile3("3rd quartile"),
    quartile4("4th quartile");

    public String name;
    public FindOptions findOptions;

    QuartileFilter(String name)
    {
        this.name = name;
        this.findOptions = Find.options().locator(By.cssSelector("[role='listbox'] li")).findByText(FindByText.by().equals(name));
    }
}
