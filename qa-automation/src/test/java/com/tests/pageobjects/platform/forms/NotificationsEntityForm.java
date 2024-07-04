package com.tests.pageobjects.platform.forms;

import com.tests.enums.platform.AlertFrequency;
import com.library.Find;
import com.library.FindOptions;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.EnumUtils.getEnumIgnoreCase;

public class NotificationsEntityForm extends BasePageObject
{
    private final By pageLoadIdentifier = By.cssSelector("main[class*='Templatestyled__Content']");
    private final By notificationRow = By.cssSelector("[class*='NotificationGroupRow']");
    private final By alertDropdown = By.cssSelector("[aria-haspopup='listbox']");
    private final By alertOption = By.cssSelector("[role='listbox'] [role='option']");
    private final By updateAlertPopup = By.cssSelector("[data-testid*='notification-close-button']");
    private final By backLink = By.cssSelector("a[class*='NotificationsTemplateBackLink']");

    public NotificationsEntityForm()
    {
        super();
        correctPage(pageLoadIdentifier);
    }

    public AlertFrequency getAlertValue(String topic)
    {
        Log.story("Get current alert value");
        String value = Find.getText(Find.options().locator(alertDropdown)
                .parentOption(Find.options().locator(notificationRow).findByText(FindByText.by().startsWith(topic))).scrollTo(true));
        Log.debug("AlertFrequency found: " + value);
        return getEnumIgnoreCase(AlertFrequency.class, value);
    }

    public List<AlertFrequency> getAllAlertsValues()
    {
        Log.story("Get all alerts value");
        List<AlertFrequency> alertFrequencyList = new ArrayList<>();
        List<String> values = Find.getTexts(Find.options().locator(alertDropdown)
                .parentOption(Find.options().locator(pageLoadIdentifier)));

        for (String value : values) {
            alertFrequencyList.add(getEnumIgnoreCase(AlertFrequency.class, value));
        }

        return alertFrequencyList;
    }

    public void selectAlertValue(String topic, AlertFrequency alertFrequency)
    {
        Log.story("Select alert value");
        FindOptions parentOption = Find.options().locator(notificationRow).findByText(FindByText.by().startsWith(topic));
        Find.click(Find.options().locator(alertDropdown).parentOption(parentOption));
        Find.click(Find.options().locator(alertOption).findByText(FindByText.by().equals(alertFrequency.name)).parentOption(parentOption));
        Verify.isFound(updateAlertPopup, "Not info about updating alert");
    }

    public void verifySelectedAlert(String topic, AlertFrequency expectedAlert)
    {
        Log.story("Verify that expected alert is selected");
        Verify.isEqual(expectedAlert.name, getAlertValue(topic).name, "Expected alert frequency is not selected");
    }


    public NotificationsForm clickOnBackLink()
    {
        Find.click(backLink);
        return new NotificationsForm();
    }

}
