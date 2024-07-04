package com.tests.pageobjects.platform;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.library.helpers.Function;
import com.library.helpers.NumberHelper;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;

public class TheAllocatePage extends BasePageObject
{
    private By pageLoadIdentifier = By.cssSelector("[class*='Allocatorstyled__SelectorWrapper']");
    private By articleItemButton = By.cssSelector("button[class*='ListItemstyled__Container'] > div > p:nth-of-type(1)");
    private By contentBlock = By.cssSelector("[class*='Allocatorstyled__NewsletterWrapper'] iframe[title]");
    private By contentBody = By.cssSelector("table[class='email-container'] > tbody td > center");
    private By searchField = By.cssSelector("[class*='Allocatorstyled__Search'] input");
    private By countLetterLabel = By.cssSelector("[class*='Toolbarstyled__CountTag']");
    private By countResultLabel = By.cssSelector("[class*='TotalItems']");
    private By printButton = By.cssSelector("[class*='NewsletterContentstyled'] [data-icon='print']");


    public TheAllocatePage()
    {
        super();
        correctPage(pageLoadIdentifier);
    }

    public void clickOnArticleItem(int index)
    {
        Log.story("Click on allocate article link");
        Find.click(Find.elements(articleItemButton).get(index));
    }

    public void clickOnPrint()
    {
        Log.story("Click on allocate print button");
        Function.switchFrame(Find.options().locator(contentBlock));
        Verify.verifyPrint(printButton);
    }

    public void typeInSearch(String query)
    {
        Log.story("Type " + query + " into search filed for search though allocate newsletters");
        Find.insert(searchField, query);
        verifyThatContentBlockIsDisplayed();
        Verify.isFound(Find.options().locator(countResultLabel).findByText(FindByText.by().startsWith("Results for ")), "The search failed");
    }

    public void verifyThatContentBlockIsDisplayed()
    {
        Log.story("Verify that content block is displayed for article");
        Verify.isFound(contentBlock, "Content block is not found ");
    }

    public void verifySearchResultsRelevant(String query)
    {
        Log.story("Relevant that all search result are relevant to search query");
        Function.slowEnvironmentWait(Duration.ofSeconds(2));
        List<String> newsletters = Find.getTexts(Find.options().locator(articleItemButton));
        Log.object("Newsletters", newsletters);

        Verify.isEqual(NumberHelper.extractInt(Find.getText(countLetterLabel)), newsletters.size(), "Number of newsletter found is not equal to the expected one");
        
        for (int i = 0; i < newsletters.size(); i++) {
            Find.click(Find.options().locator(articleItemButton).findByText(FindByText.by().contains(newsletters.get(i))).scrollTo(true).clickable(true).timeoutMS(1000));
            Function.switchFrame(Find.options().locator(contentBlock));
            Verify.containsText(contentBody, query, "Text of newsletter is not contain query: " + query);
            Function.switchDefaultFrame();
        }
    }
}
