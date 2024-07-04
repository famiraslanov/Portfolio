package com.tests.pageobjects.platform.forms;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.helpers.Function;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.openqa.selenium.By;

public class RankToolForm extends BasePageObject
{
    private final By pageLoadIdentifier = By.id("fundRankingTool");
    private final By detailsDropdown = By.cssSelector("[name='int_cat_search']");
    private final By detailsDropdownOptions = By.cssSelector("#int_cat_search option");
    private final By detailsMainInfo = By.id("detaildiv");
    private final By printButton = By.id("fund-ranking-print");
    private final By contentChartBlocks = By.cssSelector("[class*='content_charttitle']");

    public RankToolForm()
    {
        super();
        correctPage(Find.options().locator(pageLoadIdentifier));
    }

    public void verifyThatRankingToolIsOpened()
    {
        Log.story("Verify that all elements from ranking tool are displayed");
        Function.switchFrame(Find.options().locator(pageLoadIdentifier));
        if (Find.element(Find.options().locator(detailsMainInfo).failOnNotFound(false)) == null) {
            Log.knownIssue("Reported. Known flakey external implementation", Function.getStackTrace());
        }

        Verify.isFound(detailsMainInfo, "Fund info block is not found");
        Verify.isFound(detailsDropdown, "Details dropdown is not found");
        Verify.isFound(printButton, "Print button is not found");
        Verify.isTrue((long) Find.elements(contentChartBlocks).size() > 0, "Chart blocks aren't found");
        Function.switchDefaultFrame();
    }
}
