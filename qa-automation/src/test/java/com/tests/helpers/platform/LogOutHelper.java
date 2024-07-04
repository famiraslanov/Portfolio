package com.tests.helpers.platform;

import com.tests.enums.platform.AssetClass;
import com.tests.pageobjects.platform.forms.TopNavigationMenu;
import com.tests.pageobjects.platform.HomePage;

public class LogOutHelper
{
    public static void runner(AssetClass assetClass)
    {
        NavigationHelper.loginAndNavigateTo(assetClass);
        TopNavigationMenu.getInstance().logOut();
        new HomePage();
    }
}
