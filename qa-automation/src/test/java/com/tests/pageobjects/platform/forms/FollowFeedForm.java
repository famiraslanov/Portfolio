package com.tests.pageobjects.platform.forms;

import com.library.Find;
import com.library.Log;
import com.library.classes.FindByText;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

public class FollowFeedForm extends BasePageObject
{
    private By pageLoadIdentifier = By.cssSelector("[data-testid='follow-popup-content']");
    private By tagsLabel = By.cssSelector("[class*='FollowingPopupContentstyled__TagList'] [class*='Tagstyled__Text']");

    public FollowFeedForm()
    {
        super();
        correctPage(pageLoadIdentifier);
    }

    public void clickOnTag(String tagName)
    {
        Log.story("Click on " + tagName + " label box");
        Find.click(Find.options().locator(tagsLabel).findByText(FindByText.by().contains(tagName)).checkForNoSpinner(true));
    }

    public void clickSubmitButton()
    {
        Log.story("Click Submit button");
        Find.click(Find.options().locator(By.cssSelector("button")).findByText(FindByText.by().contains("Submit")).parentLocator(pageLoadIdentifier).checkForNoSpinner(true));
    }
}
