package com.tests.pageobjects.platform;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.library.enums.Site;
import com.library.helpers.Function;
import com.library.helpers.NumberHelper;
import com.library.helpers.StringHelper;
import com.tests.enums.platform.EntityCard;
import com.tests.enums.platform.RFR;
import com.tests.enums.platform.SinceInception;
import com.tests.enums.platform.UnitDenomination;
import com.tests.helpers.platform.TagsHelper;
import com.tests.pageobjects.baseobjects.BasePageObject;
import com.tests.pageobjects.platform.forms.CompareFindsForm;
import com.tests.pageobjects.platform.forms.RankToolForm;
import com.tests.pageobjects.platform.forms.StatisticsForm;
import com.tests.pageobjects.platform.forms.components.ProfileCardComponent;
import com.tests.pageobjects.platform.forms.components.ProfileSectionComponent;
import com.tests.pageobjects.platform.forms.components.ProfileTableComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ProfilePage extends BasePageObject
{
    private final By pageLoadIdentifier = By.cssSelector("[class*='OverviewPanelstyled__HeadersWrapper'], #intro > [class*='ProfileIntroHeaderstyled']");
    private final By sectionLinks = By.cssSelector("[class*='ProfileContentstyled__ToCWrapper'] a[href]");
    private final By sectionBlock = By.cssSelector("div[class*='ProfileTablesstyled__GroupSection'][id], div[class*='SectionWrapper'][id]");
    private final By exportButton = By.cssSelector("[class*='OverviewPanelstyled__ActionContainer'] button[class*='export-button']");
    private final By lockBlockIcon = By.cssSelector("[class*='UpgradeCTAstyled'] [data-icon='lock']");
    private final By profileHeadline = By.cssSelector("h1[class*='OverviewPanelstyled__Heading'], [class*='ContactInfoHeadeing']");
    private final By likedLink = By.cssSelector("[class*='OverviewPanelstyled__AnchorWrapper'] a");
    public final By tableProfileParamElement = By.cssSelector("[data-testid*='StatisticsBar-Value-Container'] [class*='StatisticsBarstyled__Statisti']");
    private final By profileHeaderLabels = By.cssSelector("h1 [class*='LozengeText']");
    private final By followButton = By.cssSelector("[class*='ProfileHeader'] div:not([id='fund-ranking-tool-modal-open']) >[data-testid*='followEntity']");
    private final By followButtonStarIcon = By.cssSelector("[class*='ProfileHeader'] [data-testid*='followEntity'] > span");
    private final By spinnerPlaceholderLines = By.cssSelector("[class*='ProfileContentstyled__ToCWrapper'] [data-testid='placeholder-line']");
    private final By seeWebsiteButton = By.cssSelector("[icon='navigation-external']");
    private final By compareButton = By.cssSelector("[data-testid='fundCompare-triggerElement-Btn']");
    private final By rankButton = By.id("fund-ranking-tool-modal-open");
    private final By essentialsTabs = By.cssSelector("[data-testid*='profile-tab-handler']");
    private final By signalArticlesBlocks = By.cssSelector("[data-testid*='signalCard-']");
    private final By signals = By.cssSelector("[data-testid*='signalCard']:not(:has([class*='FeedTypeText']))");
    private final By articleHeadline = By.cssSelector("[class*='SignalCardstyled__Headline']");
    private final By latestMandatesButton = By.cssSelector("[id*='latest-mandates'] a");
    private final By linkedInButton = By.cssSelector("[class*='ProfileIntroHeaderstyled'] > [data-gtm-label='linkedIn']");
    private final By profileTags = By.cssSelector("[class*='ProfileIntroHeaderstyled'] [data-testid='tag']");
    private final By tagStatus = By.cssSelector("[data-testid='icon-wrapper']");
    private final By switchModulePopup = By.cssSelector("[data-testid='access-popup']");

    public ProfilePage()
    {
        super();
        if (Find.elements(Find.options().locator(switchModulePopup).failOnNotFound(false).timeoutMS(1500)) != null) {
            Log.debug("Profile with switch module has been opened: id =" + getId());
        } else {
            correctPage(pageLoadIdentifier);
            Verify.isFound(Find.options().locator(followButton).enabled(true).clickable(true), "Page is not loaded");
        }
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
        return Find.getText(Find.options().locator(profileHeadline).staleRefRetry(true)).split("\n")[0];
    }

    public void clickOnSectionLink(String section)
    {
        Log.story("Click on section: " + section);
        Find.click(Find.options().locator(sectionLinks).findByText(FindByText.by().contains(section)).scrollTo(true));
    }

    public void verifyThatSectionIsDisplayed(String section)
    {
        Log.story("Verify that section is displayed : " + section);
        Verify.isFound(By.cssSelector("[id*='" + StringHelper.toKebabCase(section) + "']"), "Block is not displayed !:" + section);
    }

    public boolean isDisplaySection(String section)
    {
        Log.story("Verify that section is displayed : " + section);
        return Find.elements(Find.options().locator(By.cssSelector("[id*='" + StringHelper.toKebabCase(section) + "']")).failOnNotFound(false).timeoutMS(3000).checkForNoSpinner(true)) != null;
    }

    public boolean isDisplaySeeFullHistoryButton()
    {
        Log.story("Verify that element is displayed");
        List<WebElement> elements = Find.elements(Find.options().locator(By.cssSelector("[id*='activity'] button")).findByText(FindByText.by().contains("See full history")).failOnNotFound(false).timeoutMS(1500));
        if (elements == null) {
            return false;
        }
        return !elements.isEmpty();
    }

    public void sectionIsHiddenToView(String section)
    {
        Log.story("Verify that section is displayed : " + section);
        Verify.isNotFound(Find.options().locator(sectionBlock).findByText(FindByText.by().contains(section)).parentLocator(lockBlockIcon), "Block is not locked ! :" + section);
    }

    public void clickOnExportButton()
    {
        Log.story("Click on export button for profile");
        Find.click(exportButton);
    }

    public void verifyThatExportButtonIsNotDisplayed()
    {
        Log.story("Verify that export button is displayed");
        Verify.isNotFound(exportButton, "Export button is displayed");
    }

    public ProfilePage clickOnLinkedProfileLink()
    {
        Log.story("Click on fund linked link from profile");
        Find.click(Find.options().locator(likedLink).clickable(true).scrollTo(true));
        return new ProfilePage();
    }

    public String getInfoLabelValue(String parameter)
    {
        Log.story("Get label value: " + parameter);
        return Find.getText(Find.options().locator(tableProfileParamElement).visible(true).findByText(FindByText.by().contains(parameter)))
                .replace(parameter, "").trim();
    }

    public void verifyInfoLabel(String parameter, String expectedValue)
    {
        Log.story("Verify value of label from profile: " + parameter);
        String param = Find.getText(Find.options().locator(tableProfileParamElement).visible(true).findByText(FindByText.by().contains(parameter)));
        if (parameter.contains("AUM")) {
            // Hack as this method is used in many tests. The haystack and needle are reversed due to display text
            // Example: expectedValue: "69" and paramFound: "AUD 69m"
            String temp = String.valueOf(Math.toIntExact(Math.round(NumberHelper.extractDouble(param))));
            param = expectedValue;
            expectedValue = temp;
        }
        if (parameter.contains("YTD")) {
            param = converYTDToString(NumberHelper.extractDouble(param));
        }

        Verify.contains(param, expectedValue, "Profile params not equal. Parameter: [" + parameter + "] text Found: [" + param + "] does not contain text: [" + expectedValue + "]");
    }

    public String converYTDToString(Double apiValue)
    {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.UK);
        DecimalFormat decimalFormat = new DecimalFormat("#.##", symbols);
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        Log.debug("Api value: " + apiValue);
        String returnValue = decimalFormat.format(apiValue);
        Log.debug("Rounded value: " + returnValue);
        return returnValue;
    }

    public void verifyParamIsNotDisplayed(String parameter)
    {
        Log.story("Verify that profile param is not displayed: " + parameter);
        Verify.isNotFound(Find.options().locator(tableProfileParamElement).findByText(FindByText.by().contains(parameter)),
                parameter + " is displayed for profile");
    }

    public void verifyTitleLabel(String parameter)
    {
        Log.story("Verify value of title label from profile: " + parameter);
        Verify.isFound(Find.options().locator(profileHeaderLabels).visible(true).findByText(FindByText.by().equals(parameter)),
                parameter + " is not displayed as header label");
    }

    public void clickFollow()
    {
        Log.story("Click on follow button");
        this.toggleFollow("star-empty", "star");
    }

    public void clickUnfollow()
    {
        Log.story("Click on unfollow button");
        this.toggleFollow("star", "star-empty");
    }

    private void toggleFollow(String currentStatus, String expectedStatus)
    {
        Function.slowEnvironmentWait(Duration.ofSeconds(1), "Allow follow button to settle");
        String status = Find.getAttribute(Find.options().locator(followButtonStarIcon).clickable(true), "data-icon");
        if (!Objects.equals(status, expectedStatus)) {
            Find.click(Find.options().locator(followButton).enabled(true).clickable(true));
            Function.slowEnvironmentWait(Duration.ofSeconds(2), "Allow time for icon to change");
            Verify.hasAttribute(Find.options().locator(followButtonStarIcon).clickable(true), "data-icon", expectedStatus);
        } else {
            Log.debug("Already " + (currentStatus.equals("star") ? "followed" : "unfollowed"));
        }
    }

    public void verifyFollowState(boolean followed)
    {
        Log.story("Verify that follow state is : " + followed);
        String expectedStatus = followed ? "star" : "star-empty";
        String status = Find.getAttribute(Find.options().locator(followButtonStarIcon).enabled(true), "data-icon");
        Verify.isTrue(status.equals(expectedStatus), "Profile is not " + (followed ? "followed" : "unfollowed"));
    }

    public void clickOnSeeWebsite()
    {
        Log.story("Click on See Website button");
        Find.click(seeWebsiteButton);
        Function.switchWindow(null);
    }

    public void verifyThatExternalWebsiteIsOpened()
    {
        Log.story("Verify that external link is opened");
        Verify.isTrue(!Function.getCurrentUrl().equals(Site.platform.url()), "Not opened external link");
    }

    public CompareFindsForm clickOnCompareButton()
    {
        Log.story("Click on compare button on profile");
        Find.click(Find.options().locator(compareButton).scrollTo(true).staleRefRetry(true));
        return new CompareFindsForm();
    }

    public RankToolForm clickOnRankButton()
    {
        Log.story("Click on rank button on profile");
        Find.click(Find.options().locator(rankButton).scrollTo(true).staleRefRetry(true));
        return new RankToolForm();
    }

    public void openSignalTab()
    {
        Log.story("Open Signal tab");
        Find.click(Find.options().locator(essentialsTabs).findByText(FindByText.by().contains("Signal")));
    }

    public void selectSinceInception(SinceInception sinceInception)
    {
        StatisticsForm statisticsForm = new StatisticsForm();
        statisticsForm.selectSinceInception(sinceInception);
    }

    public void profileHasLinkedArticle(String title)
    {
        Log.story("Verify that profile contains article");
        Verify.isFound(Find.options().locator(signalArticlesBlocks).findByText(FindByText.by().contains(title)), "Profile does not contain linked article");
    }

    public void selectRfrPercentages(RFR rfr)
    {
        StatisticsForm statisticsForm = new StatisticsForm();
        statisticsForm.selectRfrPercentages(rfr);
    }

    public void verifyValuesInRiskTable(Double sharpeRatio, Double annualizedStandardDeviation, Double maximumDrawdown)
    {
        StatisticsForm statisticsForm = new StatisticsForm();
        Verify.isEqual(sharpeRatio, statisticsForm.getStatisticTableValue("Sharpe ratio"), "sharpeRatio is not equal", true, "NODEV-1028");
        Verify.isEqual(annualizedStandardDeviation, statisticsForm.getStatisticTableValue("Annualized standard deviation"), "Annualized standard deviation is not equal", true, "NODEV-1028");
        Verify.isEqual(maximumDrawdown, statisticsForm.getStatisticTableValue("Maximum drawdown"), "Maximum drawdown is not equal", true, "NODEV-1028");
    }

    public void verifyThatSignalsNotContainTitles()
    {
        Log.story("Verify that profile signals do not contain titles");
        List<WebElement> signalsList = Find.elements(signals);
        for (WebElement signalBlock : signalsList) {
            Verify.isNotFound(Find.options().locator(articleHeadline).parent(signalBlock), "Signal has title");
        }
    }

    public EntityListingPage clickOnLatestMandates()
    {
        Log.story("Click on Latest Mandates button");
        Find.click(Find.options().locator(latestMandatesButton).clickable(true).scrollTo(true));
        return new EntityListingPage(EntityCard.mandates);
    }

    public EntityListingPage clickOnSeeFullHistory()
    {
        Log.story("Click See Full History");
        Find.click(Find.options().locator(By.cssSelector("[id*='activity'] button")).findByText(FindByText.by().contains("See full history")).clickable(true).scrollTo(true));
        return new EntityListingPage(EntityCard.mandates);
    }

    public void verifyThatConsultantIsLinked(String consultantName)
    {
        Log.story("Verify that consultant is linked");
        try {
            ProfileSectionComponent serviceProviders = new ProfileSectionComponent("Service providers");
            serviceProviders.verifyThatTableHasValue(consultantName);
        } catch (AssertionError error) {
            ProfileCardComponent profileCardComponent = new ProfileCardComponent("Consultants");
            profileCardComponent.verifyThatCardWithHeadlineIsDisplayed(consultantName);
        }
    }

    public void verifyRedirectOnLinkedInProfile()
    {
        Log.story("Click on linkedIn button for People profile and verify link");
        String link = Find.getAttribute(Find.element(linkedInButton), "data-gtm-value");
        Find.click(linkedInButton);
        Function.switchWindow(null);
        String expected = link.substring(link.lastIndexOf("/")).replace("/", "");
        Verify.contains(Function.getCurrentUrl(), expected, "Expected linkedin page is not opened");
    }

    public void verifyAUMinTable(String table, String label, double expectedAum)
    {
        Log.story("Verify last aum value in table " + table);
        Verify.isEqual(expectedAum, findAumFromFundAseetsTable(table), "AUM value is not equal");

        Log.story("Verify header aum value in label " + label);
        double headerAumValue = findAumLabelValue(label);
        double expectedAumInBillions = NumberHelper.round(UnitDenomination.convert(expectedAum, UnitDenomination.million, UnitDenomination.billion), 1);
        Verify.isEqual(expectedAumInBillions, headerAumValue, "Profile params not equal: [" + label + "] does not contain text: [" + expectedAum + "]");
    }

    private double findAumFromFundAseetsTable(String table)
    {
        ProfileTableComponent fundAssets = new ProfileTableComponent("Performance " + table);
        double aum = NumberHelper.extractDouble(fundAssets.getLastNotNullValueInRow(1));
        Log.debug("AUM found: " + aum);
        return aum;
    }

    private double findAumLabelValue(String label)
    {
        String headerText = Find.getText(Find.options().locator(tableProfileParamElement).visible(true).findByText(FindByText.by().contains(label)));
        double labelAum = NumberHelper.extractDouble(headerText);
        Log.debug("Label AUM found: " + labelAum);
        return labelAum;
    }

    public String clickOnFirstAvailableProfileTag()
    {
        Log.story("Click on first available tag");
        List<String> getTags = TagsHelper.getTags(List.of(), profileTags);
        TagsHelper.followUnFollowTag(true, getTags.get(0), profileTags, tagStatus, spinnerPlaceholderLines);
        return getTags.get(0);
    }

    public String clickOnCard(String cardsBlockName)
    {
        Log.story("Clicking on key contacts");
        ProfileCardComponent profileCardComponent = new ProfileCardComponent(cardsBlockName);
        String cardTitle = profileCardComponent.getFirstCardTitle();
        profileCardComponent.clickOnCard(cardTitle);
        Function.switchWindow(null);
        return cardTitle;
    }
}
