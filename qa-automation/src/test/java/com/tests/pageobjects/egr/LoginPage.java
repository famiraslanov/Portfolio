package com.tests.pageobjects.egr;

import com.library.Find;
import com.library.Log;
import com.tests.enums.platform.UserLogin;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

public class LoginPage extends BasePageObject
{
    private By pageLoadIdentifier = By.id("zephr-login__form");
    private By emailInput = By.name("email_address");
    private By passwordInput = By.name("password");
    private By logInButton = By.id("zephr-login--login-submit");

    public void login(String email, String password)
    {
        Log.story("Type credential to login in system");
        Find.insert(emailInput, email);
        Find.insert(passwordInput, password);
        Find.click(logInButton);
    }

    public void login(UserLogin user)
    {
        Log.story("Type credential to login in system");
        Find.insert(emailInput, user.emailAddress);
        Find.insert(Find.options().locator(passwordInput).decryptText(true), user.egrEncryptedPassword);
        Find.click(logInButton);
    }
}
