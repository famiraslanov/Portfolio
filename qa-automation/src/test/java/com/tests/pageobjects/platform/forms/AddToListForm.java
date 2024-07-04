package com.tests.pageobjects.platform.forms;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

import java.util.List;

public class AddToListForm extends BasePageObject
{
    private final By pageLoadIdentifier = By.cssSelector("button[id='dualModal-submit']");
    private final By closeButton = By.id("dualModal-close");
    private final By saveListButton = By.id("dualModal-submit");
    private final By savedListNameField = By.cssSelector("input[placeholder='Enter name here']");
    private final By createCustomIndexToggle = By.cssSelector("[class*='SaveAsIndexContainer'] > label");
    private final By saveAsNewIndexRadiobutton = By.cssSelector("div[data-testid='radio-group'] label:nth-child(1)");
    private final By saveAsExtendedIndexRadiobutton = By.cssSelector("div[data-testid='radio-group'] label:nth-child(2)");
    private final By createdListsLabels = By.cssSelector("[class*='EntityListModalstyled__SavedList'] [class*='Checkboxstyled__LabelText']");

    public AddToListForm()
    {
        super();
        correctPage(pageLoadIdentifier);
    }

    public void enterSavedFilterName(String name)
    {
        Log.story("Enter new name for saved filter");
        Find.insert(savedListNameField, name);
    }

    public void saveNewList()
    {
        Log.story("Click on Save list button");
        Find.click(saveListButton);
    }

    public void clickOnCustomIndexModeToggle()
    {
        Log.story("Click on Custom index mode toggle");
        Find.click(createCustomIndexToggle);
    }

    public void clickOnSaveNewListMode()
    {
        Log.story("Click on save new list mode button");
        Find.click(saveAsNewIndexRadiobutton);
    }

    public void clickOnUpdateListMode()
    {
        Log.story("Click on update list mode button");
        Find.click(saveAsExtendedIndexRadiobutton);
        Verify.isFound(Find.options().locator(createdListsLabels).returnFirst(true), "No lists for update");
    }

    public void selectCreatedListForUpdating(List<String> createdLists)
    {
        Log.story("Select saved lists for updating");
        for (String createdList : createdLists) {
            Find.click(Find.options().locator(createdListsLabels).scrollTo(true).findByText(FindByText.by().contains(createdList)));
        }
    }

    public void verifyNoToggleForCreateSavedList()
    {
        Log.story("Verify that custom saved list/benchmark is not displayed");
        Verify.isNotFound(createCustomIndexToggle, "Toggle for creation custom saved list/benchmark is displayed");
    }

    public void closeSaveListForm()
    {
        Log.story("Click on Cancel button to close Add save list form");
        Find.click(closeButton);
    }
}
