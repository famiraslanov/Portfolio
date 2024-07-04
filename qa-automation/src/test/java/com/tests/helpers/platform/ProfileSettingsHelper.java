package com.tests.helpers.platform;

import com.tests.enums.platform.AlertFrequency;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.enums.platform.SettingsLinks;
import com.library.Verify;
import com.tests.pageobjects.platform.SettingPage;
import com.tests.pageobjects.platform.StartingPage;
import com.tests.pageobjects.platform.forms.*;

import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.EnumUtils.getEnumIgnoreCase;

public class ProfileSettingsHelper
{
    public static void verifyAlertFrequency(SettingsLinks settingsLinks, String entityName, AlertFrequency expectedFrequency)
    {
        NotificationsForm notificationsForm = new NotificationsForm();
        NotificationsEntityForm notificationsEntityForm = notificationsForm.goToEntityNotificationForm(settingsLinks);
        notificationsEntityForm.verifySelectedAlert(entityName, expectedFrequency);
    }

    public static void verifyAlertIsChangeable(SettingsLinks settingsLinks, String entityName)
    {
        NotificationsForm notificationsForm = new NotificationsForm();
        NotificationsEntityForm notificationsEntityForm = notificationsForm.goToEntityNotificationForm(settingsLinks);
        AlertFrequency currentValue = notificationsEntityForm.getAlertValue(entityName);

        AlertFrequency newAlert = Arrays.stream(AlertFrequency.values())
                .filter(alert -> alert != getEnumIgnoreCase(AlertFrequency.class, currentValue.name())).toList().get(0);

        notificationsEntityForm.selectAlertValue(entityName, newAlert);
        notificationsEntityForm.verifySelectedAlert(entityName, newAlert);

        notificationsEntityForm.selectAlertValue(entityName, currentValue);
        notificationsEntityForm.verifySelectedAlert(entityName, currentValue);
    }

    public static void verifyBulkAlertIsChangeable(SettingsLinks settingsLinks)
    {
        NotificationsForm notificationsForm = new NotificationsForm();
        AlertFrequency currentValue = notificationsForm.getCurrentBulkOptionName(settingsLinks);
        AlertFrequency newAlert = Arrays.stream(AlertFrequency.values())
                .filter(alert -> alert != getEnumIgnoreCase(AlertFrequency.class, currentValue.name())).toList().get(0);
        notificationsForm.bulkChangeNotifications(settingsLinks, newAlert);

        Verify.isTrue(newAlert.name.equals(notificationsForm.getCurrentBulkOptionName(settingsLinks).name), "Bulk alert option isn't applied!");

        NotificationsEntityForm notificationsEntityForm = notificationsForm.goToEntityNotificationForm(settingsLinks);
        List<AlertFrequency> alertFrequencyList = notificationsEntityForm.getAllAlertsValues();
        Verify.isTrue(alertFrequencyList.stream().allMatch(alert -> alert.name.equals(newAlert.name)), "Not all alerts have been updated !");
    }

    public static void deleteSavedLayoutIfExist(String savedLayoutName, EntityCard entityCard, AssetClass assetClass)
    {
        SavedLayoutsSettingForm savedLayoutsSettingForm = TopNavigationMenu.getInstance().navigateToSavedLayouts(assetClass);
        savedLayoutsSettingForm.clickOnEntityToggle(entityCard);

        if (savedLayoutsSettingForm.isDisplayedLayout(savedLayoutName)) {
            savedLayoutsSettingForm.deleteLayout(savedLayoutName);
        }
    }

    public static void deleteSavedLayout(String savedLayoutName, EntityCard entityCard, AssetClass assetClass)
    {
        SavedLayoutsSettingForm savedLayoutsSettingForm = TopNavigationMenu.getInstance().navigateToSavedLayouts(assetClass);
        savedLayoutsSettingForm.clickOnEntityToggle(entityCard);
        savedLayoutsSettingForm.verifyDisplayedLayout(savedLayoutName);
        savedLayoutsSettingForm.deleteLayout(savedLayoutName);

        savedLayoutsSettingForm = TopNavigationMenu.getInstance().navigateToSavedLayouts(assetClass);
        savedLayoutsSettingForm.clickOnEntityToggle(entityCard);
        savedLayoutsSettingForm.verifyNotDisplayedLayout(savedLayoutName);
    }

    public static void verifyThatTopProfilesAreDisplayed(EntityCard entityCard, AssetClass assetClass, List<String> expectedProfiles)
    {
        StartingPage startingPage = NavigationHelper.loginAndNavigateTo(assetClass);
        SettingPage settingPage = startingPage.goToSettingPage();
        settingPage.goToLink(SettingsLinks.profiles);

        ProfilesSettingForm profilesSettingForm = new ProfilesSettingForm();
        profilesSettingForm.clickOnEntityToggle(entityCard);
        List<String> actualProfiles = profilesSettingForm.getAllProfiles();
        profilesSettingForm.verifyThatCorrectTrendProfilesAreDisplayed(actualProfiles, expectedProfiles);
    }
}
