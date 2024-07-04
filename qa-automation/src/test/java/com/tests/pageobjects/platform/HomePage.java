package com.tests.pageobjects.platform;

import com.tests.enums.platform.UserLogin;
import com.library.Find;
import com.library.Log;
import com.library.enums.Site;
import com.library.helpers.Function;
import com.tests.pageobjects.baseobjects.BasePageObject;
import com.tests.quickwatch.QuickWatchItems;
import com.library.Store;
import com.library.enums.Environment;
import org.openqa.selenium.By;

public class HomePage extends BasePageObject
{
    private final By pageLoadIdentifier = By.id("login-emailInput");
    private final By emailField = By.id("login-emailInput");
    private final By passwordField = By.id("login-password");
    private final By submitButton = By.id("login-submit");
    private final By forgotPasswordLink = By.id("login-forgot-password-link");

    public HomePage()
    {
        super();
        addQuickWatch(QuickWatchItems.cookieBanner());
        correctPage(pageLoadIdentifier);
    }

    public static HomePage load()
    {
        Function.load(Site.platform.url());
        return new HomePage();
    }

    public void login(UserLogin userLogin)
    {
        Find.insert(emailField, userLogin.emailAddress);
        Find.insert(Find.options().locator(passwordField).decryptText(true),
                (Store.getEnvironment() == Environment.prod && userLogin == UserLogin.admin) ? userLogin.encryptedPasswordAdmin : userLogin.encryptedPassword);
        Find.click(submitButton);
    }

    public ForgotPasswordPage clickForgotPasswordLink()
    {
        Log.info("Click on Forgot password? link");
        Find.click(forgotPasswordLink);
        return new ForgotPasswordPage();
    }
}
