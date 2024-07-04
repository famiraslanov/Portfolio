package com.tests.pageobjects.egr;

import com.tests.helpers.egr.ArticlesHelper;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

public class EventsPage extends BasePageObject
{
    private By pageLoadIdentifier = By.cssSelector("div[class='section-filter-item'] h1");
    private final By articlesBlocks = By.cssSelector("article[class*='card-post']");
    private final By articleTags = By.cssSelector("span[class*='card-post__sub-top-type']");
    private final By categoryTagLabels = By.cssSelector("[class='buttons-container'] > div[class='button-wrapper']");

    public EventsPage()
    {
        super();
        correctPage(pageLoadIdentifier);
    }

    public void verifyThatAllCategoryTagsContainsArticle()
    {
        ArticlesHelper.verifyThatAllCategoryTagsContainsArticle(categoryTagLabels, articlesBlocks, articleTags);
    }
}
