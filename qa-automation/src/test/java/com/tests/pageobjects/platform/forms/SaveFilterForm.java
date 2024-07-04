package com.tests.pageobjects.platform.forms;

import com.library.Find;
import com.library.FindOptions;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.library.helpers.Function;
import com.tests.enums.platform.AlertFrequency;
import com.tests.enums.platform.UserLogin;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

import java.time.Duration;

public class SaveFilterForm extends BasePageObject
{
    private final By pageLoadIdentifier = By.cssSelector("h2[class*='PreSavedLayoutsModalstyled__']");
    private final By saveFilterNameField = By.cssSelector("[data-testid='pre-save-filters-textarea']");
    private final FindOptions saveFilterButton = Find.options().locator(By.cssSelector("[class^='DualModalstyled__Wrapper'] button")).findByText(FindByText.by().contains("Save layout"));
    private final FindOptions copyFilterButton = Find.options().locator(By.cssSelector("[class^='DualModalstyled__Wrapper'] button")).findByText(FindByText.by().contains("Copy layout"));
    private final By notificationToggle = By.className("[data-testid='emailAlerts-toggle']");
    private final By closeButton = By.id("dualModal-close");

    public SaveFilterForm()
    {
        super();
        correctPage(pageLoadIdentifier);
    }

    public void saveFilter(String name, AlertFrequency frequency)
    {
        Log.story("Enter new saved filter name and save filter");
        Find.insert(Find.options().locator(saveFilterNameField).clickable(true), name);
        Find.click(frequency.selectOptions);
        Find.click(saveFilterButton);
    }

    public void saveFilter(String name)
    {
        Log.story("Enter new saved filter name and save filter without Alert Frequency");
        Find.insert(saveFilterNameField, name);
        Find.click(notificationToggle);
        Find.click(saveFilterButton);
    }

    public void copyFilter(String name, AlertFrequency frequency, UserLogin userLogin)
    {
        Log.story("Copy not saved filter");
        Find.insert(Find.options().locator(saveFilterNameField).clickable(true), name);
        Find.click(frequency.selectOptions);
        Find.click(copyFilterButton);

        CopyForm copyForm = new CopyForm();
        copyForm.copy(userLogin);
        Find.click(closeButton);
    }
}
