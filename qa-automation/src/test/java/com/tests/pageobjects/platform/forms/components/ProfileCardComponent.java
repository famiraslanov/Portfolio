package com.tests.pageobjects.platform.forms.components;

import com.library.Find;
import com.library.FindOptions;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.library.helpers.StringHelper;
import com.tests.pageobjects.platform.ProfilePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Objects;

public class ProfileCardComponent extends ProfileComponent
{
    private final By headlineLabel = By.cssSelector("[class*='ListCardstyled__ItemList'] [class*='HeadingStyle']");
    private final By followButton = By.cssSelector("[data-testid*='followEntity']");
    private final By followButtonStarIcon = By.cssSelector("[data-testid*='followEntity'] > span");
    private final By cardLabel = By.cssSelector("div[data-testid*='listCard-listItem']");
    private final By basicLocator;


    public ProfileCardComponent(String section)
    {
        super(section);
        basicLocator = By.cssSelector("[id*='" + StringHelper.toKebabCase(section) + "'] [class*='ListCardstyled__ItemList']");
        correctPage(Find.options().locator(basicLocator).returnFirst(true));
    }

    public void verifyThatCardWithHeadlineIsDisplayed(String headline)
    {
        Log.story("Verify that card with expected headline is displayed");
        Verify.isFound(Find.options()
                        .locator(basicLocator)
                        .findByText(FindByText.by().contains(headline))
                        .scrollTo(true)
                        .visible(true),
                "Card with " + headline + " is not found");
    }

    public String getFirstCardTitle()
    {
        Log.story("Get first card title");
        WebElement parentElement = Find.element(Find.options().locator(cardLabel).parentLocator(basicLocator).scrollTo(true).returnFirst(true));
        return Find.getText(Find.options().locator(headlineLabel)
                .parent(parentElement)
                .scrollTo(true)
                .returnFirst(true));
    }

    public ProfilePage clickOnCard(String headline)
    {
        Log.story("Click on card");
        Find.click(Find.options().locator(headlineLabel)
                .findByText(FindByText.by().contains(headline))
                .scrollTo(true)
                .clickable(true));
        return new ProfilePage();
    }

    public void clickFollow(String headline)
    {
        Log.story("Click on follow button");
        this.clickFollowState(headline, "star-empty");
    }

    public void clickUnfollow(String headline)
    {
        Log.story("Click on unfollow button");
        this.clickFollowState(headline, "star");
    }

    private void clickFollowState(String headline, String expectedStatus)
    {
        FindOptions starStatus = Find.options()
                .locator(followButtonStarIcon)
                .clickable(true)
                .parentOption(Find.options()
                        .locator(basicLocator)
                        .findByText(FindByText.by().contains(headline))
                );

        String status = Find.getAttribute(starStatus, "data-icon");
        if (Objects.equals(status, expectedStatus)) {
            Find.click(Find.options()
                    .locator(followButton)
                    .clickable(true)
                    .parentOption(Find.options()
                            .locator(basicLocator)
                            .findByText(FindByText.by().contains(headline))
                    )
            );
        } else {
            Log.debug("Already " + (expectedStatus.equals("star-empty") ? "un" : "") + "followed");
        }
    }

}

