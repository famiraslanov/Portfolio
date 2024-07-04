package com.tests.pageobjects.egr;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class EgrArticlePage extends BasePageObject
{
    private final By pageLoadIdentifier = By.className("article-title");
    private final By loginForm = By.cssSelector("[class='content-login-popup-wrapper']");
    private final By articleContent = By.cssSelector("[class='article-content']");
    private final By articleHeadline = By.cssSelector("[class='article-title'] h1");
    private final By viewDigitalEditionButton = By.cssSelector(".author-wrap-right a");
    private final By relatedArticleLinks = By.cssSelector("[class*='content-sidebar'] a");

    public EgrArticlePage()
    {
        super();
        correctPage(pageLoadIdentifier);
    }

    public EgrArticlePage(String title)
    {
        super();
        correctPage(pageLoadIdentifier);
        Verify.isEqual(title, Find.getText(articleHeadline), title + "title is not found");
    }

    public void verifyViewLoginForm()
    {
        Log.story("Verify that login form is displayed");
        Verify.isNotFound(articleContent, "Article content is displayed");
        Verify.isFound(loginForm, "Login Form is not displayed");
    }

    public void verifyContentIsDisplayed()
    {
        Log.story("Verify that content is displayed");
        Verify.isFound(articleContent, "Article content is displayed");
    }

    public CloudBookPage clickOnViewDigitalEditionButton()
    {
        Log.story("Click on View Digital Edition button");
        Find.click(viewDigitalEditionButton);
        return new CloudBookPage();
    }

    public EgrArticlePage goToFirstRelatedArticleLink()
    {
        Log.story("Click on first article related link");
        WebElement link = Find.element(Find.options().locator(relatedArticleLinks).returnFirst(true));
        String title = Find.getText(link);
        Find.click(link);
        return new EgrArticlePage(title);
    }
}
