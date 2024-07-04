package com.tests.pageobjects.platform;

import com.library.*;
import com.library.classes.FindByText;
import com.library.helpers.DateHelper;
import com.library.helpers.Function;
import com.library.helpers.NumberHelper;
import com.library.helpers.StringHelper;
import com.tests.enums.platform.*;
import com.tests.pageobjects.baseobjects.BasePageObject;
import com.tests.pageobjects.platform.forms.*;
import com.tests.pageobjects.platform.forms.components.ProfileTableComponent;
import com.tests.pageobjects.platform.forms.components.SingleTable;
import org.apache.commons.collections4.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class EntityListingPage extends BasePageObject
{
    private final By pageLoadIdentifier = By.cssSelector("[class*='TabNavLinksWrapper']");
    private final By searchField = By.cssSelector("[id*='table-search']");
    private final By exportButton = By.cssSelector("[data-testid='exportModal-triggerElement-exportBtn']");
    private final By layoutDropdown = By.cssSelector("[data-testid*='toggle-item']:not([data-testid*='Filters']):not([data-testid*='Data settings'])");
    private final By layoutDropdownOpen = By.cssSelector("[data-testid^='toggle-item-Layout:']+div[class^='FilterFolderstyled_']");
    private final By savedLayoutNonCheckedItems = By.cssSelector("[class*='FilterFolderstyled__ContentWrapper'] div > span:not([data-icon='checkmark']), a[href*='performances']");
    private final By defaultLayoutItem = By.cssSelector("[class*='FilterFolderstyled__ContentWrapper'] a");
    private final By selectedLayouts = By.cssSelector("[data-testid*='toggle-item-Layout'] [class*='FilterFolderHeader']");
    private final By filterDropdown = By.cssSelector("[data-testid='toggle-item-Filters']");
    private final By filterDropdownPerf = By.cssSelector("[class*='FilterCardstyle'] button");
    private final By resetLayoutButtonPerf = By.cssSelector("[class*='Folderstyled__DropdownModal'] [class*='ActionGroup'] button:first-child");
    private final By editFiltersButtonPerf = By.cssSelector("button:not([data-testid=\"preSavedLayoursModal-triggerElement-Btn\"]) > span[data-icon=\"layout-full\"]");
    private final By filterBarOpen = By.cssSelector("[data-testid='toggle-item-Filters']+div, [class*='FolderFilterGroupstyled__Wrapper']");
    private final By filterToggles = By.cssSelector("button[data-testid='filter-group-toggle']");
    private final By filterListElements = By.cssSelector("[data-testid*='selectMenu-listItem']:not([data-testid='selectMenu-listItem-selectAll'])");
    private final By filterDoubleList = By.cssSelector("[data-testid='dropdown-content'] ul[aria-multiselectable='true']:has([data-icon*='chevron'])");
    private final By firstLevelDoubleListElements = By.cssSelector("ul[aria-multiselectable='true'] div > li div[class*='Checkboxstyled__LabelText']:nth-child(3), ul[aria-multiselectable='true'] div:nth-last-child(2):has( > label), ul[aria-multiselectable='true'] div:nth-last-child(1):has( > label)");
    private final By expendFilterListButton = By.cssSelector("[data-testid='dropdown-content'] [data-icon='chevron-down']");
    private final By saveLayoutButton = By.cssSelector("[data-testid='pre-save-filters-trigger-popup-btn'], [data-testid^='preSavedLayoursModal-']");
    private final By resetLayoutButton = By.cssSelector("[id*='filter-clear-all'], [class*='ActiveFiltersstyled'] [data-icon='close']");
    private final By addFilterButton = By.cssSelector("[class*='FilterFolderstyled'] span[data-icon='plus']");
    private final By addFilterButtonPerf = By.cssSelector("[class*='FilterFolderstyled'] span[data-icon='layout-full']");
    private final By dataSettingDropdown = By.cssSelector("[data-testid='toggle-item-Data settings']");
    private final By entityTab = By.cssSelector("[class*='TabNavLinksWrapper'] [data-testid*='navLinkName']");
    private final By entityTabNumber = By.cssSelector("[class*='Lozengestyled__LozengeText']");
    private final By entityTabNumberPerf = By.cssSelector("[id='table-wrapper'] [class*='Pagestyled__FilterWrapper']");
    private final By dataPointsCheckboxes = By.cssSelector("[class*='FilterCategoryCheckboxWrapper']");
    private final By clearAllFiltersButton = By.cssSelector("[class*='CtaClearButton'] > button");
    private final By doneFiltersButton = By.cssSelector("[class*='FilterDrawerstyled__ButtonWrapper'] > button");
    private final By settingTogglesInput = By.cssSelector("label[class*='Togglestyled__Label'] input");
    private final By settingToggles = By.cssSelector("label[class*='Togglestyled__Label']");
    private final By upgradePlanPopup = By.cssSelector("[class*='UpgradePopupstyled__Container']");
    private final By doneSingleFilterButton = By.cssSelector("[class*='Contentstyled'] button");
    private final By searchByValueField = By.cssSelector("[class*='Contentstyled'] [data-testid*='selectMenu-input']");
    private final By nameColumnLabel = By.cssSelector("[data-testid*='table-row'] [role='cell']:nth-of-type(1)");
    private final By columnHeaders = By.cssSelector("[role='columnheader'] div[data-testid*='tooltip'] > div");
    private final By crossFilterIconButton = By.cssSelector("[class*='Contentstyled__Wrapper'] [data-icon='cross']");
    private final By spinner = By.cssSelector("[class*='Spinnerstyled'], [class*='LogoSpinner_']");
    private final By countOfSelectedFilterLabel = By.cssSelector("[data-testid='count-badge']");
    private final By tableRow = By.cssSelector("[data-testid*='table-row-']");
    private final By notResultFoundLabel = By.cssSelector("[data-testid='table-noResults']");
    private final String rowLocatorString = "a[data-rowname=\"%s\"]";
    private final By mandateInvestorNames = By.cssSelector("[role='cell']:nth-of-type(1) [data-testid^='tooltip-'] div");
    private final By benchmarkNames = By.cssSelector("[role='cell']:nth-of-type(1) > span");
    private FindOptions clearFilterSelectedOptions = Find.options().locator(By.cssSelector("button")).scrollTo(true).checkForNoSpinner(true).findByText(FindByText.by().contains("Clear")).parentLocator(By.cssSelector("[role='listbox'] + div"));
    private final By filterOptionsContainer = By.cssSelector("[class^='FilterFolderstyled__ContentWrapper-']:has([class*='FilterContainer'])");
    private final By selectedOptions = By.cssSelector("[role='listbox'] li:has([aria-checked='true']), label[class*='Checkboxstyled']:has([aria-checked='true'], [aria-checked='mixed']) + span");
    private final By statisticsDropdowns = By.cssSelector("[class*='FundsPerformancesstyled'] button");
    private final By cell = By.cssSelector("[role='cell']");
    private final By successSavedMessageAlert = By.cssSelector("[class*='UserMessagestyled__MessageText']");
    private final By allCheckBoxes = By.cssSelector("a[data-rowname] input + span svg");
    private final EntityCard selectedEntity;
    private final FindOptions exportingButton = Find.options().locator(exportButton).findByText(FindByText.by().contains("Exporting...")).timeoutMS(Store.getSettings().getDefaultTimeoutMS() * 3);
    private final By filterRangeFrom = By.cssSelector("input[placeholder='From']");
    private final By filterRangeTo = By.cssSelector("input[placeholder='To']");
    private final By showPerformanceAsToDataFilter = By.cssSelector("[role='presentation'] [aria-label='select-box']");
    private final By showPerformanceAsToDataOptions = By.cssSelector("[aria-label='select-box'] [role='option'] div[data-testid*='tooltip-']");
    private final By filterDragAndDropButton = By.cssSelector("[aria-describedby*='DndDescribedBy']");
    private final By filterDragAndDropTitlesDisabled = By.cssSelector("[class*='FilterDragAndDropColumnTitle'][disabled]");
    private final By filterSeeResultsButton = By.cssSelector("[class*='Folderstyled__DropdownModal'] [class*='ActionGroup'] button:last-child");
    private final By fundPerformancePrivateMarketLabel = By.cssSelector("[data-testid='navLink-fundPerformance']");
    private final FindOptions seeFilersResultButtonPerf = Find.options().locator(By.cssSelector("[class*='FolderFilterStatisticsstyled'] button")).findByText(FindByText.by().contains("See "));
    private final By quartileDropdown = By.cssSelector("[class*='FormSelectstyled__DropdownSelect']");
    private final By doneButton = By.cssSelector("[data-testid=\"dropdown-content\"] div:last-child > button:not([aria-haspopup])");
    private final By iwowNavigationIcon = By.cssSelector("div[class*=\"NavLinkstyled__LinkIconWrapper\"]");

    public EntityListingPage(EntityCard entityCard)
    {
        super();
        selectedEntity = entityCard;

        correctPage(pageLoadIdentifier);
        verifyCorrectTabSelected();
    }

    public void verifyCorrectTabSelected()
    {
        Log.story("Verify that " + selectedEntity + " listing page shown");
        Verify.hasNotCss(selectedEntity.tab, "color", "rgba(38, 38, 44, 1)", "The entity tab is not showing as selected");
    }

    public void verifyThatTabIsHidden()
    {
        Log.story("Verify that " + selectedEntity.name() + " tab is hidden");
        Verify.isFound(Find.options().locator(upgradePlanPopup), "'Upgrade to see this' popup is not found");
    }

    public void verifyThatFilterSelected(String filterName)
    {
        Log.story("Verify that " + filterName + " filter is selected");
        Verify.isTrue(Find.getAttribute(Find.options().locator(layoutDropdown), "data-testid").contains(filterName), "Saved layout is not selected");
    }

    public void clickOnEntityTab(EntityCard entityCard)
    {
        Log.story("Click on entity tab:" + entityCard);
        Find.click(entityCard.tab);
    }

    public String getCellValues(int rowIndex, String cellName)
    {
        Log.story("Get " + cellName + " value for row: " + rowIndex);
        int index = Find.getTexts(Find.options().locator(columnHeaders).clickable(true)).indexOf(cellName);
        return Find.getText(Find.options().locator(By.cssSelector("[role='cell']:nth-of-type(" + (index + 1) + ")")).clickable(true)
                .parent(Find.elements(tableRow).get(rowIndex))).replace("0.00%", "-").trim();
    }

    public void verifyThatCellContainsValue(int rowIndex, String cellName, String expectedValue)
    {
        Log.story("Verify that " + cellName + " cell contains value : " + expectedValue + " in row " + rowIndex);
        Verify.contains(this.getCellValues(rowIndex, cellName), expectedValue);
    }

    public BenchmarkProfilePage clickOnBenchmark(int rowIndex)
    {
        Log.story("Click on benchmark row: " + rowIndex);
        Find.click(Find.elements(benchmarkNames).get(rowIndex));
        Function.switchWindow(null);
        return new BenchmarkProfilePage(selectedEntity);
    }

    public String getInvestorName(int rowIndex)
    {
        Log.story("Find investor name at rowIndex: " + rowIndex);
        if (selectedEntity == EntityCard.mandates) {
            return Find.getTexts(mandateInvestorNames).get(rowIndex);
        }
        return Find.getAttribute(Find.elements(tableRow).get(rowIndex), "data-rowname");
    }

    public ProfilePage getInvestorWithBlock(List<String> blocks)
    {
        Log.story("Select entity with selected blocks: " + blocks);
        final int maxRows = 20;
        final int minRowIndex = 3;

        String baseListingWindow = Function.getOriginalWindow();

        ProfilePage profilePage = null;
        for (int i = minRowIndex; i < maxRows; i++) {
            profilePage = this.clickOnRowByName(this.getInvestorName(i));
            for (String block : blocks) {
                if (!profilePage.isDisplaySection(block)) {
                    Log.info("Entity does not contain " + block);
                    profilePage = null;
                    Function.closeCurrentTab();
                    Function.switchToSelectedWindow(baseListingWindow);
                    break;
                }
            }
            if (profilePage != null) {
                return profilePage;
            }
        }

        Verify.isTrue(false, "No matching profile page found");
        return profilePage;
    }

    public ProfilePage getInvestorWithFullActiveListButton()
    {
        Log.story("Select entity with Full History button");
        final int maxRowsToCheck = 30;
        final int startingRowIndex = 0;

        String baseListingWindow = Function.getOriginalWindow();

        ProfilePage profilePage;
        for (int i = startingRowIndex; i < maxRowsToCheck; i++) {
            profilePage = this.clickOnRowByName(this.getInvestorName(i));
            if (!profilePage.isDisplaySeeFullHistoryButton()) {
                Log.info("Entity does not contain Full history button. Attempt: " + i);
                profilePage = null;
                Function.closeCurrentTab();
                Function.switchToSelectedWindow(baseListingWindow);
            }

            if (profilePage != null) {
                return profilePage;
            }
        }

        Verify.isTrue(false, "Profile page with fullHistory button could not be found");
        return null;
    }

    public void enterInSearchField(String query, int preSearchCount)
    {
        Log.story("Type search query in search field");
        Find.insert(Find.options().locator(searchField), query);

        int attempts = 0;
        while (getEntityCountListing() == preSearchCount && attempts < 30) { // 30 * 100ms = 3 seconds
            Function.slowEnvironmentWait(Duration.ofMillis(100), "Small wait to allow search to apply");
            attempts++;
        }
    }

    public void verifySearchResults(String query)
    {
        Log.story("Verify that search results are relevant search query: " + query);
        List<String> names = Find.getTexts(Find.options().locator(nameColumnLabel).checkForNoSpinner(true));
        for (int i = 0; i < names.size(); i++) {
            try {
                Verify.isTrue(names.get(i).contains(query), "Table contains not only " + query + " names after search");
            } catch (AssertionError e) {
                Log.debug("there is no name match. Checking by nested manager name");
                verifyThatCellContainsValue(i, "Manager name", query);
            }
        }
    }

    public void verifyThatNoResultsFound()
    {
        Log.story("Verify that 'No results found' is displayed");
        Verify.isFound(notResultFoundLabel, "'No results found' is not displayed");
    }

    public void uncheckAllFilters()
    {
        if (Log.featureFlag("CEFP_PERFORMANCE", Find.options().locator(editFiltersButtonPerf))) {
            Find.elements(dataPointsCheckboxes)
                    .stream()
                    .filter(option -> Find.getAttribute(Find.options().locator(By.cssSelector("input")).parent(option), "aria-checked").equals("true"))
                    .forEach(Find::click);
        }
    }

    public void selectFilters(List<String> filterNames)
    {
        Log.story("Select filters by name");

        if (Log.featureFlag("CEFP_PERFORMANCE", Find.options().locator(editFiltersButtonPerf))) {
            Find.click(editFiltersButtonPerf);
            uncheckAllFilters();
        } else {
            selectFilterDropdown(true);
            Find.click(addFilterButton);
            Find.click(clearAllFiltersButton);
        }
        if (!filterNames.isEmpty()) {
            filterNames.stream()
                    .map(filterName -> Find.options().locator(dataPointsCheckboxes).scrollTo(true).findByText(FindByText.by().equals(filterName)))
                    .filter(checkBoxElement -> {
                        String ariaChecked = Find.getAttribute(Find.options().locator(By.cssSelector("input")).parentOption(checkBoxElement), "aria-checked");
                        return !("true".equals(ariaChecked));
                    })
                    .forEach(Find::click);
        }
        Find.click(Find.options().locator(doneFiltersButton).findByText(FindByText.by().contains("Done")));
    }

    public void selectSavedLayout(String name)
    {
        Log.story("Select saved layout by name: " + name);
        selectLayoutDropdown(true);
        Find.click(Find.options().locator(savedLayoutNonCheckedItems).findByText(FindByText.by().contains(name)));
        Verify.isNotFound(Find.options().locator(spinner).timeoutMS(20000).maxWaitMS(30000), "Spinner still seen");
    }

    public List<String> getSavedLayout()
    {
        Log.story("Get saved layout by name");

        List<String> layouts = fetchLayoutOptions();

        if (CollectionUtils.isEmpty(layouts) || layouts.size() < 2) {
            Log.story("No saved layouts on this account so need to set them up");
            setupLayouts(selectedEntity.dataPoint1Text, DateHelper.dtInsert("yyMMddHHmmss") + "-Keep-" + selectedEntity);
            setupLayouts(selectedEntity.dataPoint2Text, DateHelper.dtInsert("yyMMddHHmmss") + "-Keep-" + selectedEntity);
            layouts = fetchLayoutOptions();
        }

        return layouts;
    }

    private List<String> fetchLayoutOptions()
    {
        selectLayoutDropdown(true);
        List<String> layouts = Find.getTexts(Find.options().locator(savedLayoutNonCheckedItems).failOnNotFound(false));
        selectLayoutDropdown(false);
        return layouts;
    }

    public String setupLayouts(String layoutType, String layoutName)
    {
        selectFilters(List.of(layoutType));
        String optionText = applyNewFilter(layoutType);
        Function.slowEnvironmentWait(Duration.ofSeconds(3), "Allow time for page URL to settle");
        saveFiltersLayout(layoutName, AlertFrequency.immediately);
        Function.slowEnvironmentWait(Duration.ofSeconds(1), "Allow time for the filter to save");
        return optionText;
    }

    public String getSavedLayoutListOfParams()
    {
        Log.story("Get selected layout set of filters settings");
        String url = Function.getCurrentUrl();
        return url.split("/?filter=")[1];
    }

    public void applyRangeFilter(String filterName, int from, int to)
    {
        Log.story("Apply range filter");
        openFilterDropdown(filterName);
        clearFilter();
        Find.insert(filterRangeFrom, String.valueOf(from));
        Verify.isNotFound(spinner, "Page is not loaded");
        Find.insert(filterRangeTo, String.valueOf(to));
        Verify.isNotFound(spinner, "Page is not loaded");
        Find.click(doneButton);
        if (Log.featureFlag("CEFP_PERFORMANCE", Find.options().locator(editFiltersButtonPerf))) {
            Find.click(seeFilersResultButtonPerf);
        }
    }

    public void applyRangeFilter(String filterName, String option)
    {
        Log.story("Apply range filter");
        openFilterDropdown(filterName);
        clearFilter();
        Find.click(quartileDropdown);
        Find.click(Find.options().locator(By.cssSelector("li[role='option']")).findByText(FindByText.by().equals(option)).parentLocator(quartileDropdown));
        Verify.isNotFound(spinner, "Page is not loaded");
        Find.click(doneButton);
        if (Log.featureFlag("CEFP_PERFORMANCE", Find.options().locator(editFiltersButtonPerf))) {
            Find.click(seeFilersResultButtonPerf);
        }
    }

    public String applyNewFilter(String filterName)
    {
        int index = 0;
        WebElement saveButton = null;
        String optionText = null;
        while (saveButton == null && index < 10) {
            optionText = applyFilter(filterName, index);
            saveButton = Find.element(Find.options().locator(saveLayoutButton).enabled(true).findByText(FindByText.by().contains("Save layout")).failOnNotFound(false).timeoutMS(3000));
            index++;
            Log.debug("Apply new filter attempt: " + index + " Ok: " + (saveButton != null));
        }
        if (Log.featureFlag("CEFP_PERFORMANCE", Find.options().locator(editFiltersButtonPerf))) {
            Find.click(seeFilersResultButtonPerf);
        }
        return optionText;
    }

    private void openFilterDropdown(String filterName)
    {
        if (Log.featureFlag("CEFP_PERFORMANCE", Find.options().locator(editFiltersButtonPerf))) {
            selectDropdown(true, filterDropdownPerf, filterBarOpen);
        } else {
            selectFilterDropdown(true);
        }
        FindOptions filterToggleButton = Find.options().locator(filterToggles).findByText(FindByText.by().contains(filterName));
        selectDropdown(true, filterToggleButton, doneButton);
    }

    private void clearFilter()
    {
        WebElement clearPreviousSelected = Find.element(clearFilterSelectedOptions.failOnNotFound(false).checkForNoSpinner(true));
        if (clearPreviousSelected != null) {
            Log.story("Clear previously selected options");
            Find.click(clearPreviousSelected);
        }

        WebElement clearPreviousSelectedRange = Find.element(Find.options().locator(By.cssSelector("button"))
                .scrollTo(true).visible(true).findByText(FindByText.by().contains("Clear")).failOnNotFound(false).timeoutMS(3000));
        if (clearPreviousSelectedRange != null) {
            Log.story("Clear previously selected options");
            Find.click(clearPreviousSelectedRange);
        }
    }

    private void insertAndWaitForFilters(String optionText)
    {
        String first10Char = optionText.substring(0, Math.min(optionText.length(), 10)).split("[^a-zA-Z0-9]")[0];

        Find.insert(Find.options().locator(searchByValueField), first10Char);
        Function.slowEnvironmentWait(Duration.ofSeconds(2), "Allow time for filters to settle");

        WebElement filterDoubleListElement = Find.element(Find.options().locator(filterDoubleList).timeoutMS(2000).failOnNotFound(false));
        if (filterDoubleListElement != null) {
            WebElement expendButton = Find.element(Find.options().locator(expendFilterListButton).failOnNotFound(false));
            if (expendButton != null) {
                Find.click(expendButton);
            }
            Find.click(Find.options().locator(By.cssSelector("label")).parentOption(
                    Find.options().locator(firstLevelDoubleListElements).findByText(FindByText.by().equals(optionText)).returnLast(true).scrollOnElement(true)));
        } else {
            Find.click(Find.options().locator(filterListElements).findByText(FindByText.by().equals(optionText)).returnLast(true).scrollOnElement(true));
        }
        Function.slowEnvironmentWait(Duration.ofSeconds(1), "Allow time for filters to settle");
        Find.click(Find.options().locator(doneSingleFilterButton).findByText(FindByText.by().contains("Done")).scrollTo(true));
    }

    private String getOptionTextByIndex(int indexInList)
    {
        WebElement doubleList = Find.element(Find.options().locator(filterDoubleList).timeoutMS(3000).failOnNotFound(false));
        if (doubleList != null) {
            return Find.getTexts(firstLevelDoubleListElements).get(indexInList);
        } else {
            return Find.getTexts(Find.options().locator(filterListElements)).get(indexInList);
        }
    }

    public void applyFilter(String filterName, String optionText)
    {
        openFilterDropdown(filterName);
        clearFilter();
        insertAndWaitForFilters(optionText);
        if (Log.featureFlag("CEFP_PERFORMANCE", seeFilersResultButtonPerf)) {
            Find.click(seeFilersResultButtonPerf);
        }
    }

    public String applyFilter(String filterName, int indexInList)
    {
        openFilterDropdown(filterName);
        clearFilter();
        String optionText = getOptionTextByIndex(indexInList);
        insertAndWaitForFilters(optionText);
        return optionText;
    }

    public void verifyThatFilterValueIsSelected(String filterName, List<String> values)
    {
        Log.story("Verify that filter options are selected");
        List<String> selectedValues = getListOfSelectedFilterOptions(filterName);

        Verify.isTrue(selectedValues.containsAll(values), "Filter values are not selected");
        selectFilterDropdown(false);
    }

    public List<String> getListOfSelectedFilterOptions(String filterName)
    {
        Log.story("Get list of selected filter options");
        selectLayoutDropdown(false);
        selectFilterDropdown(true);

        Find.click(Find.options().locator(filterToggles).findByText(FindByText.by().contains(filterName)));
        Function.slowEnvironmentWait(Duration.ofSeconds(1));

        WebElement filterOption = Find.element(Find.options().locator(quartileDropdown).failOnNotFound(false).maxWaitMS(300));
        return (filterOption == null) ? Find.getTexts(Find.options().locator(selectedOptions)) : List.of(Find.getText(quartileDropdown));
    }

    public List<String> getListOfAllSelectedInternalFilterOptions(String filterName, String findOptions)
    {
        Log.story("Get list of selected filter options");
        selectLayoutDropdown(false);
        selectFilterDropdown(true);

        Find.click(Find.options().locator(filterToggles).findByText(FindByText.by().contains(filterName)));
        Function.slowEnvironmentWait(Duration.ofSeconds(1));

        Find.insert(Find.options().locator(searchByValueField), findOptions);
        Function.slowEnvironmentWait(Duration.ofSeconds(2));

        return Find.getTexts(Find.options().locator(selectedOptions));
    }

    public void saveFiltersLayout(String name, AlertFrequency frequency)
    {
        Log.story("Save user preferences in saved layout");
        Find.click(Find.options().locator(saveLayoutButton).scrollTo(true));
        new SaveFilterForm().saveFilter(name, frequency);
        Verify.isFound(successSavedMessageAlert, "Saved message does not snow");
        verifyThatFilterSelected(name);
    }

    public void copyFiltersLayout(String name, AlertFrequency frequency, UserLogin userLogin)
    {
        Log.story("Copy user preferences in saved layout");
        Find.click(Find.options().locator(saveLayoutButton).scrollTo(true));
        new SaveFilterForm().copyFilter(name, frequency, userLogin);
    }

    public void verifyThatAllFiltersAreDisplayedAsDefault(List<String> expectedFilters)
    {
        Log.story("Verify that all expected filters are displayed as default");
        selectFilterDropdown(true);
        for (String expectedFilter : expectedFilters) {
            Verify.isFound(Find.options().locator(filterToggles).findByText(FindByText.by().startsWith(expectedFilter)).timeoutMS(2000), "Filter option not found. Looking for: [" + expectedFilter + "]", true, "ENG-1679");
        }
    }

    public ExportListingForm openExportPopup()
    {
        Log.story("Open Export popup");
        Find.click(Find.options().locator(exportButton).clickable(true));
        return new ExportListingForm();
    }

    public void openAddToListPopup()
    {
        Log.story("Click on Add to list button");
        clickOnToolbeltIcon(ToolbeltIcon.addToList);
    }

    public CompareFindsForm clickCompareFinds()
    {
        Log.story("Open compare finds form");
        clickOnToolbeltIcon(ToolbeltIcon.compareButton);
        return new CompareFindsForm();
    }

    public void setDataSetting(String settingName)
    {
        Log.story("Set data settings for listing. Setting: " + settingName);
        if (Log.featureFlag("CEFP_PERFORMANCE", Find.options().locator(editFiltersButtonPerf))) {
            Find.click(editFiltersButtonPerf);
            Find.click(Find.options().locator(settingToggles).findByText(FindByText.by().contains(settingName)));
            Find.click(editFiltersButtonPerf);
            return;
        }

        Find.click(dataSettingDropdown);
        Find.click(Find.options().locator(settingToggles).findByText(FindByText.by().contains(settingName)));
        Find.click(dataSettingDropdown);
    }

    public void selectRowCheckboxByName(String name)
    {
        name = StringHelper.removeLabelsFromProfileName(name);

        Log.story("Select row checkbox by name of entity: " + name);

        String baseLocator = rowLocatorString + " input";
        String checkBoxAdditionalLocator = " + span svg";

        Find.click(By.cssSelector(String.format(baseLocator + checkBoxAdditionalLocator, name)));
        Verify.hasAttribute(By.cssSelector(String.format(baseLocator, name)), "aria-checked", "true");
    }

    public ProfilePage clickOnRowByName(String name)
    {
        Log.story("Click on row by name: " + name);
        FindOptions findOptions = selectedEntity == EntityCard.mandates ?
                Find.options().locator(mandateInvestorNames).findByText(FindByText.by().contains(name)).returnFirst(true)
                : Find.options().locator(By.cssSelector(String.format(rowLocatorString, StringHelper.removeLabelsFromProfileName(name)))).scrollTo(true).returnFirst(true);

        Find.click(findOptions);
        Function.switchWindow(null);
        return new ProfilePage();
    }

    public AddToListForm clickOnAddToList()
    {
        Log.story("Click on 'Add to List' button");
        Find.click(ToolbeltIcon.addToList.findOptions);
        return new AddToListForm();
    }

    public void verifyThatNotAbilityToCreateCustomList()
    {
        AddToListForm addToListForm = clickOnAddToList();
        addToListForm.clickOnSaveNewListMode();
        addToListForm.verifyNoToggleForCreateSavedList();
        addToListForm.closeSaveListForm();
    }

    public void createNewSavedList(String name, boolean isIndex)
    {
        Log.story("Create new saved list: " + name);
        AddToListForm addToListForm = clickOnAddToList();
        addToListForm.clickOnSaveNewListMode();
        addToListForm.enterSavedFilterName(name);
        if (isIndex) {
            addToListForm.clickOnCustomIndexModeToggle();
        }
        addToListForm.saveNewList();
        Verify.isFound(successSavedMessageAlert, "The message about the successful creation Saved list did not appear");
    }

    public String[] createDefaultSaveList(String name, boolean isIndex)
    {
        Log.story("Create a default saved list");
        String selectFund1 = getInvestorName(0);
        String selectFund2 = getInvestorName(1);
        selectRowCheckboxByName(selectFund1);
        selectRowCheckboxByName(selectFund2);
        createNewSavedList(name, isIndex);
        Verify.isNotFound(successSavedMessageAlert, "Saved message still snow");
        new EntityListingPage(selectedEntity);
        return new String[]{selectFund1, selectFund2};
    }

    public void updateSaveList(List<String> savedLists)
    {
        Log.story("Update saved lists");
        AddToListForm addToListForm = clickOnAddToList();
        addToListForm.clickOnUpdateListMode();
        addToListForm.selectCreatedListForUpdating(savedLists);
        addToListForm.saveNewList();
    }

    public int getEntityCountListing()
    {
        Log.story("Get count of selected tab: " + selectedEntity.name());

        int found = 0;
        int attempts = 0;
        Exception lastException = null;
        while (found == 0 && attempts < 10) {
            FindOptions countFindOptions;
            if (Log.featureFlag("CEFP_PERFORMANCE", Find.options().locator(iwowNavigationIcon).returnFirst(true).maxWaitMS(100))) {
                countFindOptions = Find.options().locator(entityTabNumberPerf);
            } else {
                countFindOptions = Find.options().locator(entityTabNumber).parentOption(selectedEntity.tab);
            }
            try {
                found = NumberHelper.extractInt(Find.getText(countFindOptions));
            } catch (Exception e) {
                lastException = e;
            }
            attempts++;
            Log.debug("Looking for count. Attempt: " + attempts + " Found: " + found);
        }

        if (lastException != null && found == 0) {
            Log.exception(lastException);
        }
        return found;
    }

    public void resetLayout()
    {
        Log.info("Click on reset layout button");
        for (WebElement resetButton : Find.elements(resetLayoutButton)) {
            Find.scrollToElement(resetButton);
            Find.click(resetButton);
        }
    }

    public void verifyThatFilterReset()
    {
        Log.info("Verify that filter is reset and url doesn't contain filter settings");
        String url = Function.getCurrentUrl();
        Verify.isTrue(!url.contains("?filter="), "Filter is stay applied! ");

        selectLayoutDropdown(true);
        String defaultLayoutName = Find.getText(defaultLayoutItem);

        selectLayoutDropdown(false);
        String selectedLayoutName = Find.getText(selectedLayouts);

        Verify.isTrue(selectedLayoutName.contains(defaultLayoutName), "Filter is not reset");
    }

    private void selectDropdown(boolean expectedToBeShown, By dropdown, By openLocator)
    {
        selectDropdown(expectedToBeShown, Find.options().locator(dropdown), openLocator);
    }

    private void selectDropdown(boolean expectedToBeShown, FindOptions dropdown, By openLocator)
    {
        int attempts = 0;

        while (attempts < 10 && isDropdownOpen(openLocator) != expectedToBeShown) {
            Log.story("Click the dropdown. Attempt: " + attempts);
            Find.click(dropdown.checkForNoSpinner(true));
            attempts++;
        }
    }

    private boolean isDropdownOpen(By openLocator)
    {
        return isDropdownOpen(Find.options().locator(openLocator).timeoutMS(500));
    }

    private boolean isDropdownOpen(FindOptions option)
    {
        return Find.element(option.failOnNotFound(false)) != null;
    }

    private void selectLayoutDropdown(boolean expectedToBeShown)
    {
        selectDropdown(expectedToBeShown, layoutDropdown, layoutDropdownOpen);
    }

    private void selectFilterDropdown(boolean expectedToBeShown)
    {
        if (!expectedToBeShown && Log.featureFlag("CEFP_PERFORMANCE", seeFilersResultButtonPerf)) {
            selectDropdown(expectedToBeShown, seeFilersResultButtonPerf, filterBarOpen);
        } else {
            selectDropdown(expectedToBeShown, Log.featureFlag("CEFP_PERFORMANCE",
                    Find.options().locator(editFiltersButtonPerf)) ? filterDropdownPerf : filterDropdown, filterBarOpen);
        }
    }

    public void checkSavedLayoutIsApplied(String layoutName)
    {
        Log.info("Verify that saved layout is applied");
        Function.slowEnvironmentWait(Duration.ofSeconds(1), "Wait for page to settle");
        Verify.isFound(resetLayoutButton, "Reset layout button not found");
        verifyThatFilterSelected(layoutName);
    }

    public RankToolForm clickOnRankButton()
    {
        Log.info("Click on icon: " + ToolbeltIcon.rankButton);
        Find.click(ToolbeltIcon.rankButton.findOptions);
        return new RankToolForm();
    }

    private void clickOnToolbeltIcon(ToolbeltIcon toolbeltIcon)
    {
        Log.info("Click on icon: " + toolbeltIcon);
        Find.click(toolbeltIcon.findOptions);
    }

    public void verifyThatToolbeltIconEnabled(ToolbeltIcon toolbeltIcon)
    {
        Log.info("Verify that " + toolbeltIcon + "is enabled");
        Verify.isFound(toolbeltIcon.findOptions.enabled(true), toolbeltIcon + " ToolbeltIcon is disabled");
    }

    public void verifyThatToolbeltIconDisabled(ToolbeltIcon toolbeltIcon)
    {
        Log.info("Verify that " + toolbeltIcon + "is disabled");
        Verify.isNotFound(toolbeltIcon.findOptions.enabled(true), toolbeltIcon + " ToolbeltIcon is enabled");
    }

    public void exportListingInvestor(ExportTemplates template, ExportDateFormat exportDateFormat, ExportFileFormat exportFileFormat)
    {
        Log.info("Export selected listing");
        ExportListingForm exportListingForm = openExportPopup();
        exportListingForm.selectTemplateByName(template);
        exportListingForm.selectDateFormat(exportDateFormat);
        exportListingForm.selectFileFormat(exportFileFormat);
        exportListingForm.clickExportButton();
        Verify.isNotFound(exportingButton, "Long file download");
    }

    public void exportNewCustomTemplate(String customTemplate, List<String> options, ExportDateFormat exportDateFormat, ExportFileFormat exportFileFormat, Date from, Date to)
    {
        Log.info("Create new custom template");
        ExportListingForm exportListingForm = openExportPopup();
        exportListingForm.createCustomExportTemplate(customTemplate, options);
        exportListingForm.selectDateFormat(exportDateFormat);
        exportListingForm.selectFileFormat(exportFileFormat);
        exportListingForm.selectPerformancePeriod(from, to);
        exportListingForm.clickExportButton();
        Verify.isNotFound(exportingButton, "Long file download");
    }

    public void exportNewCustomTemplate(String customTemplate, List<String> options, ExportDateFormat exportDateFormat, ExportFileFormat exportFileFormat)
    {
        Log.info("Create new custom template");
        ExportListingForm exportListingForm = openExportPopup();
        exportListingForm.createCustomExportTemplate(customTemplate, options);
        exportListingForm.selectDateFormat(exportDateFormat);
        exportListingForm.selectFileFormat(exportFileFormat);
        exportListingForm.clickExportButton();
        Verify.isNotFound(exportingButton, "Long file download");
    }


    public void editCustomTemplate(String customTemplate, String newCustomTemplate, List<String> options)
    {
        Log.info("Create new custom template");
        ExportListingForm exportListingForm = openExportPopup();
        exportListingForm.editCustomTemplate(customTemplate, newCustomTemplate, options);
    }

    public void deleteCustomTemplate(String customTemplate)
    {
        Log.info("Delete custom template");
        ExportListingForm exportListingForm = openExportPopup();
        exportListingForm.deleteCustomTemplate(customTemplate);
    }

    public void closeExportPopup()
    {
        Log.info("Click close export popup button");
        ExportListingForm exportListingForm = new ExportListingForm();
        exportListingForm.closePopup();
    }

    public void clickOnColumnToSort(ColumnName columnNames)
    {
        Log.info("Click on column header to apply sorting: " + columnNames);
        Find.click(columnNames.headerLocator);
    }

    public void verifyThatColumnIsSorted(ColumnName columnName, boolean descending)
    {
        Log.story("Verify that table is sorted by " + (descending ? "DESC" : "ASC") + " for column " + columnName);

        List<String> values = new ArrayList<>(Find.getTexts(columnName.columnContentLocator));
        List<String> sortedValues = new ArrayList<>(values);

        if (columnName == ColumnName.latestDateReported) {
            sortedValues = sortQuarters(values, descending);
        } else {
            sortedValues.sort(descending ? Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER) : String.CASE_INSENSITIVE_ORDER);
        }
        Log.object("Found", values);
        Log.object("Sorted", sortedValues);
        Verify.isTrue(values.equals(sortedValues), "Column is not sorted by " + (descending ? "DESC" : "ASC"));
    }

    private List<String> sortQuarters(List<String> quarters, boolean descending)
    {
        return quarters.stream()
                .map((q) -> {
                    List<String> parts = Arrays.asList(q.split(" "));
                    Collections.reverse(parts);
                    return String.join(" ", parts);
                })
                .sorted(descending ? Collections.reverseOrder() : String.CASE_INSENSITIVE_ORDER)
                .map((q) -> {
                    List<String> parts = Arrays.asList(q.split(" "));
                    Collections.reverse(parts);
                    return String.join(" ", parts);
                }).collect(Collectors.toList());
    }

    public void verifyThatEntityIsDisplayedOnListing(List<String> names)
    {
        Log.story("Verify that table contains expected entity");
        for (String name : names) {
            Verify.isFound(Find.options().locator(By.cssSelector(String.format(rowLocatorString, name))), "Row " + name + " is not displayed");
        }
    }

    public void verifyThatColumnIsDisplayed(List<String> columns)
    {
        Log.story("Verify that columns is displayed");
        for (String column : columns) {
            Verify.isFound(Find.options().locator(columnHeaders).findByText(FindByText.by().startsWith(column)), "Column " + column + " is not displayed");
        }
    }

    public void verifyDataSettingsStatus(DataSettings dataSettings, boolean expectedStatus)
    {
        Log.story("Verify that data settings toggle is selected");
        Find.click(dataSettingDropdown);
        String attribute = Find.getAttribute(
                Find.options()
                        .locator(settingTogglesInput)
                        .parentOption(Find.options()
                                .locator(settingToggles)
                                .findByText(FindByText.by().startsWith(dataSettings.dropdownOption))
                        ),
                "aria-checked");
        Verify.isEqual(attribute, String.valueOf(expectedStatus), dataSettings.dropdownOption + ": Data Settings is not selected");
    }

    public void selectOnSinceInception(SinceInception sinceInception)
    {
        Find.click(Find.elements(statisticsDropdowns).get(0));
        Find.click(sinceInception.findOptions);
        Find.click(Find.elements(statisticsDropdowns).get(0));
    }

    public void selectRfr(RFR rfr)
    {
        Find.click(Find.elements(statisticsDropdowns).get(1));
        Find.click(rfr.findOptions);
    }

    public int findFirstRankEnabledInvestorRowIndex()
    {
        int indexFound = 0;
        for (WebElement checkBox : Find.elements(allCheckBoxes)) {
            Find.click(checkBox);  // (select)
            if (Find.element(ToolbeltIcon.rankButton.findOptions.failOnNotFound(false).timeoutMS(2000).clickable(true)) != null) {
                return indexFound;
            }
            Find.click(checkBox); // (unselect)
            indexFound++;
        }
        Verify.fail("No rank-enabled investors found in attempts: " + indexFound);
        return 0;
    }

    public ProfilePage selectFundWithPerformanceFrom2018(List<String> funds)
    {
        for (String fundName : funds) {
            this.enterInSearchField(fundName, fundName.length());
            ProfilePage profilePage = this.clickOnRowByName(fundName);
            ProfileTableComponent perfTable = new ProfileTableComponent("Monthly returns");
            List<String> years = perfTable.getColumnValues("Year");

            if (years.contains("2018")) {
                return profilePage;
            } else {
                Function.closeCurrentTab();
            }
        }
        return null;
    }

    public List<String> getRowData(int rowIndex, List<String> filters)
    {
        Log.story("Get cells values from listing for row: " + rowIndex);
        List<String> values = new ArrayList<>();
        filters.forEach(filterName -> values.add(getCellValues(rowIndex, filterName)));
        return values;
    }

    public void verifyAllHistoryIsForInvestor(String investorName)
    {
        List<String> allResults = Find.getTexts(By.cssSelector("[role='rowgroup'] [role='cell']:first-of-type"));
        Verify.isTrue(allResults.stream().filter(r -> r.equals(investorName)).toList().size() == allResults.size(), "Not all results are for " + investorName);
    }

    public void setShowPerformanceAsToDataFilter(String value)
    {
        Log.story("Set Show Performance as to for listing with value " + value);
        Find.click(showPerformanceAsToDataFilter);
        Find.click(Find.options().locator(showPerformanceAsToDataOptions).findByText(FindByText.by().equals(value)));
        Verify.isNotFound(spinner, "Page is not loaded");
    }

    public List<String> dragAndDropFiltersInOrder(List<String> expectedOrder)
    {
        Log.story("Drag and drop filters in order");
        Find.click(editFiltersButtonPerf);
        List<WebElement> dragAndDropElementsStatic = Find.elements(Find.options().locator(filterDragAndDropTitlesDisabled));
        List<String> allOrder = new ArrayList<>(Find.getTexts(dragAndDropElementsStatic));
        for (String filter : expectedOrder) {
            int actualIndex = Find.getTexts(Find.options().locator(filterDragAndDropButton)).indexOf(filter);
            int expectedIndex = expectedOrder.indexOf(filter) + dragAndDropElementsStatic.size();
            if (actualIndex != expectedIndex) {
                Find.dragAndDrop(Find.element(Find.options().locator(filterDragAndDropButton).findByText(FindByText.by().contains(filter))),
                        Find.elements(Find.options().locator(filterDragAndDropButton)).get(expectedIndex));
            }
        }
        allOrder.addAll(expectedOrder);
        Find.click(Find.options().locator(doneFiltersButton).findByText(FindByText.by().contains("Done")));
        return allOrder;
    }

    public void verifyColumnOrder(List<String> columns)
    {
        Log.story("Verify that columns is displayed in expected order");
        Verify.isTrue(Find.getTexts(Find.options().locator(columnHeaders)).equals(columns), "Columns in expected order");
    }

    public void verifyFilterApplied(ColumnName columnName, List<String> expectedList)
    {
        Log.story("Verify that filter is applied for listing and relevant results are displayed");
        SingleTable singleTable = new SingleTable();
        List<String> cellValues = singleTable.getColumnValues(columnName.headerText);

        boolean allValuesInExpectedList = cellValues.stream()
                .map(value -> value.split(", ")[0])
                .allMatch(expectedList::contains);

        Verify.isTrue(allValuesInExpectedList, "Not all values from listing contains in expected option list");
    }

    public void verifyThatAllMandatesAreService()
    {
        Log.story("Verify that all mandates on listing are service mandate");
        Find.getAttributes(Find.options().locator(tableRow), "href").stream().forEach(
                href -> {
                    String id = NumberHelper.extractDigits(href);
                    Verify.isTrue(id.startsWith("100"), id + " is not service mandate id");
                });
    }
}