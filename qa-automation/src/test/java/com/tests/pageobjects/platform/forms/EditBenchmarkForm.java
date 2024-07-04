package com.tests.pageobjects.platform.forms;

import com.library.Find;
import com.library.FindOptions;
import com.library.Log;
import com.library.classes.FindByText;
import com.library.helpers.Function;
import com.library.helpers.NumberHelper;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;

public class EditBenchmarkForm extends BasePageObject
{
    private final By runBenchmarkButton = By.cssSelector("[class*='Modalstyled__ActionList'] div:last-child > button");
    private final By resetAllButton = By.cssSelector("[class*='Modalstyled__FilterGroupReset'] button");

    public EditBenchmarkForm()
    {
        super();
        correctPage(runBenchmarkButton);
    }

    public List<String> getListOfOptions(Dropdown dropdown)
    {
        Log.story("Fetch list of options for: " + dropdown);
        Find.click(dropdown.trigger);
        List<String> options = Find.getTexts(dropdown.options);
        Log.object("Options", options);
        Find.click(dropdown.trigger);
        return options;
    }

    public int selectStrategy(String strategy)
    {
        Log.story("Select strategy");
        Dropdown dropdown = Dropdown.strategy;

        Find.click(dropdown.trigger);
        FindOptions option = Find.options().locator(dropdown.options).findByText(FindByText.by().contains(strategy));
        int count = NumberHelper.extractInt(Find.getText(option));
        Find.click(option);
        Function.slowEnvironmentWait(Duration.ofSeconds(1));
        return count;
    }

    public void selectVintage(String vintage)
    {
        Log.story("Select vintage");
        Dropdown dropdown = Dropdown.vintage;

        Find.click(dropdown.trigger);
        Find.click(Find.options().locator(dropdown.options).findByText(FindByText.by().contains(vintage)));
        Function.slowEnvironmentWait(Duration.ofSeconds(1));
    }

    public void clickRunBenchmarkButton()
    {
        Log.story("Select vintage");
        Find.click(Find.options().locator(runBenchmarkButton).checkForNoSpinner(true));
    }

    public void clickResetAllFilters()
    {
        Log.story("Click on reset all filters button");
        Find.click(Find.options().locator(resetAllButton).clickable(true));
    }

    public enum Dropdown
    {
        strategy(By.cssSelector("[class*='Modalstyled__FilterGroupFilterList'] div[role='presentation']:first-child")),
        vintage(By.cssSelector("[class*='Modalstyled__FilterGroupFilterList'] div[role='presentation']:last-child"));

        public By trigger;
        public final By options = By.cssSelector("[role='option'][data-testid*='dropdown-item-']");

        Dropdown(By trigger)
        {
            this.trigger = trigger;
        }
    }
}
