package com.tests.helpers.platform;

import com.library.Find;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.NavigationItem;
import com.tests.enums.platform.UserLogin;
import com.library.Log;
import com.library.Store;
import com.library.Verify;
import com.tests.pageobjects.platform.forms.TopNavigationMenu;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.HomePage;
import com.tests.pageobjects.platform.StartingPage;
import org.openqa.selenium.By;

public class NavigationHelper
{
    public static StartingPage loginAndNavigateTo(AssetClass assetClass)
    {
        Log.story("Login and navigate to starting page for " + assetClass);
        return startingPage(assetClass, Store.getUserLogin());
    }

    public static ExplorePage loginAndNavigateToExplore(AssetClass assetClass)
    {
        return loginAndNavigateToExplore(assetClass, Store.getUserLogin());
    }

    public static ExplorePage loginAndNavigateToExplore(AssetClass assetClass, UserLogin userLogin)
    {
        Log.story("Login and navigate to Explore page for " + assetClass);
        startingPage(assetClass, userLogin);
        return TopNavigationMenu.getInstance().navigateToExplore(assetClass);
    }

    public static StartingPage startingPage(AssetClass assetClass, UserLogin userLogin)
    {
        HomePage homePage = HomePage.load();
        homePage.login(userLogin);
        Verify.isNotFound(By.cssSelector("[class^='LogoSpinner_']"), "Spinner still seen");
        TopNavigationMenu.getInstance().selectAssetClass(assetClass);
        return new StartingPage(assetClass);
    }

    public static <T extends Enum<T> & NavigationItem> void verifyThatAllSecondaryNavigationAreDisplayed(AssetClass assetClass, Class<T> enumClass)
    {
        StartingPage startingPage = loginAndNavigateTo(assetClass);
        startingPage.verifyNavigationItemsAreDisplayed(enumClass);
    }
}
