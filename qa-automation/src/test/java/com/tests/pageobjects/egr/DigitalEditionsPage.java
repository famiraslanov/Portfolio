package com.tests.pageobjects.egr;

import com.library.Find;
import com.library.FindOptions;
import com.library.Log;
import com.library.Verify;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

public class DigitalEditionsPage extends BasePageObject
{
    private final FindOptions pageLoadIdentifier = Find.options().locator(By.cssSelector(".card-digital-edition")).returnFirst(true);
    private static final By articlesBlocks = By.cssSelector(".card-digital-edition");
    private static final By articleTitle = By.cssSelector(".card-digital-edition__content-title");

    public DigitalEditionsPage()
    {
        super();
        correctPage(pageLoadIdentifier);
    }

    public void verifyThatPageContainsArticle()
    {
        Log.story("Verify that page contains article");
        Verify.isTrue(Find.elements(articlesBlocks).size() > 0, "No articles on page!");
    }

    public EgrArticlePage goToDigitalArticle()
    {
        Log.story("Go to first digital article");
        String title = Find.getText(Find.options().locator(articleTitle).returnFirst(true)).trim();
        Find.click(Find.options().locator(articlesBlocks).returnFirst(true));
        return new EgrArticlePage(title);
    }

}
