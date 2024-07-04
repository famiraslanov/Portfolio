package com.tests.pageobjects.platform.forms.components;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.helpers.StringHelper;
import com.tests.enums.platform.EntityCard;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ProfilePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProfileSectionComponent extends ProfileComponent
{

    private final By peopleAllContacts = By.cssSelector("div:has(span[data-icon='contact']) a[href*='people']");
    private final By experienceTitle = By.cssSelector("div a:not([href*='people'])");

    private final By basicLocator;

    public ProfileSectionComponent(String section)
    {
        super(section);
        basicLocator = By.cssSelector("[id*='" + StringHelper.toKebabCase(section) + "'] [class*='SectionContent'] > div > div:nth-of-type(1)");
        correctPage(Find.options().locator(basicLocator).clickable(true).returnFirst(true));
    }

    public void verifyThatTableHasValue(String expectedText)
    {
        Verify.isTrue(Find.getTexts(Find.options().locator(By.cssSelector("div")).parentLocator(basicLocator)).contains(expectedText), "Table is not contains value " + expectedText);
    }

    public String experienceName(int index)
    {
        Log.story("Get name of contact person");
        return Find.getText(Find.elements(Find.options().locator(experienceTitle).parentLocator(basicLocator)).get(index));
    }

    public ProfilePage clickOnSection(int index)
    {
        Log.story("Click on of contact person");
        WebElement element = Find.elements(Find.options().locator(experienceTitle).clickable(true).parentLocator(basicLocator)).get(index);
        Find.scrollToElement(element);
        Find.click(element);
        return new ProfilePage();
    }

    public EntityListingPage clickOnAllContacts(int index)
    {
        Log.story("Click on all View all contacts button");
        WebElement element = Find.elements(Find.options().locator(peopleAllContacts).parentLocator(basicLocator).scrollTo(true).clickable(true)).get(index);
        Find.scrollToElement(element);
        Find.click(element);
        return new EntityListingPage(EntityCard.people);
    }
}
