package com.tests.enums.platform;

import com.library.Find;
import com.library.FindOptions;
import com.library.classes.FindByText;
import org.openqa.selenium.By;

public enum AssetClass
{
    allocate("Allocate With", "fundmap"),
    hedge("Hedge Funds", "hedgemap"),
    privateEquity("Private Equity", "pefi"),
    privateCredit("Private Credit", "altcredit"),
    realEstate("Real Estate", "refi"),
    traditional("Traditional", "cwi", "tfi"),
    withDirectory("With Directory", By.cssSelector("[title='Investors']"), By.cssSelector("[title='Investors']")),
    infrastructure("Infrastructure", "iwi", "iwi");

    public FindOptions findOptions;
    public By startingPageLocator;
    public By explorePageLoadIdentifier;
    public String websiteApiHeader;
    public String websiteApiHeaderArticle;

    private final By defaultStartingPageLoadIdentifier = By.cssSelector("section[class*='ArticleListingstyled']");
    private final By defaultExplorePageLoadIdentifier = By.cssSelector("[class^='Dashboardstyled__MainContainer-']");
    private final FindOptions baseFindOptions = Find.options().locator(By.cssSelector("[id^='downshift-0-item-']"));

    AssetClass(String text, By startingPageLocator, By explorePageLoadIdentifier)
    {
        this.setup(text, startingPageLocator, explorePageLoadIdentifier, null, null);
    }

    AssetClass(String text, String websiteApiHeader)
    {
        this.setup(text, null, null, websiteApiHeader, null);
    }

    AssetClass(String text, String websiteApiHeader, String websiteApiHeaderArticle)
    {
        this.setup(text, null, null, websiteApiHeader, websiteApiHeaderArticle);
    }

    private void setup(String text, By startingPageLocator, By explorePageLoadIdentifier, String websiteApiHeader, String websiteApiHeaderArticle)
    {
        this.findOptions = baseFindOptions.findByText(FindByText.by().contains(text));
        this.startingPageLocator = startingPageLocator == null ? defaultStartingPageLoadIdentifier : startingPageLocator;
        this.explorePageLoadIdentifier = explorePageLoadIdentifier == null ? defaultExplorePageLoadIdentifier : explorePageLoadIdentifier;
        this.websiteApiHeader = websiteApiHeader;
        this.websiteApiHeaderArticle = websiteApiHeaderArticle == null ? websiteApiHeader : websiteApiHeaderArticle;
    }
}
