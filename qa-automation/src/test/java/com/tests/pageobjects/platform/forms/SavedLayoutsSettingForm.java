package com.tests.pageobjects.platform.forms;

import com.library.helpers.Function;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.EntityCard;
import com.tests.enums.platform.SettingsLinks;
import com.tests.enums.platform.UserLogin;
import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.library.helpers.DateHelper;
import com.tests.pageobjects.baseobjects.BasePageObject;
import com.tests.pageobjects.platform.EntityListingPage;
import com.tests.pageobjects.platform.ExplorePage;
import com.tests.pageobjects.platform.SettingPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

public class SavedLayoutsSettingForm extends BasePageObject
{
    private final By pageLoadIdentifier = By.cssSelector("[class*='SavedLayoutsstyled__Description']");
    private final By entityToggles = By.cssSelector("[class*='SavedLayoutsCardstyled__TitleToggle']");
    private final By layoutItems = By.cssSelector("[class*='CardItemstyled__Header']");
    private final By removeLayoutItemsButton = By.cssSelector("[data-icon='trash']");
    private final By confirmRemoveButton = By.id("confirmation-submit");
    private final By editLayoutItemsButton = By.cssSelector("[data-icon='edit']");
    private final By layoutNameField = By.cssSelector("[class*='CardItemstyled__Textarea']");
    private final By saveChangesLayoutButton = By.cssSelector("button[type='submit']");
    private final By saveLayoutLink = By.cssSelector("a[class*='CardItemstyled__Title']");
    private final By copyLayoutIcon = By.cssSelector("[data-icon='copy']");
    private final By notificationAlertPopup = By.cssSelector("[data-testid*='notification-close-button']");

    private AssetClass selectedAssetClass;

    public SavedLayoutsSettingForm(AssetClass assetClass)
    {
        super();
        correctPage(pageLoadIdentifier);
        selectedAssetClass = assetClass;
    }

    public void clickOnEntityToggle(EntityCard entityCard)
    {
        Log.story("Click on entity toggle: " + entityCard);
        WebElement section = findEntitySection(entityCard);
        if (section == null) {
            createNewSavedLayouts(entityCard);
            Function.sleep(Duration.ofSeconds(5), "Allow time for the new saved layout to appear");
            section = findEntitySection(entityCard);
            Verify.isTrue(section != null, "No saved layouts found for " + entityCard);
        }
        Find.click(section);
    }

    private WebElement findEntitySection(EntityCard entityCard)
    {
        return Find.element(Find.options().locator(entityToggles).findByText(FindByText.by().contains(entityCard.value)).failOnNotFound(false));
    }

    private void createNewSavedLayouts(EntityCard entityCard)
    {
        Log.story("No saved layouts on this account so need to set them up");
        ExplorePage explorePage = TopNavigationMenu.getInstance().navigateToExplore(selectedAssetClass);
        EntityListingPage entityListingPage = explorePage.navigateToEntityListingPage(entityCard);
        entityListingPage.setupLayouts(entityCard.dataPoint1Text, DateHelper.dtInsert("yyMMddHHmmss") + "-Keep-" + entityCard);
        entityListingPage.setupLayouts(entityCard.dataPoint2Text, DateHelper.dtInsert("yyMMddHHmmss") + "-Keep-" + entityCard);

        SettingPage settingPage = TopNavigationMenu.getInstance().navigateToSettings();
        settingPage.goToLink(SettingsLinks.saveLayouts);
    }

    public boolean isDisplayedLayout(String layoutName)
    {
        return Find.element(Find.options().locator(layoutItems).failOnNotFound(false).findByText(FindByText.by().contains(layoutName))) != null;
    }

    public void verifyDisplayedLayout(String layoutName)
    {
        Verify.isFound(Find.options().locator(layoutItems).findByText(FindByText.by().contains(layoutName)), "Saved Filter is not found but should be: " + layoutName);
    }

    public void verifyNotDisplayedLayout(String layoutName)
    {
        Verify.isNotFound(Find.options().locator(layoutItems).findByText(FindByText.by().contains(layoutName)), "Saved Filter is found but should not be: " + layoutName);
    }

    public void deleteLayout(String layoutName)
    {
        Find.click(Find.options().locator(removeLayoutItemsButton).parentOption(Find.options().locator(layoutItems).findByText(FindByText.by().contains(layoutName))));
        Find.click(confirmRemoveButton);
        Function.slowEnvironmentWait();
        Verify.isNotFound(confirmRemoveButton, "Remove button is still seen");
    }

    public void editLayout(String layoutName, String newName)
    {
        Find.click(Find.options().locator(editLayoutItemsButton).
                parentOption(Find.options().locator(layoutItems).findByText(FindByText.by().contains(layoutName))));
        Find.insert(Find.options().locator(layoutNameField), newName);
        Find.insert(layoutNameField, newName);
        Find.click(saveChangesLayoutButton);
    }

    public void copyLayout(String layoutName, UserLogin userLogin)
    {
        Find.click(Find.options().locator(copyLayoutIcon).
                parentOption(Find.options().locator(layoutItems).findByText(FindByText.by().contains(layoutName))));
        CopyForm copyForm = new CopyForm();
        copyForm.copy(userLogin);
    }

    public List<String> getSavedLayout()
    {
        Log.story("Get all saved layout");
        return Find.getTexts(saveLayoutLink);
    }

    public EntityListingPage clickOnSavedLayout(String saveLayoutName, EntityCard entityCard)
    {
        Log.story("Click on saved layout to apply: " + saveLayoutName);
        Find.click(Find.options().locator(saveLayoutLink).findByText(FindByText.by().equals(saveLayoutName)));
        return new EntityListingPage(entityCard);
    }
}
