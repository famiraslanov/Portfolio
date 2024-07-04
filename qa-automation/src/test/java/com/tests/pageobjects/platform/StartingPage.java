package com.tests.pageobjects.platform;

import com.library.Find;
import com.library.FindOptions;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.library.helpers.DateHelper;
import com.library.helpers.Function;
import com.library.helpers.NumberHelper;
import com.tests.enums.platform.*;
import com.tests.helpers.platform.SearchApiHelper;
import com.tests.pageobjects.baseobjects.BasePageObject;
import com.tests.pageobjects.platform.forms.FollowFeedForm;
import com.tests.pageobjects.platform.forms.GlobalSearchForm;
import com.tests.pageobjects.platform.forms.TopNavigationMenu;
import com.tests.pageobjects.platform.forms.WatchlistForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class StartingPage extends BasePageObject
{
    private By spinner = By.cssSelector("[data-testid='spinner']");
    private By articleBlock = By.cssSelector("[data-testid*='signalCard']");
    private By articlesWithProfile = By.xpath("//div[contains(@data-testid, 'signalCard') and (.//a[contains(@data-testid, 'entityLink')]  and .//a[contains(@data-testid, 'signal-articleLink')])]");
    private By articlesHeadlines = By.xpath("//div[contains(@data-testid, 'signalCard') and .//span[contains(@class, 'FeedTypeText')]]//*[contains(@data-testid, 'article-headline')]");
    private By headlines = By.cssSelector("[data-testid*='article-headline']");
    private By articles = By.xpath("//div[contains(@data-testid, 'signalCard') and .//a[contains(@data-testid, 'signal-articleLink')] and .//*[contains(@class, '_TagList-sc-')]]");
    private By signals = By.cssSelector("[data-testid*='signalCard']:not(:has([class*='FeedTypeText']))");
    private By signalContent = By.cssSelector("[class*='SignalCardstyled__SignalContent']");
    private By articleFullLink = By.cssSelector("[data-testid*='signal-articleLink']");
    private By articleLinks = By.cssSelector("[data-testid*='signalCard']:not(:has([href*='hfa-'])) [data-testid='signal-articleLink']");
    private By articleProfileLinks = By.cssSelector("a[data-testid*='entityLink']");
    private By myWatchlistButton = By.cssSelector("[data-testid='followSection-followSectionAction']");
    private final By followPopupHolder = By.cssSelector("[data-testid='follow-popup-content']");
    private final By followArticleButtons = By.cssSelector("[data-testid='followEntity-followBtn']");
    private final By sortListingDropdown = By.cssSelector("[data-testid='sorting-dropdown']");
    private final By mostRecentOrderItem = By.cssSelector("[data-testid='dropdown-item-Most recent']");
    private final By mostRelevantOrderItem = By.cssSelector("[data-testid='dropdown-item-Most relevant']");
    private final By dateOfArticleLabel = By.cssSelector("[data-testid*='article-date']");
    private final By globalSearchInput = By.cssSelector("[data-testid='globalSearch-input']");
    private final By articleTagsLabel = By.cssSelector("[class*='Tagstyled__Text']");
    private final By articleFollowingPopupButton = By.cssSelector("[data-testid='followEntity-followBtn']");
    private final By portfolioCardBlock = By.cssSelector("[class*='ProfileCardstyled__Content']");
    private final By goWatchlistButton = By.cssSelector("[class*='ArticleListingstyled__WatchListCardWrapper'] button");
    private final By categoryMenuList = By.cssSelector("[class*='CategoryFilterDropdownstyled__Wrapper']");
    private final By categoryMenuItems = By.cssSelector("[class*='Folderstyled__DropdownItem']");
    private final By runAssistedSearches = By.cssSelector("[data-gtm-action='announcement']");
    private final FindOptions followTagOptions = Find.options().locator(By.cssSelector("[data-testid='tag']")).parentLocator(followPopupHolder).visible(true);
    private final FindOptions followSubmitButton = Find.options().locator(By.cssSelector("button")).parentLocator(followPopupHolder).findByText(FindByText.by().equals("Submit"));

    private AssetClass selectedAssetClass;

    public StartingPage(AssetClass assetClass)
    {
        super();
        correctPage(assetClass.startingPageLocator);
        selectedAssetClass = assetClass;
        Verify.isNotFound(spinner, "Spinner still seen");
    }

    public void primaryNavigationItem(PrimaryNavigationItem primaryNavigationItem)
    {
        Log.story("Select primary navigation from the header: " + primaryNavigationItem);
        Find.click(primaryNavigationItem.findOptions);
        Verify.isNotFound(spinner, "Spinner is still shown");
    }

    public void selectSecondaryNavigation(NavigationItem navigationItem)
    {
        Log.story("Select secondary navigation: " + navigationItem);
        Find.click(navigationItem.getFindOptions());
        Find.hover(PrimaryNavigationItem.explore.findOptions);
    }

    public void selectThirdLevelNavigation(ThirdLevelNavigationItem thirdLevelNavigationItem)
    {
        Log.story("Select third level navigation: " + thirdLevelNavigationItem);
        Find.click(categoryMenuList);
        Find.click(Find.options().locator(categoryMenuItems).findByText(FindByText.by().equals(thirdLevelNavigationItem.text)));
    }

    public ExplorePage navigateToExplore(AssetClass assetClass)
    {
        return TopNavigationMenu.getInstance().navigateToExplore(assetClass);
    }

    public EventsPage navigateToEvents()
    {
        TopNavigationMenu.getInstance().navigateToEvent();
        Function.switchWindow(null);
        return new EventsPage();
    }

    public SettingPage goToSettingPage()
    {
        return TopNavigationMenu.getInstance().navigateToSettings();
    }

    public void checkForSavedList(String listName)
    {
        Verify.isFound(Find.options().locator(By.cssSelector("button[data-rowname=\"" + listName + "\"]")).scrollTo(true), "List not found: " + listName);
    }

    public PlatformArticlePage goToFirstArticle()
    {
        Log.story("Click on Full article link for selected article");
        Find.click(Find.options().locator(articleLinks).scrollTo(true).returnFirst(true));
        return new PlatformArticlePage(selectedAssetClass);
    }

    public PlatformArticlePage goToArticle(String title)
    {
        Log.story("Click on Full article link for selected article");
        Find.hoverClick(Find.options().returnFirst(true).locator(articleLinks).scrollTo(true).parentOption(Find.options().locator(articleBlock).findByText(FindByText.by().contains(title))));
        return new PlatformArticlePage(selectedAssetClass);
    }

    public String getArticleWithTags(AssetClass assetClass)
    {
        Log.story("Go to article with tag which user can follow");
        String articleTitle = null;
        List<String> articles = getArticlesText();
        for (String article : articles) {
            PlatformArticlePage platformArticlePage = this.goToArticle(article);
            if (!platformArticlePage.getNotNavigationTags(assetClass).isEmpty()) {
                articleTitle = platformArticlePage.getTitle();
                primaryNavigationItem(PrimaryNavigationItem.now);
                Find.hover(PrimaryNavigationItem.explore.findOptions);
                break;
            } else {
                Log.debug("Return back to select new Article with tags");
                primaryNavigationItem(PrimaryNavigationItem.now);
                Find.hover(PrimaryNavigationItem.explore.findOptions);
            }
        }
        return articleTitle;
    }

    public String getLinkedEntityType(String title)
    {
        Log.story("Get type of linked entity to article");
        return Find.getText(Find.options().locator(articleProfileLinks)
                .parentOption(Find.options().locator(articleBlock).findByText(FindByText.by().contains(title)))).replace(" profile", "").toLowerCase();
    }

    public int getLinkedEntityId(String title)
    {
        Log.story("Get id of linked entity to article");
        return NumberHelper.extractInt(Find.getAttribute(Find.options().locator(articleProfileLinks)
                .parentOption(Find.options().locator(articleBlock).findByText(FindByText.by().contains(title))), "href"));
    }

    public String getFirstArticleWithProfile()
    {
        Log.story("Get first article with linked profile entity");
        for (WebElement article : Find.elements(articlesWithProfile)) {
            WebElement headline = Find.element(Find.options().locator(headlines).parent(article).timeoutMS(300).failOnNotFound(false));
            if (headline != null) {
                return Find.getText(headline);
            }
        }
        Verify.fail("Unable to find an article with a headline");
        return null;
    }

    public String getFirstArticleWithProfile(String type)
    {
        Log.story("Get first article with linked " + type + " profile entity");
        for (WebElement article : Find.elements(articlesWithProfile)) {
            WebElement headline = Find.element(Find.options().locator(headlines).parent(article).timeoutMS(300).failOnNotFound(false));
            if (headline != null) {
                String title = Find.getText(headline);
                if (this.getLinkedEntityType(title).equals(type) && SearchApiHelper.isAvailableEntities(getLinkedEntityId(title), type, selectedAssetClass)) {
                    return title;
                }
            }
        }
        Verify.fail("Unable to find an article with a headline");
        return null;
    }

    public ProfilePage goToArticleLinkedProfile(String articleTitle)
    {
        Log.story("Click on Profile link for selected article");
        Find.hoverClick(Find.options().locator(articleProfileLinks).scrollTo(true).parentOption(Find.options().locator(articleBlock).findByText(FindByText.by().contains(articleTitle))));
        return new ProfilePage();
    }

    public List<WebElement> getArticles()
    {
        Log.story("Get all articles elements");
        return Find.elements(articles);
    }

    public List<String> getArticlesText()
    {
        Log.story("Get all articles text");
        Function.slowEnvironmentWait();
        return Find.getTexts(articlesHeadlines);
    }

    public PlatformArticlePage goToArticle(WebElement article)
    {
        Log.story("Click on Full article link for selected article");
        Find.click(Find.options().locator(articleFullLink).parent(article).scrollTo(true));
        return new PlatformArticlePage(selectedAssetClass);
    }

    public PlatformArticlePage getArticleWithRelatedLinks()
    {
        Log.story("Go to article with related articles link");
        List<String> articles = getArticlesText();
        Log.debug("Articles found: " + articles.size());
        Log.object("Articles", articles);

        for (String article : articles) {
            PlatformArticlePage platformArticlePage = goToArticle(article);
            List<String> relatedArticles = platformArticlePage.getAvailableRelatedArticles(selectedAssetClass);
            if (!relatedArticles.isEmpty()) {
                return platformArticlePage;
            } else {
                Log.debug("Return back to select new Article with links");
                primaryNavigationItem(PrimaryNavigationItem.now);
            }
        }
        Verify.isTrue(false, "No Article page found with related articles");
        return null;
    }

    public void verifyThatArticlesAreDisplayed()
    {
        Log.story("Verify that articles are displayed");
        Verify.isTrue(getArticles().size() > 0, "Articles aren't displayed ");
    }

    public WatchlistForm clickOnMyWatchlist()
    {
        Log.story("Open my watchlist");
        Find.click(Find.options().locator(myWatchlistButton).scrollTo(true).clickable(true));
        return new WatchlistForm(selectedAssetClass);
    }

    public void followAvailableArticles()
    {
        Log.story("Follow article");
        Find.click(Find.options().locator(followArticleButtons).staleRefRetry(true).returnFirst(true).scrollTo(true));
        Verify.isFound(followPopupHolder, "Follow popup not found");
        for (WebElement element : Find.elements(followTagOptions)) {
            Find.click(element);
        }
        Find.click(followSubmitButton);
        Verify.isNotFound(followPopupHolder, "Follow popup still shown");
    }

    public void sortListing(boolean mostRecent)
    {
        Log.story("Click on Most recent order");
        Find.click(sortListingDropdown);
        Find.click(mostRecent ? mostRecentOrderItem : mostRelevantOrderItem);
    }

    public void verifyThatArticlesAreSortedByDesc()
    {
        DateHelper.verifyThatSortedByDateDesc(dateOfArticleLabel);
    }

    public void typeToGlobalSearch(String query)
    {
        Log.story("Type value and search by global search");
        Find.click(globalSearchInput);
        Find.insert(Find.options().locator(globalSearchInput).checkForNoSpinner(true), query);
    }

    public void verifyCorrectGlobalSearchByNow(String headline)
    {
        Log.story("Verify that global search by Now section works correctly");
        GlobalSearchForm globalSearchForm = GlobalSearchForm.getInstance();
        globalSearchForm.verifyThatCardIsDisplayed(headline);
    }

    public void clearSearch()
    {
        Log.story("Click on clear search button");
        GlobalSearchForm globalSearchForm = GlobalSearchForm.getInstance();
        globalSearchForm.clearSearchField();
    }

    public void verifyThatResultsSortedByDateDesc()
    {
        Log.story("Verify that results block arte sorted by date desc");
        GlobalSearchForm globalSearchForm = GlobalSearchForm.getInstance();
        globalSearchForm.verifyThatResultsSortedByDateDesc();
    }

    public void verifyThatAllSearchExploreResultsRelevant(String query)
    {
        Log.story("Verify that all result on all tabs are relevant ot selected search query");
        GlobalSearchForm globalSearchForm = GlobalSearchForm.getInstance();
        globalSearchForm.navigateToExploreTab();
        globalSearchForm.verifyThatAllSearchExploreResultsRelevant(query);
    }

    public List<String> getArticleTags(String title)
    {
        Log.story("Get list of article tags");
        return Find.getTexts(Find.options().locator(articleTagsLabel)
                .parentOption(Find.options().locator(articleBlock).findByText(FindByText.by().contains(title))));
    }

    public <T extends Enum<T> & NavigationItem> void verifyNavigationItemsAreDisplayed(Class<T> enumClass)
    {
        Log.story("Verify that all navigation items are displayed");
        Find.hover(PrimaryNavigationItem.now.findOptions);
        T[] items = enumClass.getEnumConstants();
        for (T item : items) {
            Verify.isFound(item.getFindOptions(), item + " menu is not found");
        }
    }

    private FollowFeedForm clickOnFollowingArticlePopup(String articleTitle)
    {
        Log.story("Click on Following popup for selected article");
        Find.hoverClick(Find.options().locator(articleFollowingPopupButton).scrollTo(true).parentOption(Find.options().locator(articleBlock).findByText(FindByText.by().contains(articleTitle))));
        return new FollowFeedForm();
    }

    public void followTagOnFeed(String articleTitle, String tag)
    {
        Log.story("Click on article tag into Follow popup on feed");
        FollowFeedForm followFeedForm = this.clickOnFollowingArticlePopup(articleTitle);
        followFeedForm.clickOnTag(tag);
        followFeedForm.clickSubmitButton();
    }

    public void clickOnGoWatchlistButton()
    {
        Log.story("Click on go watchlist button");
        Find.click(Find.options().locator(goWatchlistButton).returnFirst(true));
    }

    public ProfilePage goToFirstSignalProfile()
    {
        Log.story("Click on go watchlist button");
        Find.click(Find.options().locator(articleProfileLinks).clickable(true).scrollTo(true).parentOption(Find.options().locator(signals).returnFirst(true)));
        return new ProfilePage();
    }

    public String getFirstSignalContent()
    {
        Log.story("Get signal content");
        return Find.getText(Find.options().locator(signalContent).parentOption(Find.options().locator(signals).returnFirst(true)));
    }

    public void clickOnAssistedSearches()
    {
        Log.story("Get signal content");
        Find.click(runAssistedSearches);
    }

}
