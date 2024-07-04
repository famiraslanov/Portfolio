package com.tests.pageobjects.platform;

import com.tests.enums.platform.AssetClass;
import com.library.Find;
import com.library.Log;
import com.library.Verify;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MarketsPage extends SecondaryNowPage
{
    private By pageLoadIdentifier = By.cssSelector(".bDoBuW");
    private By rowBlocks = By.cssSelector("[data-testid^='signalCard-']");

    public MarketsPage(AssetClass assetClass)
    {
        super(assetClass);
        correctPage(pageLoadIdentifier);
    }

    public void verifyAtLeast(int expectedGreaterThan)
    {
        Log.story("Verify at least " + expectedGreaterThan + " rows shown");

        List<WebElement> rows = Find.elements(rowBlocks);
        Verify.greaterThan(expectedGreaterThan, rows.size(), "The number of rows shown was less than: " + expectedGreaterThan);
    }
}
