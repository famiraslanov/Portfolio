package com.tests.pageobjects.platform.forms;

import com.library.helpers.NumberHelper;
import com.tests.enums.platform.RFR;
import com.tests.enums.platform.SinceInception;
import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

import java.util.Date;

public class StatisticsForm extends BasePageObject
{
    private final By returnStatisticTable = By.cssSelector("h6+div+ul:nth-of-type(1)");
    private final By riskStatisticTable = By.cssSelector("h6+div+ul:nth-of-type(2)");
    private final By dateRangeDropdown = By.cssSelector("[class*='PanelStatisticsstyled'] [data-testid='filter-group-toggle']");
    private final By rfrDropdown = By.cssSelector("[data-testid='sorting-dropdown']:has([data-testid^='active-item-RFR'])");
    private final By options = By.cssSelector("[data-testid*='dropdown-item-']");
    private final By listItemLocator = By.cssSelector("li[class*='ListItem']");
    private final By listValueLocator = By.cssSelector("p[class*='ListItemValue']");

    public StatisticsForm()
    {
        super();
        correctPage(returnStatisticTable);
        Verify.isFound(riskStatisticTable, "Page verification not complete");
    }

    public void selectSinceInception(Date from, Date to)
    {
        Log.story("Select date range for Since Inception");
        Find.click(Find.options().locator(dateRangeDropdown).scrollTo(true));
        DateRangeForm dateRangeForm = new DateRangeForm();
        dateRangeForm.selectSinceInception(from, to);
    }

    public void selectSinceInception(SinceInception sinceInception)
    {
        Log.story("Select since Inception value");
        Find.click(Find.options().locator(dateRangeDropdown).scrollTo(true));
        DateRangeForm dateRangeForm = new DateRangeForm();
        dateRangeForm.selectSinceInception(sinceInception.name);
    }

    public void selectRfrPercentages(RFR rfr)
    {
        Log.story("Select rtr value: " + rfr.name);
        Find.click(Find.options().locator(rfrDropdown).scrollTo(true));
        Find.click(Find.options().locator(options).scrollTo(true).findByText(FindByText.by().contains(rfr.name)));
    }

    public Double getStatisticTableValue(String param)
    {
        Double returnValue = NumberHelper.extractDouble(Find.getText(Find.options().locator(listValueLocator).parentOption(Find.options().locator(listItemLocator).findByText(FindByText.by().startsWith(param)))));
        Verify.isTrue(returnValue != null, param + " value returned as null (could be an empty string)");
        return returnValue;
    }
}
