package com.tests.enums.platform;

import com.library.Find;
import com.library.FindOptions;
import com.library.classes.FindByText;
import org.openqa.selenium.By;

public enum SinceInception
{
    sinceInception("Since inception"),
    last3months("3 months"),
    last6months("6 months"),
    last12months("12 months"),
    last2years("2 years"),
    last3years("3 years"),
    last4years("4 years"),
    last5years("5 years"),
    last10years("10 years");

    public String name;
    public FindOptions findOptions;

    SinceInception(String name)
    {
        this.name = name;
        this.findOptions = Find.options().locator(By.cssSelector("[role='listbox'] li")).findByText(FindByText.by().contains(name));
    }
}
