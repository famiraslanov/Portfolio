package com.tests.pageobjects.platform.forms;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.library.helpers.DateHelper;
import com.library.helpers.Function;
import com.library.helpers.HtmlSourceHelper;
import com.library.helpers.NumberHelper;
import com.tests.pageobjects.baseobjects.BasePageObject;
import com.tests.pageobjects.platform.ProfilePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

public class GlobalSearchForm extends BasePageObject
{
    private By pageLoadIdentifier = By.id("results-wrapper");
    private By tabButtons = By.cssSelector("button[role='tab']");
    private By searchResultCard = By.cssSelector("[class*='SearchCardstyles__Wrapper']");
    private By clearSearchButton = By.cssSelector("[class*='mastheadContent-globalSearchWrapper'] div:nth-child(2) > span[data-icon='cross']");
    private By dateOfItemLabel = By.cssSelector("[class*='TagItemstyled'] > [class*='DateTagstyled']");
    private By countItemsLabel = By.cssSelector("p[data-testid='globalsearch-secondary-tab-badge']");
    private By exploreSearchPanel = By.cssSelector("[data-testid='tabsHandlers-tabPanel-exploreResults']");
    private By resultSearchName = By.cssSelector("[class*='ListItemHighlighterstyled']");
    private By noResultsFoundMessage = By.cssSelector("[class*='InfiniteListNotFound']");
    private static GlobalSearchForm instance;

    private GlobalSearchForm()
    {
        super();
        correctPage(Find.options().locator(pageLoadIdentifier).timeoutMS(20000));
    }

    public static GlobalSearchForm getInstance()
    {
        if (instance == null) {
            instance = new GlobalSearchForm();
        }
        return instance;
    }

    public void verifyGlobalSearchFormIsDisplayed()
    {
        Verify.isFound(Find.options().locator(searchResultCard).returnFirst(true), "Search form is displayed with results");
    }

    public void verifyResultsNoFound()
    {
        Verify.isFound(Find.options().locator(noResultsFoundMessage), "Search results is found");
    }

    public void navigateToExploreTab()
    {
        Log.story("Navigate to Explore search result tab");
        Find.click(Find.options().locator(tabButtons).findByText(FindByText.by().equals("Explore Results")).checkForNoSpinner(true));
    }

    public List<WebElement> getResultsCard()
    {
        Log.story("Get all visible search result from tab");
        return Find.elements(searchResultCard);
    }

    public void verifyThatCardIsDisplayed(String headline)
    {
        Log.story("Verify that card is displayed in global search result");
        Verify.isFound(Find.options().locator(resultSearchName).findByText(FindByText.by().contains(headline)), "Article is not found");
    }

    public void clearSearchField()
    {
        Log.story("Click on cross button for close search filed");
        Find.click(Find.options().locator(clearSearchButton));
    }

    public void verifyThatResultsSortedByDateDesc()
    {
        DateHelper.verifyThatSortedByDateDesc(dateOfItemLabel);
    }

    public void verifyThatAllSearchExploreResultsRelevant(String searchTerm)
    {
        List<WebElement> badgeLabels = Find.elements(countItemsLabel);
        for (WebElement badgeLabel : badgeLabels) {
            Find.click(badgeLabel);
            int badgeCount = NumberHelper.extractInt(Find.getText(badgeLabel));
            if (badgeCount > 0) {
                List<WebElement> cardOptions = Find.elements(Find.options().locator(searchResultCard).parentLocator(exploreSearchPanel));
                int maxCheck = 10; // Just check the first {x} records per badge label
                int counter = 0;
                for (WebElement cardOption : cardOptions) {
                    counter++;
                    if (counter < maxCheck) {
                        String name = Find.getText(Find.options().locator(resultSearchName).parent(cardOption)).toLowerCase();
                        try {
                            Verify.isTrue(name.contains(searchTerm.toLowerCase()), name + " - Result card does not contain: " + searchTerm);
                        } catch (AssertionError error) {
                            Log.debug("Search term not found on result card. Loading profile page for result: " + counter);
                            String link = Find.getAttribute(Find.options().locator(By.cssSelector("a")).parent(cardOption), "href");
                            String originalWindow = Function.getOriginalWindow();
                            Find.openLinkInNewTab(link);
                            Function.switchWindow(null);
                            new ProfilePage();
                            Verify.isTrue(HtmlSourceHelper.getPageSource().toLowerCase().contains(searchTerm.toLowerCase()), "Source does not contain query");
                            Function.closeCurrentTab();
                            Function.switchToSelectedWindow(originalWindow);
                        }
                    }
                }
            }
        }
    }

    public void clickOnSearchResult(int index)
    {
        Log.story("Click on selected search result");
        Function.slowEnvironmentWait(Duration.ofSeconds(3));
        Find.click(Find.elements(Find.options().locator(searchResultCard).checkForNoSpinner(true)).get(index));
    }

}
