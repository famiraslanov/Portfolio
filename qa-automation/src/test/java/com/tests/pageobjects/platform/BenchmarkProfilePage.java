package com.tests.pageobjects.platform;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.library.helpers.*;
import com.tests.enums.platform.EntityCard;
import com.tests.enums.platform.ExportFileFormat;
import com.tests.pageobjects.baseobjects.BasePageObject;
import com.tests.pageobjects.platform.forms.AddToListForm;
import com.tests.pageobjects.platform.forms.EditBenchmarkForm;
import com.tests.pageobjects.platform.forms.SaveFilterForm;
import com.tests.pageobjects.platform.forms.SavedLayoutsSettingForm;
import com.tests.pageobjects.platform.forms.components.SingleTable;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

public class BenchmarkProfilePage extends BasePageObject
{
    private final EntityCard selectedEntity;
    private final By pageLoadIdentifier = By.cssSelector("[class*='BenchmarkProfilestyled__Body']");
    private final By profileHeadline = By.cssSelector("[class*='Toolbarstyled__Title']");
    private final By tabLinks = By.cssSelector("[class*='Navigationstyled__TabList'] a");
    private final By countFundsLabel = By.cssSelector("[class*='Metricsstyled__ItemValue']");
    private final By benchmarkToggle = By.cssSelector("[class*='Sidebarstyled__GroupList'] div[class*='Groupstyled__Wrapper']");
    private final By noResultsLabel = By.cssSelector("[data-testid=\"table-noResults\"]");
    private final By saveAsReportButton = By.cssSelector("div[class*='SaveAsReportstyled']");

    public BenchmarkProfilePage(EntityCard entityCard)
    {
        super();
        selectedEntity = entityCard;
        correctPage(Find.options().locator(pageLoadIdentifier).checkForNoSpinner(true));
    }

    public void verifyThatSelectedProfileIsOpen(String expected)
    {
        Log.story("Verify that open profile page: " + expected);
        Function.slowEnvironmentWait(Duration.ofSeconds(2));
        Verify.contains(getProfileTitle().toLowerCase(), expected.toLowerCase(), "Opened profile does not have the correct title");
    }

    public String getProfileTitle()
    {
        Log.story("Get profile name");
        return Find.getText(Find.options().locator(profileHeadline).staleRefRetry(true));
    }

    public void goToTab(String tabName)
    {
        Log.story("Open " + tabName + " tab on Benchmark page");
        Find.click(Find.options().locator(tabLinks).findByText(FindByText.by().equals(tabName)));
    }

    public void verifyBenchmarkStatisticInTableRow(String rowValue, List<String> expectedStats)
    {
        Log.story("Verify statistics in Benchmark overview for value row = " + rowValue);
        goToTab("Benchmark overview");
        SingleTable benchmarkOverviewTable = new SingleTable();
        int valueColumnIndex = benchmarkOverviewTable.getColumnValues("Value").indexOf(rowValue);
        List<String> actualValues = benchmarkOverviewTable.getRowValues(valueColumnIndex + 1);
        IntStream.range(0, expectedStats.size())
                .forEach(i -> Verify.isEqual(actualValues.get(i + 1), expectedStats.get(i),
                        "Statistics value is not equal: " + actualValues.get(i) + "!=" + expectedStats.get(i)));
    }

    public void verifyCountOfConstituentFunds(String actualCount)
    {
        Log.story("Verify count of Constituent funds");
        Verify.isNotFound(Find.options().locator(noResultsLabel), "No Results found for benchmark");
        String expectedCount = Find.getText(countFundsLabel);
        Verify.isEqual(expectedCount, actualCount, "Count of Constituent Funds is not equals");
    }

    public String getInvestorName(int index)
    {
        SingleTable investorsTable = new SingleTable();
        return investorsTable.getColumnValue(index, "Investor name");
    }

