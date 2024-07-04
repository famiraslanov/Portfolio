package com.tests.ui.egr.login;

import com.tests.pageobjects.egr.HomePage;
import com.tests.pageobjects.egr.LoginPage;
import com.tests.pageobjects.baseobjects.EGRMain;
import com.library.annotations.CoverageInfo;
import org.testng.annotations.Test;

public class IncorrectLoginTests extends EGRMain
{
    @CoverageInfo(details = "Verify error message is displayed on egr after incorrect login")
    @Test
    public void incorrectLoginTest()
    {
        HomePage home = navToStart();
        LoginPage loginPage = home.goToLoginPage();
        loginPage.login("test@withintelligence.com", "IncorrectTestPassword");
        home.verifyIncorrectLoginMessageIsDisplayed();
    }
}
