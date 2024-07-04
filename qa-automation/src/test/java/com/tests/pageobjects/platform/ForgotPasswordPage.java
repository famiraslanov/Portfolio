package com.tests.pageobjects.platform;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

public class ForgotPasswordPage extends BasePageObject
{
    private final By sendResetInstructionsBtn = By.id("forgot-password-submit");
    private final By emailToResetField = By.id("email");
    private final By successMessageTitleLabel = By.cssSelector("[data-testid='success-message-title']");

    public ForgotPasswordPage()
    {
        super();
        correctPage(sendResetInstructionsBtn);
    }

    public void enterEmailAddressAndSubmit(String email)
    {
        Log.story("Enter email in reset email field and submit");
        Find.insert(emailToResetField, email);
        Find.click(sendResetInstructionsBtn);
    }

    public void verifyEmailResetMessage()
    {
        Log.story("Verify that success message about send reset email is displayed");
        Verify.isFound(successMessageTitleLabel, "Email is not reset");
    }
}