    public String getInvestorFunds(String investor)
    {
        SingleTable investorsTable = new SingleTable();
        investorsTable.typeSearchQuery(investor);
        return StringUtils.substringAfter(investorsTable.getColumnValues("Fund name").get(0), "\n");
    }

    public ProfilePage clickOnInvestorLink(String investor)
    {
        SingleTable investorsTable = new SingleTable();
        investorsTable.typeSearchQuery(investor);
        investorsTable.clickOnCellLinkInRow(0, "Investor name");
        return new ProfilePage();
    }

    public String getColumValueName(int index, String column)
    {
        SingleTable table = new SingleTable();
        return table.getColumnValue(index, column);
    }

    public ProfilePage clickOnLinkInRow(String value)
    {
        SingleTable investorsTable = new SingleTable();
        investorsTable.clickOnLinkWithName(value);
        return new ProfilePage();
    }

    public void exportTable(String option)
    {
        Log.story("Export export from table");
        SingleTable singleTable = new SingleTable();
        singleTable.clickOnExportWithOption(option);
    }

    private String getExportFileName(String tabName)
    {
        int year = NumberHelper.extractInt(getProfileTitle());
        String name = getProfileTitle().replace(String.valueOf(year), "");
        return StringHelper.toSnakeCase(DateHelper.dtInsert("yyyyMMdd") + "_benchmark_" + tabName.split(" ")[1] + "_" + NumberHelper.extractInt(getProfileTitle()) + "_" + name);
    }

    public void verifyExportConstituentRankingTable(String tabName, int countOfEntity)
    {
        Log.story("Verify export export from Constituent Ranking table");
        String fileName = getExportFileName(tabName);
        Verify.isFileDownloaded(fileName, ExportFileFormat.excel.fileExtension);
        Verify.isEqual(countOfEntity, FileHelper.getCountEntityInFile(fileName, ExportFileFormat.excel), "Count of exported rows not equal");
    }

    public void verifyExportGraph(String tabName)
    {
        Log.story("Verify export export graph");
        Verify.isFileDownloaded(getExportFileName(tabName), ExportFileFormat.png.fileExtension);
    }

    public int selectBenchmarkViews(String strategy, String vintage)
    {
        Log.story("Change benchmark strategy and vintage");
        EditBenchmarkForm editBenchmarkForm = new EditBenchmarkForm();
        editBenchmarkForm.selectVintage(vintage);
        int count = editBenchmarkForm.selectStrategy(strategy);
        editBenchmarkForm.clickRunBenchmarkButton();
        return count;
    }

    public int changeBenchmarkViewOnFirstValues()
    {
        Log.story("Change benchmark strategy");
        Find.click(benchmarkToggle);
        EditBenchmarkForm editBenchmarkForm = new EditBenchmarkForm();
        editBenchmarkForm.clickResetAllFilters();
        Function.slowEnvironmentWait(Duration.ofSeconds(3));
        String[] vintage = editBenchmarkForm.getListOfOptions(EditBenchmarkForm.Dropdown.vintage).get(0).split(" \n");
        String[] strategy = editBenchmarkForm.getListOfOptions(EditBenchmarkForm.Dropdown.strategy).get(0).split(" \n");
        return selectBenchmarkViews(strategy[0], vintage[0]);
    }

    public void verifyContainedFunds(List<String> expectedFunds)
    {
        Log.story("Verify that expected fund are contain in benchmark");
        goToTab("Constituent Ranking");
        verifyCountOfConstituentFunds(String.valueOf(expectedFunds.size()));
        SingleTable singleTable = new SingleTable();
        List<String> actualFunds = singleTable.getColumnValues("Fund name");
        Verify.isTrue(actualFunds.containsAll(expectedFunds), "Not all funds are presented in benchmark");
    }

    public void setSaveAsReport(String reportName)
    {
        Log.story("Save benchmark as a report with name " + reportName);
        Find.click(saveAsReportButton);
        AddToListForm addToListForm = new AddToListForm();
        addToListForm.enterSavedFilterName(reportName);
        addToListForm.saveNewList();
    }
}
