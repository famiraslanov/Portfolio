package com.tests.enums.platform;

import com.library.Find;
import com.library.FindOptions;
import com.library.classes.FindByText;
import com.library.helpers.DateHelper;
import org.openqa.selenium.By;

public enum IndicesFilter
{
    YTD("YTD"),
    lastYear(String.format("%d",DateHelper.getPreviousYear())),
    rolling3year("Rolling 3 year");

    public FindOptions findOptions;

    IndicesFilter(String option)
    {
        this.findOptions = Find.options().locator(By.cssSelector("[class*='IndexTickerstyled__Container'] [role='option']")).findByText(FindByText.by().contains(option));
    }
}