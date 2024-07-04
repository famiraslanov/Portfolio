package com.tests.enums.platform;

import com.library.Find;
import com.library.FindOptions;
import com.library.classes.FindByText;
import org.openqa.selenium.By;

public enum RFR
{
    rfr0("0%"),
    rfr1("1%"),
    rfr2("2%"),
    rfr3("3%"),
    rfr4("4%"),
    rfr5("5%");

    public String name;
    public FindOptions findOptions;

    RFR(String name)
    {
        this.name = name;
        this.findOptions = Find.options().locator(By.cssSelector("[role='listbox'] li")).findByText(FindByText.by().contains(name));
    }
}
