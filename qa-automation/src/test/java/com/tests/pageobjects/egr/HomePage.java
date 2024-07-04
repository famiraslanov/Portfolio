package com.tests.pageobjects.egr;

import com.library.Find;
import com.library.FindOptions;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.library.enums.Site;
import com.library.helpers.Function;
import com.library.helpers.HtmlSourceHelper;
import com.tests.enums.egr.Region;
import com.tests.helpers.egr.ArticlesHelper;
import com.tests.pageobjects.baseobjects.BasePageObject;
import com.tests.quickwatch.QuickWatchItems;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

public class HomePage extends BasePageObject
{
    private final By loginButton = By.id("logout-button");
    private final By logoutButton = By.id("logout-button");
    private final By emailErrorLoginLabel = By.cssSelector("[x-text*='emailError']");
    private final By passwordErrorLoginLabel = By.cssSelector("[x-text*='passwordError']");
    private final By searchField = By.id("search");
    private final By articlesBlocks = By.cssSelector("article[class*='card-post']");
    private final By articleLink = By.cssSelector("a[class*='card-post__content-title']");
    private final By articleTags = By.cssSelector("span[class*='card-post__top-caption']");
    private final By categoryTagLabels = By.cssSelector("[class='buttons-container'] > div[class='button-wrapper']");
    private final By adsBlocks = By.cssSelector("div[id='ad_container'] img, [id='aw0'] img");
    private final By adsTopFrame = By.cssSelector("div[id='LB1'] iframe[id*='google_ads_iframe']");
    private final By locationButton = By.cssSelector("button[class*='location-dropdown']");
    private final By searchByLocation = By.cssSelector("input[x-model='search']");
    private final By locationCheckboxes = By.cssSelector("label[class='checkbox-wrapper']");
    private final By globalButton = By.cssSelector("button[class*='dropdown-filter--global']");
    private final By filtersDropdownButton = By.cssSelector("button[class*='primary-dropdown-filter']");
    private final By internalFiltersDropdownButton = By.cssSelector("button[class*='secondary-dropdown-filter']");
    private final By filterItems = By.cssSelector("span[class='checkbox-checktext']");


    public HomePage()
    {
        super();
        addQuickWatch(QuickWatchItems.cookieBanner());
        correctPage(loginButton);
    }

    public static HomePage load()
    {
        Log.story("Go to Home page");
        Function.load(Site.egr.url());
        return new HomePage();
    }

    public LoginPage goToLoginPage()
    {
        Log.story("Go to Login page");
        Find.click(loginButton);
        return new LoginPage();
    }

    public void verifyThatUserLoggedIn()
    {
        Log.story("Verify that user successfully login");
        Verify.isFound(logoutButton, "User is not logged in");
    }

    public void verifyIncorrectLoginMessageIsDisplayed()
    {
        Log.story("Verify that incorrect login message is displayed");
        Verify.isFound(emailErrorLoginLabel, "Error message about incorrect email in not displayed");
        Verify.isFound(passwordErrorLoginLabel, "Error message about incorrect password in not displayed");
    }

    public void searchByArticle(String query)
    {
        Log.story("Search by article: " + query);
        Find.insert(searchField, query);
        Find.insert(Find.options().locator(searchField).clearFirst(false), Keys.ENTER);
        Function.slowEnvironmentWait(Duration.ofSeconds(3), "Allow to load search results");
    }

    public void verifyThatSearchResultIsRelevant(String searchTerm)
    {
        Log.story("Verify that all search results relevant to search query");
        List<WebElement> articles = Find.elements(articlesBlocks);
        for (WebElement article : articles) {
            String link = Find.getAttribute(Find.options().locator(articleLink).parent(article), "href");
            String originalWindow = Function.getOriginalWindow();
            Find.openLinkInNewTab(link);
            Function.switchWindow(null);
            EgrArticlePage egrArticlePage = new EgrArticlePage();
            Verify.isTrue(HtmlSourceHelper.getPageSource().toLowerCase().contains(searchTerm.toLowerCase()), "Source does not contain query");
            Function.closeCurrentTab();
            Function.switchToSelectedWindow(originalWindow);
        }
    }

    public void verifyThatAllTagsIsDisplayed()
    {
        Log.story("Verify that all category tags are disposed on page");
        List<String> expectedList = List.of("All", "Sports Betting", "Poker", "Casino", "Lottery", "Bingo");
        Verify.isTrue(expectedList.containsAll(Find.getTexts(categoryTagLabels)), "No all category tags are displayed");
    }

    public void verifyThatAllCategoryTagsContainsArticle()
    {
        ArticlesHelper.verifyThatAllCategoryTagsContainsArticle(categoryTagLabels, articlesBlocks, articleTags);
    }


    public void verifyThatAdsBlocksAreDisplayed()
    {
        Log.story("Verify that ads block is displayed in page");
        Function.switchFrame(Find.options().locator(adsTopFrame));
        Verify.isTrue(Find.elements(adsBlocks).size() > 0, "Ads block is not displayed");
        Function.switchDefaultFrame();
    }

    public EgrArticlePage goToFistArticle()
    {
        Log.story("Go to first article in feed");
        Find.hoverClick(Find.options().locator(articleLink).parent(Find.elements(articlesBlocks).get(0)));
        return new EgrArticlePage();
    }

    public void selectLocation(String location)
    {
        Log.story("Select location for search");
        Find.click(locationButton);
        Find.insert(searchByLocation, location);
        Find.click(Find.options().locator(locationCheckboxes).findByText(FindByText.by().contains(location)));
    }

    public void verifyThatAllArticleContainsSelectedTag(String tag)
    {
        Function.slowEnvironmentWait(Duration.ofSeconds(2), "Allow to load results");
        ArticlesHelper.verifyThatAllArticleContainsSelectedTag(articlesBlocks, articleTags, tag);
    }

    public void selectRegion(Region region)
    {
        Log.story("Select system region");
        Find.click(globalButton);
        Find.click(region.findOptions);
    }

    public void verifyThatPageContainsArticle()
    {
        Log.story("Verify that page contains article");
        Verify.isTrue(Find.elements(articlesBlocks).size() > 0, "No articles on page!");
    }

    public void verifyWidthOfColumns()
    {
        Log.story("Verify that size of columns is equal");
        List<WebElement> articles = Find.elements(articlesBlocks);
        if (articles.size() >= 2) {
            Verify.isTrue(articles.get(0).getSize().getWidth() == articles.get(1).getSize().getWidth(), "Size of 1 and 2 columns is not equal");
        }
        if (articles.size() >= 3) {
            Verify.isTrue(articles.get(2).getSize().getWidth() == articles.get(3).getSize().getWidth(), "Size of 2 and 3 columns is not equal");
        }
    }

    public void selectFilter(String filterName, String value)
    {
        Log.story("Select [" + filterName + "] filter with value [" + value + "]");
        Find.click(filtersDropdownButton);
        FindOptions secondFilterButton = Find.options().locator(internalFiltersDropdownButton).findByText(FindByText.by().contains(filterName.toLowerCase()));
        Find.click(secondFilterButton);
        Find.click(Find.options().locator(filterItems).findByText(FindByText.by().contains(value.toLowerCase())));
        Verify.isFound(Find.options().locator(articleLink).returnFirst(true), "Article is not found");
    }
}
