package com.tests.pageobjects.platform;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.tests.enums.platform.RFR;
import com.tests.enums.platform.SinceInception;
import com.tests.pageobjects.baseobjects.BasePageObject;
import com.tests.pageobjects.platform.forms.StatisticsForm;
import org.openqa.selenium.By;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IndexPage extends BasePageObject
{
    private final By indexTitle = By.cssSelector("[class*='DataStudioViewstyled__IndexTitle']");
    private final By searchField = By.cssSelector("[class*='IndexSearchInputWrapper'] input");
    private final By industryIndexBlock = By.id("dataStudioScrollableSidebarIndustry");
    private final By indexCard = By.cssSelector("[class*='IndexCardstyles__Title']");
    private final By exportButton = By.cssSelector("[class^='GraphCardstyled__ControlsGroup'] [data-gtm-action='export']");
    private final By exportConstituentFundsButton = By.cssSelector(".js-action-container [data-gtm-action='export']");
    private final By monthlyReturnsTable = By.cssSelector("[class*='MonthlyReturnsWrapper']");
    private final By monthlyReturnsTableView = By.cssSelector("[class*='MonthlyReturnsWrapper'] [data-testid='table']");
    private final By monthlyReturnsChartView = By.cssSelector("[class*='MonthlyReturnsWrapper'] [data-testid='graphCard-graph']");
    private final By monthlyReturnsExportButton = By.cssSelector("[class*='MonthlyReturnsWrapper'] button[class*='export-button']");
    private final By chartViewButton = By.id("product-entity-monthly-returns-show-graph");
    private final By returnStatisticTable = By.cssSelector("h6+div+ul:nth-of-type(1)");
    private final By riskStatisticTable = By.cssSelector("h6+div+ul:nth-of-type(2)");
    private final By overviewContainer = By.cssSelector("[class*='OverviewWrapper']");
    private final By spinner = By.cssSelector("[class*='Spinner']");
    private final By listKeyLocator = By.cssSelector("p[class*='ListItemKey']");
    private final By listValueLocator = By.cssSelector("p[class*='ListItemValue']");
    private final By overviewStatsBlock = By.cssSelector("[class*='DataStudiostyled__ReturnContainer']");
    private final By overviewStatsValue = By.cssSelector("[class*='DataStudiostyled__IndexValue']");

    public IndexPage(String indexName)
    {
        super();
        correctPage(Find.options().locator(indexTitle).findByText(FindByText.by().contains(indexName)).checkForNoSpinner(true));
    }

    public IndexPage()
    {
        super();
    }

    public IndexPage selectIndex(String indexName)
    {
        Log.story("Select index card:" + indexName);
        Find.click(Find.options().locator(indexCard).findByText(FindByText.by().equals(indexName)).scrollTo(true));
        return new IndexPage(indexName);
    }

    public void searchByIndex(String indexName)
    {
        Log.story("Search by index name:" + indexName);
        Find.insert(searchField, indexName);
        Verify.isNotFound(spinner, "Spinner is displayed");
    }

    public void exportIndex()
    {
        Log.story("Click on Export button");
        Find.click(exportButton);
    }

    public void exportConstituentFundsIndex()
    {
        Log.story("Click on Export Constituent Funds button");
        Find.click(exportConstituentFundsButton);
    }

    public void exportMonthlyReturns()
    {
        Log.story("Click on Monthly Returns button");
        Find.click(Find.options().locator(monthlyReturnsExportButton).scrollTo(true));
    }

    public void verifyThatFileDownload(String indexName, String fileExtension)
    {
        Verify.isFileDownloaded(indexName, fileExtension);
    }

    public void changeMonthlyReturnsView()
    {
        Log.story("Change Monthly Returns table view from chart/list");
        Find.click(chartViewButton);
    }

    public void verifyThatMonthlyReturnsViewIsTable()
    {
        Log.story("Verify that Monthly Returns is displayed as table");
        Verify.isFound(Find.options().locator(monthlyReturnsTableView), "Monthly Returns is not displayed as table ");
    }

    public void verifyThatMonthlyReturnsViewIsChart()
    {
        Log.story("Verify that Monthly Returns is displayed as chart");
        Verify.isFound(monthlyReturnsChartView, "Monthly Returns is not displayed as chart ");
    }

    public void verifyThatMonthlyReturnsIsNotDisplayed()
    {
        Log.story("Verify that Monthly Returns is not displayed");
        Verify.isNotFound(monthlyReturnsTable, "Monthly Returns is displayed ");
    }

    public void verifyThatMonthlyReturnsIsDisplayed()
    {
        Log.story("Verify that Monthly Returns is not displayed");
        Verify.isFound(Find.options().locator(monthlyReturnsTable).checkForNoSpinner(true), "Monthly Returns is not displayed ");
    }

    public String getFirstIndustryIndicesName()
    {
        Log.story("Find first Industry Indices");
        return Find.getText(Find.options().locator(indexCard).parentLocator(industryIndexBlock).returnFirst(true));
    }

    private Map<String, String> getStatisticTable(By tableLocator, By keyLocator, By valueLocator)
    {
        List<String> keyText = Find.getTexts(Find.options().locator(keyLocator).parentLocator(tableLocator));
        List<String> cellText = Find.getTexts(Find.options().locator(valueLocator).parentLocator(tableLocator));
        return IntStream.range(0, keyText.size()).boxed().collect(Collectors.toMap(keyText::get, cellText::get));
    }

    public void selectSinceInception(SinceInception sinceInception)
    {
        StatisticsForm statisticsForm = new StatisticsForm();
        statisticsForm.selectSinceInception(sinceInception);
    }

    public void selectSinceInception(Date from, Date to)
    {
        StatisticsForm statisticsForm = new StatisticsForm();
        statisticsForm.selectSinceInception(from, to);
    }

    public void selectRfrPercentages(RFR rfr)
    {
        StatisticsForm statisticsForm = new StatisticsForm();
        statisticsForm.selectRfrPercentages(rfr);
    }

    public void verifyThatIndexStatsDataIsDisplayed()
    {
        Log.story("Verify all statistics block are displayed");
        Verify.isFound(overviewContainer, "Overview Statistic is not displayed ");
        Verify.isFound(returnStatisticTable, "Return Statistic is not displayed ");
        Verify.isFound(riskStatisticTable, "Risk Statistic is not displayed ");
    }

    public void verifyThatExportIsDisabled()
    {
        Log.story("Verify that export button is disabled");
        Verify.isNotFound(Find.options().locator(exportConstituentFundsButton).enabled(true), " Export button is disabled");
    }

    public void verifyOverviewTableValueByKey(String key, String expectedValue)
    {
        Log.story("Get value by key from Index Overview Table");
        String value = Find.getText(Find.options().locator(overviewStatsValue).parentOption(Find.options().locator(overviewStatsBlock).findByText(FindByText.by().contains(key))));
        Verify.isEqual(expectedValue, value, key + " value is not equal", true, "ENG-1663", true);
    }

    public String getReturnStatisticTableValue(String key)
    {
        Log.story("Get Return Statistic Table Value");
        Map<String, String> stats = getStatisticTable(returnStatisticTable, listKeyLocator, listValueLocator);
        return stats.get(key);
    }

    public String getRiskStatisticTableValue(String key)
    {
        Log.story("Get Risk Statistic Table Value");
        Map<String, String> stats = getStatisticTable(riskStatisticTable, listKeyLocator, listValueLocator);
        return stats.get(key);
    }
}