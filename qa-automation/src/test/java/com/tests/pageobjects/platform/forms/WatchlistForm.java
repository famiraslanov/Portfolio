package com.tests.pageobjects.platform.forms;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.tests.enums.platform.AlertFrequency;
import com.tests.enums.platform.AssetClass;
import com.tests.pageobjects.baseobjects.BasePageObject;
import com.tests.pageobjects.platform.StartingPage;
import org.apache.commons.collections4.CollectionUtils;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class WatchlistForm extends BasePageObject
{
    private final By pageLoadIdentifier = By.cssSelector("[class*='Popupstyled__Wrapper']");
    private final By closeButton = By.cssSelector("[data-testid='popup-empty-value-crossBtn']");
    private final By mailIcon = By.cssSelector("[data-icon='mail']");
    private final By topicRow = By.cssSelector("[class*='SettingsFieldstyled__HeaderContent']");
    private final By saveButton = By.cssSelector("[class*='SettingsFieldstyled'] [type='submit']");
    private final By topicSectionName = By.cssSelector("[role='tabpanel'] > section > p");
    private final By topicSection = By.cssSelector("[role='tabpanel'] > section");

    private AssetClass selectedAssetClass;

    public WatchlistForm(AssetClass assetClass)
    {
        super();
        correctPage(pageLoadIdentifier);
        selectedAssetClass = assetClass;
    }

    public List<String> getAllTopics()
    {
        Log.story("Get list of topics");
        ArrayList<String> excludeSection = new ArrayList<>(Arrays.asList("Asset class", "Secondary industry"));
        List<String> sections = findSections();
        if (CollectionUtils.isEmpty(sections)) {
            setupWatchTopics();
            sections = findSections();
        }

        List<String> topics = new ArrayList<>();
        for (String section : sections) {
            if (!excludeSection.contains(section) && !Objects.equals(section, "")) {
                topics.addAll(Find.getTexts(Find.options()
                                .locator(topicRow)
                                .parentOption(Find.options()
                                        .locator(topicSection)
                                        .findByText(FindByText.by().startsWith(section))
                                )
                        )
                );
            }
        }
        return topics;
    }

    private List<String> findSections()
    {
        return Find.getTexts(Find.options().locator(topicSectionName).failOnNotFound(false));
    }

    private void setupWatchTopics()
    {
        Log.story("No watch topics setup so creating some now");
        closePopup();

        StartingPage startingPage = TopNavigationMenu.getInstance().navigateToNow(selectedAssetClass);
        startingPage.followAvailableArticles();
        startingPage.clickOnGoWatchlistButton();
    }

    public void selectAlert(String topic, AlertFrequency alertFrequency)
    {
        Log.story("Select alert Frequency " + alertFrequency + " for topic " + topic);
        Find.click(Find.options().locator(mailIcon).parentOption(Find.options().locator(topicRow).findByText(FindByText.by().startsWith(topic))));
        Find.hoverClick(alertFrequency.selectOptions);
        Find.hoverClick(Find.options().locator(saveButton));
        Verify.isNotFound(saveButton, "Form not saved ");
    }

    public void verifyTagAlertMode(String topic, AlertFrequency alertFrequency)
    {
        Log.story("Verify that alert Frequency is equal" + alertFrequency + " for topic " + topic);
        Find.click(Find.options().locator(mailIcon).parentOption(Find.options().locator(topicRow).findByText(FindByText.by().startsWith(topic))));
        Verify.isTrue(Find.element(alertFrequency.verifyStateOptions).isSelected(), "Expected  Alert Frequency is not checked");
    }

    public void closePopup()
    {
        Log.story("Click close button");
        Find.click(closeButton);
    }
}
