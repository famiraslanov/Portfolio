package com.tests.pageobjects.platform.forms;

import com.tests.enums.platform.AlertFrequency;
import com.tests.enums.platform.SettingsLinks;
import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

import static org.apache.commons.lang3.EnumUtils.getEnumIgnoreCase;

public class NotificationsForm extends BasePageObject
{
    private final By pageLoadIdentifier = By.cssSelector("[data-testid='template-contentTitle-notificationsCenter']");
    private final By notificationBlock = By.cssSelector("[class*='NotificationTileWrapper']");
    private final By notificationEntityLink = By.cssSelector("[class*='ArrowLinkLink']");
    private final By bulkNotificationToggle = By.cssSelector("button[class*='NotificationFrequencyToggle']");
    private final By notificationOptions = By.cssSelector("[class*='NotificationFrequencyContentItem']");
    private final By notificationMessage = By.cssSelector("[class*='UserMessagestyled__MessageText']");

    public NotificationsForm()
    {
        super();
        correctPage(pageLoadIdentifier);
    }

    public NotificationsEntityForm goToEntityNotificationForm(SettingsLinks settingsLinks)
    {
        Log.story("Go to entity notification form");
        Find.click(Find.options().locator(notificationEntityLink)
                .parentOption(Find.options().locator(notificationBlock).findByText(FindByText.by().contains(settingsLinks.name))));
        return new NotificationsEntityForm();
    }

    public AlertFrequency getCurrentBulkOptionName(SettingsLinks settingsLinks)
    {
        Log.story("Get current bulk alert option");
        String value = Find.getText(Find.options().locator(bulkNotificationToggle)
                .parentOption(Find.options().locator(notificationBlock).findByText(FindByText.by().contains(settingsLinks.name))));
        if (!value.equals("Custom")) {
           return getEnumIgnoreCase(AlertFrequency.class, value);
        }
        return AlertFrequency.none;
    }

    public void bulkChangeNotifications(SettingsLinks settingsLinks, AlertFrequency alertFrequency)
    {
        Log.story("Change notification for all entity ");
        Find.click(Find.options().locator(bulkNotificationToggle)
                .parentOption(Find.options().locator(notificationBlock).findByText(FindByText.by().contains(settingsLinks.name))));
        Find.click(Find.options().locator(notificationOptions).findByText(FindByText.by().contains(alertFrequency.name))
                .parentOption(Find.options().locator(notificationBlock).findByText(FindByText.by().contains(settingsLinks.name))));

        Verify.isFound(Find.options().locator(bulkNotificationToggle).findByText(FindByText.by().contains(alertFrequency.name))
                .parentOption(Find.options().locator(notificationBlock).findByText(FindByText.by().contains(settingsLinks.name))), "Notifications aren't updated !");
    }
}
