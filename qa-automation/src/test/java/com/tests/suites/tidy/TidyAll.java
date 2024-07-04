package com.tests.suites.tidy;

import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.UserLogin;
import com.tests.helpers.platform.NavigationHelper;
import com.library.Find;
import com.library.Log;
import com.library.helpers.Function;
import com.tests.pageobjects.baseobjects.MiscMain;
import com.tests.pageobjects.platform.forms.SavedFilterNotificationForm;
import com.tests.pageobjects.platform.ExplorePage;
import org.apache.commons.collections4.CollectionUtils;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class TidyAll extends MiscMain
{
    @Test(dataProvider = "userLoginsProvider")
    public void tidyTestAccounts(UserLogin userLogin)
    {
        ExplorePage explorePage = NavigationHelper.loginAndNavigateToExplore(AssetClass.hedge, userLogin);
        cleanAllSavedLists(explorePage);
        cleanAllSavedLayouts(explorePage);
    }

    @DataProvider(name = "userLoginsProvider")
    private Object[][] userLoginsProvider()
    {
        UserLogin[] userLoginsEnums = UserLogin.values();
        Object[][] userLogins = new Object[userLoginsEnums.length][];
        for (int i = 0; i < userLoginsEnums.length; i++) {
            userLogins[i] = new Object[]{userLoginsEnums[i]};
        }
        return userLogins;
    }

    private void cleanAllSavedLists(ExplorePage explorePage)
    {
        Function.slowEnvironmentWait(Duration.ofSeconds(3)); // Page needs to settle fully before we interact
        List<String> savedListNames = Find.getAttributes(Find.options().locator(By.cssSelector("[data-rowname]")).failOnNotFound(false), "data-rowname");
        if (!CollectionUtils.isEmpty(savedListNames)) {
            savedListNames = savedListNames.stream().filter(listName -> !listName.toLowerCase().contains("keep")).toList();
            Log.story("Saved lists found that need deleting: " + savedListNames.size());
            for (String listToDelete : savedListNames) {
                explorePage.deleteSavedList(listToDelete);
                Function.slowEnvironmentWait(Duration.ofSeconds(3));
            }
        } else {
            Log.story("No savedLists to delete");
        }
    }

    private void cleanAllSavedLayouts(ExplorePage explorePage)
    {
        SavedFilterNotificationForm savedFilterNotificationForm = explorePage.openEditSavedLayout();
        List<String> allSavedLayouts = Find.getTexts(Find.options().locator(By.cssSelector("[class^='SettingsFieldstyled__HeaderContent-']")).failOnNotFound(false));
        if (!CollectionUtils.isEmpty(allSavedLayouts)) {
            allSavedLayouts = allSavedLayouts.stream().filter(layoutName -> !layoutName.toLowerCase().contains("keep")).toList();
            Log.story("Saved layouts to delete: " + allSavedLayouts.size());
            for (String layoutName : allSavedLayouts) {
                Function.slowEnvironmentWait(Duration.ofSeconds(1));
                savedFilterNotificationForm.clickDeleteButton(layoutName);
            }
        } else {
            Log.story("No savedLayouts to delete");
        }
    }
}
