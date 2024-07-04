package com.tests.pageobjects.platform;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.tests.enums.platform.AssetClass;
import com.tests.enums.platform.PrimaryNavigationItem;
import org.openqa.selenium.By;

import java.util.List;

public class SecondaryNowPage extends StartingPage
{
    private final By actionNavigationElement = By.cssSelector("[class*='BreadcrumbActive']");
    private final By activeFilterElement = By.cssSelector("[data-testid*='filterGroupText'] + span");
    private final By filtersNamesLabels = By.cssSelector("[class*='FilterGroupstyled__GroupControls']");
    private final By filterListElements = By.cssSelector("label[class*='Checkboxstyled']:has(input[aria-checked='false'])");
    private final By noFilterValuesLabel = By.cssSelector("[data-testid='optionSelect-noMatches']");
    private final By searchFilterTextbox = By.cssSelector("[aria-haspopup='listbox'] input");
    private final By tagsLists = By.cssSelector("div[class*='TagList']");
    private final By filterButton = By.cssSelector("[class*='FilterCardstyled'] [class*='Folderstyled__Handler']");
    private final By doneButton = By.cssSelector("[data-testid='dropdown-content'] button[class*='Buttonstyled__ButtonWrapper']");
    private final By seeResultsButton = By.cssSelector("[class*='FolderFilterStatisticsstyled__ActionGroup'] button");
    private final By trendingLinks = By.cssSelector("[class*='ArticleListingstyled__LeftSidebar'] a");

    public SecondaryNowPage(AssetClass assetClass)
    {
        super(assetClass);
        Find.hover(PrimaryNavigationItem.explore.findOptions);
    }

    public void verifyThatTagFilterApplied(String tag)
    {
        Log.story("Verify that regular or navigation tag is applied");
        Verify.isFound(Find.options().locator(filterSelector()).findByText(FindByText.by().contains(tag)), "The tag/navigation filter was not applied");
    }

    private By filterSelector()
    {
        if (Find.element(Find.options().locator(activeFilterElement).failOnNotFound(false)) != null) {
            return activeFilterElement;
        }

        return actionNavigationElement;
    }

    public String selectFilter(String filterName)
    {
        Log.story("Select filter: " + filterName);
        Find.click(filterButton);
        Find.click(Find.options().locator(filtersNamesLabels).findByText(FindByText.by().contains(filterName)));
        String optionText = Find.getTexts(Find.options().locator(filterListElements)).get(1);
        Find.insert(Find.options().locator(searchFilterTextbox).checkForNoSpinner(true), optionText);
        Find.click(Find.options().locator(filterListElements).checkForNoSpinner(true).findByText(FindByText.by().startsWith(optionText)));
        Find.click(Find.options().locator(doneButton).checkForNoSpinner(true).staleRefRetry(true).findByText(FindByText.by().equals("Done")));
        Find.click(Find.options().locator(seeResultsButton).findByText(FindByText.by().contains("See ")));
        return optionText;
    }

    public void verifyThatResultsAreRelevantToFilter(String option)
    {
        Log.story("Verify that all articles match the filter: " + option);
        for (String content : Find.getTexts(tagsLists)) {
            Verify.isTrue(content.contains(option), "Article doesn't contain filter option: " + option);
        }
    }

    public String getFirstTrendingEntity()
    {
        Log.story("Get first trending entity title");
        return Find.getText(Find.options().locator(trendingLinks).returnFirst(true));
    }

    public ProfilePage navigateToTrendingLink(String title)
    {
        Log.story("Navigate to trending link");
        Find.click(Find.options().locator(trendingLinks).findByText(FindByText.by().equals(title)));
        return new ProfilePage();
    }

    public void verifyThatNoEmptyFilters()
    {
        Log.story("Verify that not empty filters for default state");
        Find.click(filterButton);
        List<String> filters = Find.getTexts(Find.options().locator(filtersNamesLabels));
        for (String filter : filters) {
            Find.click(Find.options().locator(filtersNamesLabels).findByText(FindByText.by().contains(filter)));
            Verify.isNotFound(noFilterValuesLabel, "No filter value for " + filter + " filter");
        }
        Find.click(seeResultsButton);
    }

    public void verifyThatCorrectTrendLinksAreDisplayed(List<String> expectedLinks)
    {
        List<String> actualLinks = Find.getTexts(Find.options().locator(trendingLinks));
        Log.object("actualLinks", actualLinks);
        Log.object("expectedLinks", expectedLinks);
        actualLinks.stream().forEach(link->Verify.isTrue(expectedLinks.stream().filter(expectedLink->expectedLink.startsWith(link)).toList().size()==1, link + " is not displayed on page"));
    }
}
