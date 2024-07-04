package com.tests.pageobjects.platform.forms;

import com.library.helpers.Function;
import com.tests.enums.platform.UserLogin;
import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TopicsSettingForm extends BasePageObject
{
    private final By pageLoadIdentifier = By.cssSelector("[class*='Templatestyled__Section']");
    private final By topicToggleElements = By.cssSelector("[class*='TitleToggle']");
    private final By spinner = By.cssSelector("[data-testid='spinner']");
    private final By topicElement = By.cssSelector("[class*='FollowedTopicsstyled__Content'] [class*='Topicstyled__Wrapper']");
    private final By topicFollowButton = By.cssSelector("[class*='FollowButtonstyled__Button']");
    private final By copyLayoutIcon = By.cssSelector("[data-icon='copy']");

    public TopicsSettingForm()
    {
        super();
        correctPage(pageLoadIdentifier);
    }

    public void openAllTopicsElement()
    {
        Log.story("Open all topic sections");
        Find.elements(topicToggleElements).forEach(Find::click);
    }

    public void clickOnFollowTagButton(String tag)
    {
        Log.story("Click on follow(ing) tag button: " + tag);
        Find.click(Find.options().locator(topicFollowButton).
                parentOption(Find.options().locator(topicElement).findByText(FindByText.by().regex(tag))));
        Verify.isNotFound(spinner, "Spinner is still displayed");
        Function.slowEnvironmentWait();
    }

    public void copyTopic(String tag, UserLogin userLogin)
    {
        Find.click(Find.options().locator(copyLayoutIcon).
                parentOption(Find.options().locator(topicElement).findByText(FindByText.by().regex(tag))));
        CopyForm copyForm = new CopyForm();
        copyForm.copy(userLogin);
    }

    public void verifyThatTagFollowed(String tag)
    {
        Log.story("Verify that tag is followed: " + tag);

        String status = Find.getText(Find.options().locator(topicElement).findByText(FindByText.by().regex(tag))).split("\n")[1];
        Verify.isEqual("Following", status, tag + " is not followed");
    }

    public void verifyThatTagNotFollow(String tag)
    {
        Log.story("Verify that tag is not followed: " + tag);
        String status = Find.getText(Find.options().locator(topicElement).findByText(FindByText.by().regex(tag))).split("\n")[1];
        Verify.isEqual("Follow", status, tag + " is followed");
    }
}
