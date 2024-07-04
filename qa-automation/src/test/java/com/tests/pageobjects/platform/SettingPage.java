package com.tests.pageobjects.platform;

import com.tests.enums.platform.SettingsLinks;
import com.tests.enums.platform.userSettings.Industry;
import com.tests.enums.platform.userSettings.JobFunction;
import com.tests.enums.platform.userSettings.Seniority;
import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SettingPage extends BasePageObject
{
    private By pageLoadIdentifier = By.cssSelector("[data-testid='template-settingsTitle']");
    private By userNameField = By.cssSelector("[data-testid='settingsField-name']");
    private By emailField = By.cssSelector("[data-testid='settingsField-email']");
    private By phoneField = By.cssSelector("[data-testid='settingsField-phone']");
    private By industryDropdown = By.name("industry");
    private By jobDropdown = By.name("jobFunction");
    private By seniorityDropdown = By.name("seniority");
    private By companyDropdown = By.cssSelector("[class*='Organisationsstyled__Wrapper']");
    private By companyLabel = By.cssSelector("[class*='Organisationsstyled__Wrapper'] p");
    private By companyTextBox = By.cssSelector("[class*='Organisationsstyled'] input");
    private By companyOptions = By.cssSelector("ul[class*='Organisationsstyled__List'] li");
    private By editNameIcon = By.cssSelector("[class*='SettingsFieldstyled__HeaderActions']:has([data-testid='settingsField-name-editBtn']) [data-icon='edit']");
    private By editPhoneIcon = By.cssSelector("[class*='SettingsFieldstyled__HeaderActions']:has([data-testid='settingsField-phone']) [data-icon='edit']");
    private By nameEditField = By.cssSelector("input[name='name']");
    private By phoneEditField = By.cssSelector("input[name='phone']");
    private By saveChangesButton = By.cssSelector("[class*='SettingsFieldstyled__Spacer'] > button[type='submit']");
    private By alertMessageLabel = By.cssSelector("[class*='UserMessagestyled__MessageContent']");
    private By generalSaveButton = By.name("save");
    private By newCompanyOptions = By.cssSelector("li[class^='Organisationsstyled_']");

    public SettingPage()
    {
        super();
        correctPage(pageLoadIdentifier);
    }

    public void goToLink(SettingsLinks settingsLinks)
    {
        Log.story("Navigate to link: " + settingsLinks);
        Find.click(settingsLinks.findOptions);
    }

    public void verifyThatLinkIsNotDisplayed(SettingsLinks settingsLinks)
    {
        Log.story("Verify that navigation setting link is not displayed: " + settingsLinks);
        Verify.isNotFound(settingsLinks.findOptions, settingsLinks.name + " navigation setting link is not displayed");
    }

    public String getUserName()
    {
        return Find.getText(userNameField);
    }

    public String getPhone()
    {
        return Find.getText(phoneField);
    }

    public void editUserData(String name, String phone, Industry industry, JobFunction jobFunction, Seniority seniority, String notCompany)
    {
        Log.story("Edit user data");
        Find.hoverClick(Find.options().locator(editNameIcon));
        Find.insert(Find.options().locator(nameEditField).clearFirst(true), name);
        Find.hoverClick(Find.options().locator(editPhoneIcon));
        Find.insert(Find.options().locator(phoneEditField).clearFirst(true), phone);

        Find.click(industryDropdown);
        Find.select(industryDropdown, industry.name);

        Find.click(jobDropdown);
        Find.select(jobDropdown, jobFunction.name);

        Find.click(seniorityDropdown);
        Find.select(seniorityDropdown, seniority.name);

        if (Find.element(Find.options().locator(companyDropdown).failOnNotFound(false)) != null) {
            Find.click(companyDropdown);
            String newCompany = Find.getTexts(Find.options().locator(newCompanyOptions).scrollTo(true).maxWaitMS(20000)).stream().filter(c -> !c.equals(notCompany)).findFirst().get();
            Find.insert(companyTextBox, newCompany);
            Find.click(Find.options().locator(companyOptions).returnFirst(true).clickable(true));
        }

        Find.click(generalSaveButton);
        Verify.isFound(alertMessageLabel, "Alert message is not displayed");
    }

    public String getCompany()
    {
        WebElement element = Find.element(Find.options().locator(companyLabel).failOnNotFound(false).maxWaitMS(500));
        if (element != null) {
            return Find.getText(Find.options().locator(companyLabel));
        }
        return null;
    }

    public JobFunction getJob()
    {
        return JobFunction.getEnumByName(Find.getSelectValue(jobDropdown));
    }

    public Seniority getSeniority()
    {
        return Seniority.getEnumByName(Find.getSelectValue(seniorityDropdown));
    }

    public Industry getIndustry()
    {
        return Industry.getEnumByName(Find.getSelectValue(industryDropdown));
    }

    public void verifyUserData(String expectedName, String expectedPhone, Industry expectedIndustry, JobFunction expectedJobFunction, Seniority expectedSeniority, String expectedNotCompany)
    {
        Log.story("Verify user data");
        Verify.isEqual(expectedName, getUserName(), "User name is not equal");
        Verify.isEqual(expectedPhone, getPhone(), "Phone name is not equal");
        Verify.isEqual(expectedIndustry.name, getIndustry().name, "Industry is not equal");
        Verify.isEqual(expectedJobFunction.name, getJob().name, "Job is not equal");
        Verify.isEqual(expectedSeniority.name, getSeniority().name, "Serenity is not equal");

        String current = getCompany();
        if (current != null) {
            Verify.isTrue(!current.equals(expectedNotCompany), "Company is not different");
        }
    }

}
