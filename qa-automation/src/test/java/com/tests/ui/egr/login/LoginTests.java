package com.tests.ui.egr.login;

import com.library.Store;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.EGRMain;
import com.tests.pageobjects.egr.HomePage;
import com.tests.pageobjects.egr.LoginPage;
import org.testng.annotations.Test;

public class LoginTests extends EGRMain
{
    @CoverageInfo(details = "Verify login on egr")
    @Test
    public void loginTest()
    {
        HomePage home = navToStart();
        LoginPage loginPage = home.goToLoginPage();
        loginPage.login(Store.getUserLogin());
        home.verifyThatUserLoggedIn();
    }

}
