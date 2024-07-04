package com.tests.pageobjects.platform.forms;

import com.library.Find;
import com.library.FindOptions;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.library.helpers.NumberHelper;
import com.tests.enums.platform.EntityCard;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchExploreForm extends BasePageObject
{
    private final By pageLoadIdentifier = By.id("results-container");
    private final By searchResultCard = By.cssSelector("[class*='SearchCardstyles__Wrapper']");
    private final By clearSearchButton = By.cssSelector("#search-section [data-icon='cross']");
    private final By entityTabs = By.cssSelector("div[class*='EntityCardstyles'][label]");
    private final By quantityLabel = By.cssSelector("[class*='Quantitystyled']:not([class*='TotalMin'])");
    private final By upgradePlanLink = By.cssSelector("div[class*='SearchResultUpgradestyled'] > a");
    private final FindOptions resultsFilterTitle = Find.options().locator(By.cssSelector("#results-container>div>div>p")).findByText(FindByText.by().contains("Displaying"));

    public SearchExploreForm()
    {
        super();
        correctPage(pageLoadIdentifier);
    }

    public List<WebElement> getEntityResultsCards()
    {
        Log.story("Return list of results cards");
        return Find.elements(Find.options().locator(searchResultCard).checkForNoSpinner(true));
    }

    public void clearSearchField()
    {
        Log.story("Click on cross button to clear search field");
        Find.click(clearSearchButton);
    }

    public void verifySearchRelevance(String searchQuery)
    {
        searchQuery = searchQuery.toLowerCase();

        Log.story("Check that all search cards contain text");

        Verify.isFound(resultsFilterTitle, "Results filter title not showing");
        List<WebElement> quantityResults = Find.elements(quantityLabel);
        List<WebElement> initialSearchResults = getEntityResultsCards();
        Verify.greaterThan(0, initialSearchResults.size(), "No search results returned");

        for (WebElement quantityElement : quantityResults) {
            int shownEntityQuantity = NumberHelper.extractInt(Find.getText(quantityElement));

            if (shownEntityQuantity != 0) {
                Find.scrollToElement(quantityElement);
                Find.click(quantityElement);

                if (shownEntityQuantity > 30) {
                    int countOfScroll = shownEntityQuantity / 30;

                    while (countOfScroll > 0) {
                        List<WebElement> searchResults = getEntityResultsCards();
                        Find.scrollToElement(searchResults.get(searchResults.size() - 1));
                        countOfScroll--;
                    }
                }
                Find.scrollToElement(quantityElement);
                List<WebElement> searchResults = getEntityResultsCards();
                Verify.isEqual(shownEntityQuantity, searchResults.size(), "Incorrect count of search results.");
            }
        }
    }

    public void clickOnSearchTab(EntityCard entityCard)
    {
        Log.story("Click on search tab for " + entityCard);
        Find.click(Find.options().locator(entityTabs).findByText(FindByText.by().contains(entityCard.value)));
    }

    public void verifyThatResultsIsUnavailable(EntityCard entityCard)
    {
        Log.story("Check that user has not got permission for view results");
        clickOnSearchTab(entityCard);
        Verify.isFound(upgradePlanLink, "Upgrade plan link is not found");
        Verify.isNotFound(searchResultCard, "Result cards are displayed");
    }
}