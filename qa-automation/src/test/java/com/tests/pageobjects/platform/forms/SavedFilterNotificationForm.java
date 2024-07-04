package com.tests.pageobjects.platform.forms;

import com.tests.enums.platform.AlertFrequency;
import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

public class SavedFilterNotificationForm extends BasePageObject
{
    private final By pageLoadIdentifier = By.cssSelector("[class*='Popupstyled__Content']");
    private final By editButton = By.cssSelector("[data-testid*='settingsField-'] > [data-icon='edit']");
    private final By rowLink = By.cssSelector("[class*='SettingsFieldstyled__HeaderContent'] > a");
    private final By sectionHeader = By.cssSelector("[class*='Popupstyled__Content'] section > h3");
    private final By filterNameField = By.cssSelector("[class*='SettingsFieldstyled'] [for*='filter'] input");
    private final By saveButton = By.cssSelector("[class*='SettingsFieldstyled'] [type='submit']");
    private final By closeButton = By.cssSelector("[data-testid*='popup-empty-value-crossBtn'] span");
    private final By deleteButtons = By.cssSelector("[data-testid*='-deleteBtn']");
    private final By filterRows = By.cssSelector("[class^='SettingsFieldstyled__HeaderContent-']");

    public SavedFilterNotificationForm()
    {
        super();
        correctPage(pageLoadIdentifier);
    }

    public void clickOnEditButton(String sectionName, String savedFilterName)
    {
        Log.story("Click on edit icon for selected filter");
        Find.click(Find.options()
                .parentOption(
                        Find.options()
                                .locator(By.cssSelector("[class^='Popupstyled__Wrapper-'] [class^='SettingsFieldstyled__HeaderContent']"))
                                .findByText(FindByText.by().contains(savedFilterName))
                )
                .locator(By.cssSelector("div div button"))
                .returnFirst(true)
        );
    }

    public void enterFilterName(String newFilterName)
    {
        Log.story("Enter new name for selected filter");
        Find.insert(Find.options().locator(filterNameField).scrollTo(true), newFilterName);
    }

    public void selectAlertActivity(AlertFrequency alertFrequency)
    {
        Log.story("Select alert activity radiobutton: " + alertFrequency);
        Find.click(alertFrequency.selectOptions);
    }

    public void clickSaveButton()
    {
        Log.story("Click save filter button");
        Find.click(saveButton);
    }

    public void clickCloseButton()
    {
        Log.story("Click on close edit filter popup button");
        Find.click(closeButton);
    }

    public void clickDeleteButton(String filterName)
    {
        Log.story("Delete saved filter by name: " + filterName);
        Find.click(Find.options()
                .locator(deleteButtons)
                .parentOption(Find.options()
                        .locator(filterRows)
                        .findByText(FindByText.by().equals(filterName))
                        .returnFirst(true)
                )
        );
    }

    public void verifyThatAlertFrequencyIsChecked(AlertFrequency alertFrequency)
    {
        Log.story("Verify that expected Alert Frequency is checked");
        Verify.isTrue(Find.element(alertFrequency.verifyStateOptions).isSelected(), "Expected  Alert Frequency is not checked");
    }
}
