package com.tests.pageobjects.platform.forms.components;

import org.openqa.selenium.By;

public class SingleTable extends ProfileTableComponent
{
    private final By basicLocator;
    public SingleTable()
    {
        super("__next");
        basicLocator = By.cssSelector("#table-wrapper");
        correctPage(basicLocator);
    }
}
