package com.tests.enums.platform;

import com.library.Find;
import com.library.FindOptions;
import com.library.classes.FindByText;
import org.openqa.selenium.By;

public enum ColumnName
{
    investorName("Investor name"),
    investorType("Investor type"),
    investorAUM("Investor AuM"),
    sector("Sector"),
    consultantLocation("Consultant location"),

    fundYTD("YTD"),
    latestDateReported("Latest date reported"),
    netIRR("Net IRR");

    public FindOptions headerLocator;
    public By columnContentLocator;
    public String headerText;

    ColumnName(String headerText)
    {
        this.headerText = headerText;
        int index = Find.getTexts(By.cssSelector("button[role='columnheader']")).indexOf(headerText);
        String baseLocator = "a [role='cell']:nth-of-type(" + (index + 1) + ")";
        this.columnContentLocator = By.cssSelector(baseLocator + " [data-testid^='tooltip-'] > div, " + baseLocator + " > span");

        this.headerLocator = Find.options().locator(By.cssSelector("button[role='columnheader']")).findByText(FindByText.by().contains(headerText)).checkForNoSpinner(true);
    }
}
