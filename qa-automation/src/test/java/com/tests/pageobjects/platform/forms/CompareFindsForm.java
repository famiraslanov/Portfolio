package com.tests.pageobjects.platform.forms;

import com.library.Find;
import com.library.Log;
import com.library.Store;
import com.library.Verify;
import com.library.classes.FindByText;
import com.tests.pageobjects.baseobjects.BasePageObject;
import com.library.helpers.Function;
import org.openqa.selenium.By;

import java.util.List;

public class CompareFindsForm extends BasePageObject
{

    private final By pageLoadIdentifier = By.cssSelector("[class='highcharts-root']");
    private final By selectedFundsLabel = By.cssSelector("[class*='ListItemName']");
    private final By informationBlocksLabel = By.cssSelector("[class*='InformationSectionstyled__HeaderIndicator'] + span");
    private final By chartLines = By.cssSelector("[class*='highcharts-tracker-line']");
    private final By closeCompareFormButton = By.cssSelector("[data-testid='dualModal-empty-value-closeBtn']");
    private final By addButton = By.cssSelector("[data-testid='filter-group-toggle']");
    private final By searchField = By.cssSelector("[data-testid*='selectMenu-input-']");
    private final By checkboxOption = By.cssSelector("[class*='Checkboxstyled__LabelText']");
    private final By applySelectionButton = By.cssSelector("[class*='ComparePopupActionButton']");
    private final By tableRow = By.cssSelector("[data-testid='card-table-data-container'] [role='cell']");

    public CompareFindsForm()
    {
        super();
        correctPage(Find.options().locator(pageLoadIdentifier).timeoutMS(Store.getSettings().getDefaultTimeoutMS() * 3)); // Lots of spinner sections
    }

    public void verifyThatStatisticsRowIsDisplayed(List<String> expectedFunds)
    {
        Log.story("Verify that Statistics row is displayed");
        for (String fund : expectedFunds) {
            Verify.isFound(Find.options().locator(tableRow).timeoutMS(Store.getSettings().getDefaultTimeoutMS() * 3).findByText(FindByText.by().contains(fund)), fund + " statistic is not found");
        }
    }

    public void verifyThatFindsSelected(List<String> expectedFunds)
    {
        Log.story("Verify that all funds are selected for compare");

        List<String> selectedFunds = Find.getTexts(selectedFundsLabel);
        Log.object("exp", expectedFunds);
        Log.object("sel", selectedFunds);
        Verify.isTrue(selectedFunds.containsAll(expectedFunds), "Not all expected funds are selected to compare");
        Verify.isEqual(Math.toIntExact(expectedFunds.stream().count()), Math.toIntExact(Find.elements(chartLines).stream().count()),
                "Number of chart lines do not correspond to the number of funds");
    }

    public void verifyInfoIsDisplayed(List<String> expectedFunds)
    {
        Log.story("Verify that information block is displayed for all funds");
        List<String> infoFunds = Find.getTexts(Find.options().locator(informationBlocksLabel).timeoutMS(20000));
        Verify.isTrue(infoFunds.containsAll(expectedFunds), "Info table is not displayed not for all expected funds");
    }

    public void closeCompareForm()
    {
        Log.story("Click on close compare form button");
        Find.click(Find.options().locator(closeCompareFormButton).clickable(true));
    }

    private void addNewChart(String placeholderName, String entity)
    {
        Log.story(placeholderName);
        Find.click(Find.options().locator(addButton).findByText(FindByText.by().contains(placeholderName)).checkForNoSpinner(true));

        Find.insert(Find.options().locator(searchField).clickable(true).checkForNoSpinner(true), entity);
        Find.click(Find.options().locator(checkboxOption).clickable(true).findByText(FindByText.by().startsWith(entity)));
        Find.click(Find.options().locator(applySelectionButton).checkForNoSpinner(true));
        Function.slowEnvironmentWait();
        Verify.isFound(Find.options().locator(selectedFundsLabel).findByText(FindByText.by().startsWith(entity)), entity + " chart not added");
    }

    public void addFund(String fund)
    {
        this.addNewChart("Add funds", fund);
        Function.slowEnvironmentWait();
    }

    public void addIndex(String index)
    {
        this.addNewChart("Add Indices", index);
        Function.slowEnvironmentWait();
    }

    public void addCustomIndex(String index)
    {
        this.addNewChart("Add Custom Indices", index);
    }

}
