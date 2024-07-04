package com.tests.pageobjects.platform.forms.components;

import com.library.Find;
import com.library.FindOptions;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import org.openqa.selenium.By;

public class BenchmarkDataComponent extends ProfileComponent
{
    private By labelField = By.cssSelector("div > [class*='PanelBenchmarkstyled__Field-']");
    private By labelFieldValue = By.cssSelector("[class*='PanelBenchmarkstyled__FieldValue']");
    private By labelFieldName = By.cssSelector("[class*='PanelBenchmarkstyled__FieldLabel']");

    public BenchmarkDataComponent(String section)
    {
        super(section);
    }

    public void verifyLabelValue(String labelName, String expectedValue)
    {
        Log.story("Verify that label contains value");
        FindOptions labelBlock = Find.options().locator(labelField).findByText(FindByText.by().contains(labelName));
        String value = Find.getText(Find.options().locator(labelFieldValue).parentOption(labelBlock));
        Verify.isEqual(value, expectedValue, value + " != " + expectedValue + ": for label " + labelName);
    }
}
