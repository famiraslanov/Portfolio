package com.tests.pageobjects.platform.forms;

import com.tests.enums.platform.*;
import com.library.Find;
import com.library.Log;
import com.tests.pageobjects.baseobjects.BasePageObject;
import com.tests.pageobjects.platform.*;
import org.openqa.selenium.By;

public class TopNavigationMenu extends BasePageObject
{
    private By pageLoadIdentifier = By.cssSelector(".js-masthead");
    private By myAccountDropdown = By.cssSelector("[data-testid='myAccount-text']");
    private By assetClassDropdown = By.id("downshift-0-toggle-button");
    private By eventButton = By.cssSelector("a[data-testid='navLink-events']");
    private By searchField = By.cssSelector("data-testid='globalSearch-input'");
    private By logoIcon = By.id("header-logo-link");
    private static TopNavigationMenu instance;

    private TopNavigationMenu()
    {
        super();
        correctPage(pageLoadIdentifier);
    }

    public static TopNavigationMenu getInstance()
    {
        if (instance == null) {
            instance = new TopNavigationMenu();
        }
        return instance;
    }

    private void selectMyAccountDropdown()
    {
        Log.story("Click on My Account dropdown");
        Find.click(Find.options().locator(myAccountDropdown));
    }

    public void logOut()
    {
        Log.story("Click om logout link in My account dropdown");
        selectMyAccountDropdown();
        Find.click(MyAccount.logout.findOptions);
    }

    public SettingPage navigateToSettings()
    {
        Log.story("Navigate to settings page");
        selectMyAccountDropdown();
        Find.click(MyAccount.settings.findOptions);
        return new SettingPage();
    }

    public SavedLayoutsSettingForm navigateToSavedLayouts(AssetClass assetClass)
    {
        SettingPage settingPage = navigateToSettings();
        settingPage.goToLink(SettingsLinks.saveLayouts);
        return new SavedLayoutsSettingForm(assetClass);
    }

    public void selectAssetClass(AssetClass assetClass)
    {
        Log.story("Select asset class " + assetClass);
        try {
            Find.click(Find.options().locator(assetClassDropdown).failOnNotFound(false));
            Find.click(assetClass.findOptions.failOnNotFound(false));
        } catch (Exception e) {
            Log.debug("Having to re-click the assetClass dropdown");
            Find.click(Find.options().locator(assetClassDropdown));
            Find.click(assetClass.findOptions);
        }
    }

    public ExplorePage navigateToExplore(AssetClass assetClass)
    {
        Log.story("Navigate to Explore page");
        Find.click(PrimaryNavigationItem.explore.findOptions);
        return new ExplorePage(assetClass);
    }

    public StartingPage navigateToNow(AssetClass assetClass)
    {
        Log.story("Navigate to Now page");
        Find.click(PrimaryNavigationItem.now.findOptions);
        Find.hover(Find.options().locator(myAccountDropdown));
        return new StartingPage(assetClass);
    }

    public void navigateToEvent()
    {
        Log.story("Navigate to Event page");
        Find.click(PrimaryNavigationItem.events.findOptions);
    }

    public IndexPage navigateToIndices()
    {
        Log.story("Navigate to Indices page");
        Find.click(PrimaryNavigationItem.indices.findOptions);
        return new IndexPage();
    }

    public EntityListingPage navigateToPerformance()
    {
        Log.story("Navigate to Performance page");
        Find.click(PrimaryNavigationItem.performance.findOptions);
        return new EntityListingPage(EntityCard.fundPerformance);
    }

    public void enterInGlobalSearch(String query)
    {
        Log.story("Type search query in global search field");
        Find.click(PrimaryNavigationItem.search.findOptions);
        Find.insert(PrimaryNavigationItem.search.findOptions, query);
    }

    public StartingPage clickOnLogo(AssetClass assetClass)
    {
        Log.story("Click on logo");
        Find.click(logoIcon);
        return new StartingPage(assetClass);
    }
}
