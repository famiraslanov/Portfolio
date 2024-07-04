package com.tests.pageobjects.platform.forms;

import com.library.Find;
import com.library.Log;
import com.library.classes.FindByText;
import com.library.helpers.DateHelper;
import com.tests.enums.platform.ExportDateFormat;
import com.tests.enums.platform.ExportFileFormat;
import com.tests.enums.platform.ExportTemplates;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Date;
import java.util.List;

public class ExportListingForm extends BasePageObject
{
    private By pageLoadIdentifier = By.cssSelector("[class*='ExportTabPanel'][tabindex='0']");
    private By closeButton = By.id("dualModal-close");
    private By exportDataButton = By.id("dualModal-submit");
    private By exportTemplateDropdown = By.cssSelector("[aria-label='Export template']");
    private By createCustomTemplateButton = By.cssSelector("[class*='CreateCustomWrapper'] > button");
    private By exportTemplateElementButton = By.cssSelector("[class*='DefinitionItemName']");
    private By dataFormatButton = By.cssSelector("input[name='date-format'] + label");
    private By fileFormatButton = By.cssSelector("input[name='file-format'] + label");
    private By monthFromDropdown = By.cssSelector("[aria-label='Range Start Month']");
    private By yearFromDropdown = By.cssSelector("[aria-label='Range Start Year']");
    private By monthToDropdown = By.cssSelector("[aria-label='Range End Month']");
    private By yearToDropdown = By.cssSelector("[aria-label='Range End Year']");
    private By customTemplateNameField = By.cssSelector("[class*='DualModalstyled__LeftSideContent'] section > strong + input");
    private By optionSectionsToggle = By.cssSelector("h5[data-testid*='howMoreButton-showButton']");
    private By optionCheckboxes = By.cssSelector("[class*='ExportFieldName']");
    private By saveCustomLayoutButton = By.cssSelector("[data-testid='exportModal-additionalAcionts-exportBtn']");
    private By dropdownOption = By.cssSelector("[aria-label='Export template'] [role='option']");
    private By dropdownDateOption = By.cssSelector("[data-testid*='select-option']");
    private By removeCustomTemplateIcon = By.cssSelector("[data-icon='delete']");
    private By editCustomTemplateIcon = By.cssSelector("[data-icon='edit']");
    private By removeSelectedCustomOptionsLabel = By.cssSelector("[data-testid*='pillBoxRemoveBtn']");

    public ExportListingForm()
    {
        super();
        correctPage(pageLoadIdentifier);
    }

    public void selectTemplateByName(ExportTemplates templates)
    {
        Log.story("Select export template by name: " + templates.value);
        Find.click(exportTemplateDropdown);
        Find.click(Find.options().locator(dropdownOption).findByText(FindByText.by().contains(templates.value)));
    }

    public void createCustomExportTemplate(String templateName, List<String> options)
    {
        Log.story("Click on Create custom layout button and create new template: " + templateName);
        Find.click(exportTemplateDropdown);
        Find.click(createCustomTemplateButton);
        Find.insert(customTemplateNameField, templateName);

        List<WebElement> sections = Find.elements(optionSectionsToggle);
        for (WebElement section : sections) {
            Find.click(section);
        }
        for (String option : options) {
            Find.click(Find.options().locator(optionCheckboxes).findByText(FindByText.by().startsWith(option)));
        }
        Find.click(saveCustomLayoutButton);
    }

    public void selectDateFormat(ExportDateFormat exportDateFormat)
    {
        Log.story("Select Date format: " + exportDateFormat);
        Find.click(Find.options().locator(dataFormatButton).findByText(FindByText.by().contains(exportDateFormat.buttonText)));
    }

    public void selectFileFormat(ExportFileFormat exportFileFormat)
    {
        Log.story("Select File export format: " + exportFileFormat);
        Find.click(Find.options().locator(fileFormatButton).findByText(FindByText.by().contains(exportFileFormat.text)));
    }

    public void selectPerformancePeriod(Date from, Date to)
    {
        Log.story("Select Performance Period from " + from + " to " + to);

        selectPeriod(yearFromDropdown, DateHelper.getYear(from));
        selectPeriod(monthFromDropdown, DateHelper.getMonth(from));

        selectPeriod(yearToDropdown, DateHelper.getYear(to));
        selectPeriod(monthToDropdown, DateHelper.getMonth(to));
    }

    private void selectPeriod(By fieldToSelect, String contains)
    {
        Find.click(Find.options().locator(fieldToSelect).clickable(true));
        Find.click(Find.options().locator(dropdownDateOption).clickable(true).parentLocator(fieldToSelect).findByText(FindByText.by().contains(contains)));
    }

    public void clickExportButton()
    {
        Log.story("Click Export button");
        Find.click(exportDataButton);
    }

    public void deleteCustomTemplate(String template)
    {
        Log.story("Delete custom template");
        Find.click(exportTemplateDropdown);
        Find.click(Find.options().locator(removeCustomTemplateIcon)
                .parentOption(Find.options().locator(dropdownOption).findByText(FindByText.by().contains(template)))
                .clickable(true)
                .scrollTo(true)
                .checkForNoSpinner(true));
    }

    public void editCustomTemplate(String template, String newTemplate, List<String> options)
    {
        Log.story("Delete custom template");
        Find.click(exportTemplateDropdown);
        Find.click(Find.options().locator(editCustomTemplateIcon)
                .parentOption(Find.options().locator(dropdownOption).findByText(FindByText.by().contains(template))));

        for (int i = 0; i < Find.elements(removeSelectedCustomOptionsLabel).size(); i++) {
            Find.click(Find.options().locator(removeSelectedCustomOptionsLabel).returnFirst(true).clickable(true));
        }

        Find.insert(customTemplateNameField, newTemplate);
        List<WebElement> sections = Find.elements(optionSectionsToggle);
        for (WebElement section : sections) {
            Find.click(section);
        }

        for (String option : options) {
            Find.click(Find.options().locator(optionCheckboxes).findByText(FindByText.by().startsWith(option)));
        }
        Find.click(Find.options().locator(saveCustomLayoutButton).checkForNoSpinner(true));
    }

    public void closePopup()
    {
        Log.story("Click on close form button");
        Find.click(closeButton);
    }
}
