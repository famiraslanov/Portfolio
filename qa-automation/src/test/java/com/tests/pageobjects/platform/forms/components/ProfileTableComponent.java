package com.tests.pageobjects.platform.forms.components;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.library.helpers.Function;
import com.library.helpers.NumberHelper;
import com.library.helpers.StringHelper;
import com.tests.pageobjects.platform.ProfilePage;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileTableComponent extends ProfileComponent
{

    private final By searchField = By.cssSelector("[aria-label='Table search filter'],[aria-label='keyword search filter']");
    private final By footerResultCountLabel = By.cssSelector("[data-testid='table-footer-content']");
    private final By tableRow = By.cssSelector("[role='row']");
    private final By cell = By.cssSelector("[role='cell']");
    private final By columnHeader = By.cssSelector("button[role='columnheader']");
    private final By contentPlaceholder = By.cssSelector("[data-testid*='content-placeholder']");
    private final By loadCellPlaceholder = By.cssSelector("[role='cell'] [data-testid='content-placeholder']");

    private final By basicLocator;

    public ProfileTableComponent(String section)
    {
        super(section);
        basicLocator = By.cssSelector("[id*='" + StringHelper.toKebabCase(section) + "'] #table-wrapper");
        correctPage(Find.options().locator(basicLocator).scrollTo(true));
    }

    public void typeSearchQuery(String query)
    {
        Log.story("Type search query in table search filed");
        Find.insert(Find.options().locator(searchField).parentLocator(basicLocator).checkForNoSpinner(true).staleRefRetry(true), query);
    }

    public int getCountOfRows()
    {
        Log.story("Get count of found rows in table");
        return NumberHelper.extractInt(Find.getText(Find.options().locator(footerResultCountLabel).parentLocator(this.basicLocator)));
    }

    public String getColumnValue(int row, String column)
    {
        Log.story("Get " + column + " column value for row: " + row);
        String value = null;
        List<String> headers = Find.getTexts(Find.options().locator(columnHeader).parentLocator(basicLocator).scrollTo(true).clickable(true));
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).contains(column)) {
                value = Find.getText(Find.elements(Find.options().locator(cell).parentLocator(basicLocator).scrollTo(true).clickable(true)).get(i * row));
                value = StringUtils.substringBefore(value, "\n");
                break;
            }
        }
        return value;
    }

    public void verifyThatCellHasValue(int row, String column, String expectedValue)
    {
        Log.story("Verify that cell contain expected value");
        String actualValue = this.getColumnValue(row, column);
        Verify.contains(actualValue, expectedValue, "The value " + actualValue + " in the column is not equal to the expected value");
    }

    public void clickOnColumnHeader(String header)
    {
        Log.story("Click on column header: " + header);
        Find.click(Find.options().locator(columnHeader).scrollTo(true).clickable(true).parentLocator(this.basicLocator).findByText(FindByText.by().startsWith(header)));
        Verify.isNotFound(Find.options().locator(contentPlaceholder).parentLocator(basicLocator), "Table is not loaded");
    }

    public void clickOnLinkWithName(String name){
        Log.story("Click on link with name: " + name);
        Find.click(Find.options()
                .locator(cell)
                .findByText(FindByText.by().contains(name)).parentLocator(basicLocator));
    }

    public ProfilePage clickOnCellLinkInRow(int rowIndex, String column)
    {
        Log.story("Click on column cell link: " + column);
        ProfilePage profilePage = null;
        List<String> headers = Find.getTexts(Find.options().locator(columnHeader).parentLocator(basicLocator));
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).contains(column)) {
                Find.click(Find.elements(Find.options()
                        .locator(By.cssSelector("span a"))
                        .parentOption(Find.options()
                                .locator(cell)
                                .parentLocator(basicLocator)
                                .returnFirst(true)
                        )
                ).get(i * rowIndex));
                profilePage = new ProfilePage();
                break;
            }
        }
        return profilePage;
    }

    public ProfilePage clickOnRowByIndex(int index)
    {
        Log.story("Click on row");
        Find.click(Find.elements(Find.options()
                        .locator(tableRow)
                        .parentLocator(basicLocator)
                        .scrollTo(true)
                        .clickable(true)
                ).get(index)
        );
        Function.switchWindow(null);
        return new ProfilePage();
    }

    public void clickOnColumnToSort(String columnName)
    {
        Log.info("Click on column header to apply sorting: " + columnName);
        Find.click(Find.options().locator(columnHeader).scrollTo(true).findByText(FindByText.by().startsWith(columnName)).parentLocator(this.basicLocator));
        Verify.isNotFound(loadCellPlaceholder, "Table is not loaded");
    }

    public void verifyThatColumnIsSorted(int columnNumber, boolean descending)
    {
        Log.story("Verify that table is sorted by " + (descending ? "DESC" : "ASC") + " for column " + columnNumber);

        List<String> values = new ArrayList<>(Collections.unmodifiableList(
                Find.getTexts(By.cssSelector("[role='cell']:nth-of-type(" + columnNumber + ") p > a"))));
        List<String> actualValues = List.copyOf(values);
        if (descending) {
            values.sort(Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
            Log.object("Found", actualValues);
            Log.object("Sorted", values);
            Verify.isTrue(actualValues.equals(values), "Column is not sorted by DESC");
        } else {
            Collections.sort(values, String.CASE_INSENSITIVE_ORDER);

            Log.object("Found", actualValues);
            Log.object("Sorted", values);
            Verify.isTrue(actualValues.equals(values), "Column is not sorted by ASC");
        }
    }

    public List<String> getColumnValues(String columnName)
    {
        Log.story("Get column values " + columnName);
        Function.slowEnvironmentWait(Duration.ofSeconds(2), "Wait for table to settle");
        int index = Find.getTexts(Find.options().locator(columnHeader)).indexOf(columnName);
        return Find.getTexts(Find.options().locator(By.cssSelector("[role='cell']:nth-of-type(" + (index + 1) + ")")).clickable(true)
                .parentLocator(this.basicLocator));
    }

    public List<String> getHeaders()
    {
        Log.story("Get table headers");
        return Find.getTexts(Find.options().locator(columnHeader).parentLocator(this.basicLocator));
    }

    public List<String> getRowValues(int rowIndex)
    {
        Log.story("Get row values");
        return Find.getTexts(Find.options().locator(cell).parent(Find.elements(Find.options().locator(tableRow).parentLocator(basicLocator)).get(rowIndex)));
    }

    public String getLastNotNullValueInRow(int rowIndex)
    {
        Log.story("Get last not null value in row");
        return getRowValues(rowIndex).stream()
                .filter(item -> !"-".equals(item))
                .reduce((first, second) -> second)
                .orElse(null);
    }
}
