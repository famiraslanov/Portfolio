package com.tests.pageobjects.platform.forms.components;

import com.library.Log;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;

public class MonthlyReturnsIndexTable extends ProfileTableComponent
{
    private final By basicLocator;

    public MonthlyReturnsIndexTable()
    {
        super("__next");
        basicLocator = By.cssSelector("#table-wrapper");
        correctPage(basicLocator);
    }

    public Map.Entry<String, String> getYTDForLastFullYear()
    {
        Log.info("Get YTD data for last full year");
        Map<String, String> yearYTD = new HashMap<>();
        int lastYtRow = 0;
        if (this.getColumnValues( "Dec").get(lastYtRow).contains("-")) {
            lastYtRow = 1;
        }
        yearYTD.put(this.getColumnValues("Year").get(lastYtRow),
                this.getColumnValues("YTD").get(lastYtRow));
        return yearYTD.entrySet().stream().findFirst().get();
    }
}
