package com.tests.pageobjects.platform;

import com.library.Find;
import com.library.FindOptions;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.tests.enums.platform.EntityCard;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

public class PerformancePage extends BasePageObject
{
    private final By pageLoadIdentifier = By.cssSelector("div[type='secondary'] [class*='CardSectionstyled__Body']");
    private final By cardBlock = By.cssSelector("[class*='Cardstyled__Wrapper']");
    private final By savedReportsBlocks = By.cssSelector("a[class*='SavedReportCardstyled__Wrapper']");
    private final By deleteReportButton = By.cssSelector("div[class*='SavedReportCardstyled__ActionList']:has([data-testid='saved-report-card-delete-btn'])");
    private final By confirmDeleteButton = By.cssSelector("[data-testid='saved-report-card-submit-btn']");

    public PerformancePage()
    {
        super();
        Log.notRunIfFlagIfDisabled("CEFP_BENCHMARK", Find.options().locator(pageLoadIdentifier));
        correctPage(pageLoadIdentifier);
    }

    public EntityListingPage clickOnFundPerformanceButton()
    {
        clickOnPerformanceMoveButton("Fund performance");
        return new EntityListingPage(EntityCard.fundPerformance);
    }

    public EntityListingPage clickOnIndustryBenchmarksButton()
    {
        clickOnPerformanceMoveButton("Industry benchmarks");
        return new EntityListingPage(EntityCard.industryBenchmarks);
    }

    public EntityListingPage clickOnCustomBenchmarksButton()
    {
        clickOnPerformanceMoveButton("Custom benchmarks");
        return new EntityListingPage(EntityCard.customBenchmarks);
    }

    private void clickOnPerformanceMoveButton(String section)
    {
        Log.story("Click on : " + section + " button");
        Find.hoverClick(Find.options().locator(By.cssSelector("button")).parentOption(Find.options().locator(cardBlock).findByText(FindByText.by().contains(section))));
    }

    public void verifyThatSavedReportIsDisplayed(String reportName, boolean isDisplayed)
    {
        Log.story("Verify that : " + reportName + " is displayed: " + isDisplayed);
        FindOptions report = Find.options().locator(savedReportsBlocks).findByText(FindByText.by().contains(reportName));
        if (isDisplayed) {
            Verify.isFound(report, "Report is not displayed");
        } else {
            Verify.isNotFound(report, "Report is still displayed");
        }
    }

    public void deleteReport(String reportName)
    {
        Log.story("Delete report : " + reportName);
        Find.hoverClick(Find.options().locator(deleteReportButton)
                .parentOption(Find.options().locator(savedReportsBlocks).findByText(FindByText.by().contains(reportName))));
        Find.click(confirmDeleteButton);
    }
}