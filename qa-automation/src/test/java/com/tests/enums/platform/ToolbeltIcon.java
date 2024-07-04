package com.tests.enums.platform;

import com.library.Find;
import com.library.FindOptions;
import org.openqa.selenium.By;

public enum ToolbeltIcon
{
    addToList(By.cssSelector("[data-testid='tooltip-addToList'] > button")),
    rankButton(By.cssSelector("[data-testid='tooltip-fundRankingTool'] > button")),
    compareButton(By.cssSelector("[data-testid='tooltip-compare'] > button"));

    public FindOptions findOptions;

    ToolbeltIcon(By locator)
    {
        this.findOptions = Find.options().locator(locator);
    }
}
