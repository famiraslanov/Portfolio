package com.tests.pageobjects.platform;

import com.library.*;
import com.library.classes.FindByText;
import com.library.helpers.DateHelper;
import com.library.helpers.Function;
import com.library.helpers.NumberHelper;
import com.tests.enums.platform.*;
import com.tests.pageobjects.baseobjects.BasePageObject;
import com.tests.pageobjects.platform.forms.CompareFindsForm;
import com.tests.pageobjects.platform.forms.ExportListingForm;
import com.tests.pageobjects.platform.forms.SavedFilterNotificationForm;
import com.tests.pageobjects.platform.forms.TopNavigationMenu;
import org.apache.commons.collections4.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ExplorePage extends BasePageObject
{
    private final By exploreSearchField = By.id("searchInputValue");
    private final By savedListsTable = By.cssSelector("[class*='SavedListTable']");
    private final By savedListFilterDropdown = By.cssSelector("[data-testid='saved-list-select']");
    private final By savedListFilterOptions = By.cssSelector("[class*='SavedListsstyled'] [role='option']");
    private final By reportTable = By.cssSelector("[class*='ReportTable']");
    private final By tableRow = By.cssSelector("[data-testid*='table-row']");
    private final By linkRow = By.cssSelector("[class*='SavedListsstyled__FirstColumn']:not([class*='FirstColumnRoot'])");
    // relevant for Hedge Funds only
    private final By indexTickerFilterDropdown = By.cssSelector("[class*='IndexTickerstyled'] [aria-haspopup='listbox']");
    private final By indexTickerLabels = By.cssSelector("[data-testid='index-label']");
    private final By leftScrollIndexButton = By.cssSelector("[class*='Tickerstyled'] [data-icon='chevron-left']");
    private final By rightScrollIndexButton = By.cssSelector("[class*='Tickerstyled'] [data-icon='chevron-right']");
    private final By savedLayoutsLabel = By.cssSelector("a[class*='AccordionTitle']");
    private final By searchFiled = By.id("searchInputValue");
    private final By removeSavedListButton = By.cssSelector("[data-testid='remove-custom-index']");
    private final By exportSavedListButton = By.cssSelector("[data-testid*='export-test'] button span");
    private final By compareSavedListButton = By.cssSelector("[id='compare-modal-open']");
    private final By removeSubmitButton = By.id("popup-submit");
    private final By savedLayoutDropdown = By.cssSelector("[data-testid*='saved-filters-select']");
    private final By savedLayoutFilterOptions = By.cssSelector("[class*='SavedFiltersstyled'] [role='option']");
    private final By filterItem = By.cssSelector("[data-testid*='filterCard'] [data-testid='title-test']");
    private final By crossNotificationButton = By.cssSelector("[data-testid='notification-close-button'] [data-icon='cross']");
    private final By editSavedLayoutsButton = By.cssSelector("button[class*='SavedFiltersstyled'] [data-icon='edit']");
    private final By saveListNames = By.cssSelector("[data-rowname]");
    private final By saveListAsIndex = By.cssSelector("[data-testid='tooltip-addToIndex'] button");
    private final By nameColumn = By.cssSelector("[class*='_ReportTable-'] [role='cell']:nth-child(1) span");
    private final By spinner = By.cssSelector("[class*='Spinner']");

    private AssetClass selectedAssetClass;

    public ExplorePage(AssetClass assetClass)
    {
        super();
        selectedAssetClass = assetClass;
        correctPage(Find.options().locator(assetClass.explorePageLoadIdentifier).timeoutMS(30000));
        if (selectedAssetClass == AssetClass.allocate || selectedAssetClass == AssetClass.hedge) {
            Verify.isFound(Find.options().locator(indexTickerLabels).returnFirst(true).checkForNoSpinner(true).timeoutMS(Store.getSettings().getDefaultTimeoutMS() * 2), "Index tickers carousel has not loaded");
        }
    }

    public void selectSavedListTableFilter(EntityCard entityCard)
    {
        Log.story("Select filter for Saved lists table: " + entityCard);
        WebElement savedListDropdown = fetchSavedListDropdown();
        if (savedListDropdown == null) {
            setupSaveLists(entityCard);
            savedListDropdown = fetchSavedListDropdown();
        }

        Find.click(savedListDropdown);
        Function.slowEnvironmentWait(Duration.ofSeconds(2), "Allow for delete notification to show");
        Find.click(Find.options().locator(savedListFilterOptions).scrollTo(true).clickable(true).findByText(FindByText.by().contains(entityCard.value)));
    }

    private WebElement fetchSavedListDropdown()
    {
        return Find.element(Find.options().locator(savedListFilterDropdown).scrollTo(true).failOnNotFound(false).clickable(true));
    }

    private void setupSaveLists(EntityCard entityCard)
    {
        Log.story("No " + entityCard + " saved lists found so setting two up");

        EntityListingPage entityListingPage = navigateToEntityListingPage(entityCard);
        entityListingPage.createDefaultSaveList(DateHelper.dtInsert("yyMMddHHmmss") + "-Keep-" + entityCard, false);
        Store.getDriver().navigate().refresh();
        entityListingPage = navigateToEntityListingPage(entityCard);
        entityListingPage.createDefaultSaveList(DateHelper.dtInsert("yyMMddHHmmss") + "-Keep-" + entityCard, false);
        TopNavigationMenu.getInstance().navigateToExplore(selectedAssetClass);
    }

    public void selectSavedLayoutTableFilter(EntityCard entityCard)
    {
        Log.story("Select filter for Saved layout table: " + entityCard);
        Find.click(Find.options().locator(savedLayoutDropdown));
        Find.click(Find.options().locator(savedLayoutFilterOptions).findByText(FindByText.by().contains(entityCard.value)));
    }

    public void editSavedLayout(EntityCard entityCard, String savedFilterName, String newFilterName, AlertFrequency alertFrequency)
    {
        Log.story("Click edit saved layout and change data");
        SavedFilterNotificationForm savedFilterNotificationForm = openEditSavedLayout();
        savedFilterNotificationForm.clickOnEditButton(entityCard.value, savedFilterName);
        savedFilterNotificationForm.enterFilterName(newFilterName);
        savedFilterNotificationForm.selectAlertActivity(alertFrequency);
        savedFilterNotificationForm.clickSaveButton();
        savedFilterNotificationForm.clickCloseButton();
    }

    public SavedFilterNotificationForm openEditSavedLayout()
    {
        Log.story("Open SavedLayouts edit popup");
        Find.click(Find.options().locator(editSavedLayoutsButton).scrollTo(true));
        return new SavedFilterNotificationForm();
    }

    public void verifyThatSavedLayoutContainedInList(EntityCard entityCard, String savedFilterName, AlertFrequency alertFrequency)
    {
        Log.story("Verify that  " + savedFilterName + "is displayed in list");
        Find.click(Find.options().locator(editSavedLayoutsButton).scrollTo(true));
        SavedFilterNotificationForm savedFilterNotificationForm = new SavedFilterNotificationForm();
        savedFilterNotificationForm.clickOnEditButton(entityCard.value, savedFilterName);
        savedFilterNotificationForm.verifyThatAlertFrequencyIsChecked(alertFrequency);
        savedFilterNotificationForm.clickCloseButton();
    }

    public void applyFilter(String filterName)
    {
        Log.story("Select filter: " + filterName);
        Find.click(Find.options().locator(filterItem).scrollTo(true).findByText(FindByText.by().contains(filterName)));
        Verify.isNotFound(savedLayoutsLabel, "Saved layout label still seen");
    }

    public String getSavedListName(int rowIndex, boolean isIndex, EntityCard entityCard)
    {
        Log.story("Fetch the saved list name at index: " + rowIndex);
        List<String> savedList = fetchSavedList(rowIndex, isIndex);
        if (CollectionUtils.isEmpty(savedList) || savedList.size() < (rowIndex + 1)) {
            for (int i = (savedList != null ? savedList.size() : 0); i <= (rowIndex + 1); i++) {
                setupSavedLists(entityCard, isIndex);
            }
            savedList = fetchSavedList(rowIndex, isIndex);
        }

        Verify.isTrue(savedList.size() > rowIndex, "No savedList entries found with isIndex: " + isIndex + " Found: " + savedList.size());

        return savedList.get(rowIndex);
    }

    private void setupSavedLists(EntityCard entityCard, boolean isIndex)
    {
        Log.story("No saved lists for " + entityCard + " so setting up");
        EntityListingPage entityListingPage = navigateToEntityListingPage(entityCard);
        entityListingPage.createDefaultSaveList(DateHelper.dtInsert("yyMMddHHmmss") + "-" + (isIndex ? "indexed" : "notindexed") + "-" + entityCard, isIndex);
        Function.slowEnvironmentWait(Duration.ofSeconds(2), "Allow save to happen");
        TopNavigationMenu.getInstance().navigateToExplore(selectedAssetClass);
    }

    private List<String> fetchSavedList(int rowIndex, boolean isIndex)
    {
        List<String> savedList = Find.getAttributes(Find.options().locator(saveListNames).failOnNotFound(false), "data-rowname");
        if (isIndex) {
            savedList = filterNamesForIsIndex(savedList, rowIndex);
        }
        return savedList;
    }

    private List<String> filterNamesForIsIndex(List<String> list, int minNumberToReturn)
    {
        Function.slowEnvironmentWait(Duration.ofSeconds(2), "Wait for page to settle");
        List<String> filtered = new ArrayList<>();
        if (list != null) {
            for (String indexName : list) {
                WebElement isIndexElement = Find.element(By.cssSelector(rowSelectorString(indexName) + " [data-testid='tooltip-addToIndex'] button"));
                if (Find.getCSSValue(isIndexElement, "background").contains("rgb(108, 43, 255)")) {
                    filtered.add(indexName);
                }
                if (filtered.size() > minNumberToReturn) {
                    break;
                }
            }
        }

        return filtered;
    }

    public String getReportName(int rowIndex)
    {
        return Find.getTexts(nameColumn).get(rowIndex);
    }

    public List<WebElement> getSavedLayouts()
    {
        return Find.elements(savedLayoutsLabel);
    }

    public PlatformArticlePage clickOnReport(String name)
    {
        Log.story("Click on report table row: " + name);
        Find.click(Find.options().locator(nameColumn).findByText(FindByText.by().contains(name)).clickable(true).scrollTo(true).returnFirst(true));
        return new PlatformArticlePage(selectedAssetClass);
    }

    public void clickOnSavedList(String name)
    {
        Log.story("Click on saved list table row: " + name);
        Find.click(Find.options().locator(By.cssSelector(rowSelectorString(name) + " [class*='_FirstColumn-']")).scrollTo(true).clickable(true));
    }

    public void deleteSavedList(String name)
    {
        Log.story("Remove saved layout");
        Function.slowEnvironmentWait(Duration.ofSeconds(2));
        Find.click(Find.options()
                .locator(By.cssSelector(rowSelectorString(name) + " [data-testid='remove-custom-index']"))
                .scrollTo(true)
                .clickable(true)
                .staleRefRetry(true)
        );
        Find.click(Find.options().locator(removeSubmitButton).clickable(true).staleRefRetry(true));
    }

    public void clearAutotestsSavedList()
    {
        Log.story("pre-deleting sheets that have not been indexed");
        List<String> savedLists = Find.getAttributes(Find.options().locator(saveListNames).failOnNotFound(false), "data-rowname");
        if (savedLists != null) {
            for (String savedList : savedLists) {
                if (savedList.contains("CustomIndex") && !savedList.contains(DateHelper.dtInsert("yyMMdd"))) {
                    deleteSavedList(savedList);
                    Function.slowEnvironmentWait(Duration.ofSeconds(1));
                }
            }
        }
    }

    public void exportSavedList(String name, ExportTemplates template, ExportDateFormat exportDateFormat, ExportFileFormat exportFileFormat)
    {
        Log.story("Click Export saved layout");
        Function.slowEnvironmentWait(Duration.ofSeconds(1));
        Find.click(Find.options()
                .locator(exportSavedListButton)
                .parentLocator(By.cssSelector(rowSelectorString(name)))
                .scrollTo(true)
                .clickable(true)
        );
        ExportListingForm exportListingForm = new ExportListingForm();
        exportListingForm.selectTemplateByName(template);
        exportListingForm.selectDateFormat(exportDateFormat);
        exportListingForm.selectFileFormat(exportFileFormat);
        exportListingForm.clickExportButton();
    }

    public CompareFindsForm compareSavedList(String name)
    {
        Log.story("Click on compare saved fund list");
        FindOptions compareIcon = Find.options()
                .locator(compareSavedListButton)
                .parentLocator(By.cssSelector(rowSelectorString(name)))
                .scrollTo(true);
        Find.click(compareIcon);
        return new CompareFindsForm();
    }

    public void clickToAsIndexIcon(String name)
    {
        Log.story("Click on As Index button for save list fund");
        FindOptions asIndex = Find.options()
                .locator(saveListAsIndex)
                .parentLocator(By.cssSelector(rowSelectorString(name)))
                .scrollTo(true);
        Find.click(asIndex);
        Verify.hasCss(asIndex, "background", "rgb(108, 43, 255)", "The icon did not become as 'As Index'!", false);
    }

    public void selectIndexFilter(IndicesFilter indicesFilter)
    {
        Log.story("Select indices filter: " + indicesFilter);
        Find.click(indexTickerFilterDropdown);
        Find.click(indicesFilter.findOptions);
    }

    public String fetchTickerIndiciesName(int index)
    {
        Log.story("Fetch indicies name at index:" + index);
        return Find.getTexts(indexTickerLabels).get(index);
    }

    public IndexPage clickOnIndexTicker(String index)
    {
        Log.story("Navigate to index:" + index);
        Verify.greaterThan(0, index.length(), "Index name is empty");
        Find.click(Find.options().locator(indexTickerLabels).scrollTo(true).findByText(FindByText.by().contains(index)));
        return new IndexPage(index);
    }

    public void clickOnLeftScrollIndex()
    {
        Log.story("Click on left scroll index carrousel button");
        Find.click(leftScrollIndexButton);
    }

    public void clickOnRightScrollIndex()
    {
        Log.story("Click on right scroll index carrousel button");
        Find.click(rightScrollIndexButton);
    }

    public EntityListingPage navigateToEntityListingPage(EntityCard entityCard)
    {
        Log.story("Navigate to entity listing page:" + entityCard);
        Find.click(entityCard.button);
        return new EntityListingPage(entityCard);
    }

    public int getEntityCountFromTab(EntityCard tabName)
    {
        Log.story("Get entity count from tab:" + tabName);
        int found = 0;
        int attempts = 0;
        Exception lastException = null;
        while (found == 0 && attempts < 10) {
            try {
                found = NumberHelper.extractInt(Find.getText(tabName.button).replace(" ", "").replace(",", ""));
            } catch (Exception e) {
                lastException = e;
            }
            if (found == 0) {
                Function.slowEnvironmentWait(Duration.ofSeconds(1), "Wait between polls");
            }
            attempts++;
            Log.debug("Looking for entity count. Attempts: " + attempts + " Found: " + found);
        }

        if (lastException != null) {
            Log.exception(lastException);
        }

        Verify.greaterThan(0, found, "The number found was zero");

        return found;
    }

    public void enterIntoSearchField(String query)
    {
        Log.story("Type search query into search field:" + query);
        Find.click(searchFiled);
        Find.insert(searchFiled, query);
        Verify.isNotFound(spinner, "Spinner still shown");
    }

    public void verifyReportsAreSortedByDateDesc()
    {
        Log.story("Verify that report table is listing dates newest first");

        List<String> columnHeadings = Find.getTexts(By.cssSelector("[class*='_ReportTable-'] [role='columnheader']"));
        List<String> dates = Find.getTexts(Find.options().locator(By.cssSelector(String.format("[class*='_ReportTable-'] [role='cell'][role='cell']:nth-child(%d)", columnHeadings.indexOf("Date") + 1))).parentLocator(reportTable));

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        List<Date> parsedDatesFromTheUI = new ArrayList<>();
        for (String date : dates) {
            try {
                parsedDatesFromTheUI.add(formatter.parse(date));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        List<Date> originalUIDateList = new ArrayList<>(parsedDatesFromTheUI);
        List<Date> sortedByDate = new ArrayList<>(parsedDatesFromTheUI);

        sortedByDate.sort(Collections.reverseOrder());
        Verify.isTrue(sortedByDate.equals(originalUIDateList), "Report table is not showing the newest first");
    }

    public void verifyIndexCarouselContainsIndex(String indexName)
    {
        Log.story("Verify that indexes list contain custom index: " + indexName);
        Verify.isFound(Find.options().locator(indexTickerLabels).findByText(FindByText.by().contains(indexName)), "Custom index not found in ticker: " + indexName);
    }

    public void verifyIndexCarouseLoaded()
    {
        Log.story("Verify that indexes carouse is loaded");
        Verify.isFound(indexTickerFilterDropdown, "Index filter dropdown is not found");
        Verify.isFound(rightScrollIndexButton, "Index scroll button is not found");
    }

    public String getIndexName(WebElement index)
    {
        return Find.getText(index);
    }

    private String rowSelectorString(String name)
    {
        return String.format("[data-rowname=\"%s\"]", name);
    }
}
