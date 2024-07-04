package com.tests.pageobjects.platform.forms;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.tests.enums.platform.EntityCard;
import com.tests.enums.platform.UserLogin;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProfilesSettingForm extends BasePageObject
{
    private final By pageLoadIdentifier = By.cssSelector("[class*='Templatestyled__ContentWrapper']");
    private final By filterGroupToggles = By.cssSelector("[data-testid='filter-group-toggle'] [class*='FilterGroupstyled__GroupControls']");
    private final By modeFollowButtons = By.cssSelector("[data-testid='dropdown-content'] [class*='Buttonstyled__ButtonWrapper']");
    private final By profileListElement = By.cssSelector("[data-testid*='selectMenu-listItem']");
    private final By profileListNames = By.cssSelector("[class*='Liststyled__ListName']");
    private final By followButton = By.cssSelector("[class*='ListFollowButton']");
    private final By searchProfileTextField = By.cssSelector("[data-testid*='selectMenu-input']");
    private final By copyLayoutIcon = By.cssSelector("[data-icon='copy']");
    private final By expandedProfileDropdown = By.cssSelector("[role='combobox'][aria-expanded='true']");
    private By spinner = By.cssSelector("[data-testid='spinner']");

    public ProfilesSettingForm()
    {
        super();
        correctPage(pageLoadIdentifier);
    }

    public void clickOnEntityToggle(EntityCard entityCard)
    {
        Log.story("Click on entity follow toggle: " + entityCard.value);
        WebElement listsElements = Find.element(Find.options().locator(expandedProfileDropdown).returnFirst(true).failOnNotFound(false).checkForNoSpinner(true));
        if (listsElements == null) {
            Find.click(Find.options().locator(filterGroupToggles).findByText(FindByText.by().contains(entityCard.value)));
        }
    }

    private void selectFollowMode(boolean unfollow)
    {
        Log.story("Select follow mode in list of profile");
        String buttonText = unfollow ? "Follow" : "Following";
        Find.click(Find.options().locator(modeFollowButtons).findByText(FindByText.by().equals(buttonText)));
    }

    public List<String> getAllProfiles()
    {
        Log.story("Get list of all profile rows");
        return Find.getTexts(profileListNames);
    }

    private void clickOnFollowButton(String profile)
    {
        Log.story("Click on follow(ing) button for profile: " + profile);
        Find.click(Find.options().locator(followButton).clickable(true)
                .parentOption(Find.options().locator(profileListElement).returnFirst(true).findByText(FindByText.by().contains(profile))));
    }

    public void followProfile(String profile, EntityCard entityCard, boolean followMode)
    {
        Log.story("Follow/Unfollow profile using settings: " + profile + ": follow mode: " + followMode);
        clickOnEntityToggle(entityCard);
        selectFollowMode(followMode);
        Verify.isNotFound(spinner, "Spinner is displayed");
        clickOnFollowButton(profile);
    }

    public void copyProfile(String profile, EntityCard entityCard, boolean followMode, UserLogin userLogin)
    {
        clickOnEntityToggle(entityCard);
        selectFollowMode(followMode);
        Find.click(Find.options().locator(copyLayoutIcon).clickable(true)
                .parentOption(Find.options().locator(profileListElement).findByText(FindByText.by().contains(profile))));
        CopyForm copyForm = new CopyForm();
        copyForm.copy(userLogin);
    }

    public void verifyStatusOfProfileFollow(String profile, EntityCard entityCard, boolean expectedFollowedMode)
    {
        Log.story("Verify that profile is followed: " + profile);
        clickOnEntityToggle(entityCard);
        selectFollowMode(expectedFollowedMode);
        if (expectedFollowedMode){
            Find.insert(searchProfileTextField, profile);
        }
        Verify.isFound(Find.options().locator(profileListNames).findByText(FindByText.by().equals(profile)).checkForNoSpinner(true),
                profile + " is not followed");
    }

    public void verifyThatCorrectTrendProfilesAreDisplayed(List<String> actualLinks, List<String> expectedLinks)
    {
        Log.story("Verify that top profiles are displayed as expected");
        for (int i = 0; i < 50; i++) { // 50 - limit on UI
            Verify.contains(expectedLinks.get(i), actualLinks.get(i), actualLinks.get(i) + " is not displayed on page");
        }
    }
}
