package com.tests.pageobjects.platform.forms;

import com.library.Find;
import com.library.classes.FindByText;
import com.library.helpers.DateHelper;
import com.library.helpers.Function;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

import java.util.Date;

public class DateRangeForm extends BasePageObject
{
    private final By pageLoadIdentifier = By.cssSelector("[data-testid='dropdown-content']");
    private final By sinceInceptionDropdown = By.cssSelector("[data-testid='sorting-dropdown']");
    private final By dateDropdown = By.cssSelector("[class*='MonthYearSelectorstyled'] > [data-testid='sorting-dropdown']");
    private final By doneButton = By.cssSelector("[data-testid*='dropdown-content'] [class*='Buttonstyled']");
    private final By options = By.cssSelector("[data-testid*='dropdown-item-']");

    public DateRangeForm()
    {
        correctPage(pageLoadIdentifier);
    }

    public void selectSinceInception(String sinceInception)
    {
        Find.click(Find.options().locator(sinceInceptionDropdown).parentLocator(pageLoadIdentifier).returnFirst(true));
        Find.click(Find.options().locator(options).findByText(FindByText.by().equals(sinceInception)));
        Find.click(doneButton);
    }

    public void selectSinceInception(Date from, Date to)
    {
        Find.click(Find.elements(dateDropdown).get(0));
        Function.slowEnvironmentWait();
        Find.click(Find.options().locator(options).findByText(FindByText.by().contains(DateHelper.getMonth(from))));

        Find.click(Find.elements(dateDropdown).get(1));
        Function.slowEnvironmentWait();
        Find.click(Find.options().locator(options).findByText(FindByText.by().contains(DateHelper.getYear(from))));

        Find.click(Find.elements(dateDropdown).get(2));
        Function.slowEnvironmentWait();
        Find.click(Find.options().locator(options).findByText(FindByText.by().contains(DateHelper.getMonth(to))));

        Find.click(Find.elements(dateDropdown).get(3));
        Function.slowEnvironmentWait();
        Find.click(Find.options().locator(options).findByText(FindByText.by().contains(DateHelper.getYear(to))));

        Find.click(doneButton);
    }
}
