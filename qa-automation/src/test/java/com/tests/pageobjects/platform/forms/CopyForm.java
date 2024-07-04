package com.tests.pageobjects.platform.forms;

import com.library.Find;
import com.library.Log;
import com.library.Store;
import com.library.Verify;
import com.library.classes.FindByText;
import com.library.enums.Environment;
import com.library.helpers.Function;
import com.tests.enums.platform.UserLogin;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

import java.time.Duration;

public class CopyForm extends BasePageObject
{
    private final By pageLoadIdentifier = By.cssSelector("header[class*='CopyAlertProfileModalstyled']");
    private final By userDataSearchField = By.cssSelector("[class*='UserSearchstyled'] input[placeholder]");
    private final By userRows = By.cssSelector("[class*='UserSearchResultsstyled__ListItem']");
    private final By copyButton = By.id("copy-alert-submit");
    private final By cancelButton = By.id("copy-alert-close");

    public CopyForm()
    {
        super();
        correctPage(pageLoadIdentifier);
    }

    public void copy(UserLogin userLogin)
    {
        this.typeUserEmail(userLogin);
        this.clickCopy();
        this.clickCancel();
    }

    public void typeUserEmail(UserLogin userLogin)
    {
        Log.story("Type user email: " + userLogin.emailAddress);
        Find.insert(userDataSearchField, userLogin.emailAddress);
        Verify.isFound(Find.options().locator(userRows).findByText(FindByText.by().contains(userLogin.emailAddress)).returnFirst(true), "User is not found");
    }

    public void clickCopy()
    {
        Log.story("Click copy button");
        Find.click(copyButton);
        Verify.isFound(Find.options().locator(copyButton).enabled(false), "Copy button is not blocked");
        Function.sleep(Duration.ofSeconds(Store.getEnvironment() == Environment.prod ? 10:20), "Allow the copied saved layout to transfer to the other user");
        Verify.isFound(Find.options().locator(copyButton).enabled(true), "Copy button is not enabled");
    }

    public void clickCancel()
    {
        Log.story("Click copy button");
        Find.click(cancelButton);
    }
}
