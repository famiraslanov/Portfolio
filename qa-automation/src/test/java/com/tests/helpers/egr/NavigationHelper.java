package com.tests.helpers.egr;

import com.tests.enums.egr.Navigation;
import com.tests.pageobjects.egr.HomePage;
import com.tests.pageobjects.egr.LoginPage;
import com.library.Find;
import com.library.Log;
import com.library.Store;

public class NavigationHelper
{
    public static HomePage startingPage()
    {
        Log.story("Go to Home page");
        HomePage.load();
        return new HomePage();
    }

    public static HomePage navigateAndLogin()
    {
        Log.story("Navigate to Home page and login");
        HomePage homePage = startingPage();
        LoginPage loginPage = homePage.goToLoginPage();
        loginPage.login(Store.getUserLogin());
        return new HomePage();
    }

    public static void navigateTo(Navigation navigation)
    {
        Log.story("Navigate to " + navigation.name());
        Find.click(navigation.findOptions);
    }
}