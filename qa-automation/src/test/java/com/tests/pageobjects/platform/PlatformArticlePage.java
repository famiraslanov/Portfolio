package com.tests.pageobjects.platform;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import com.library.helpers.Function;
import com.library.helpers.StringHelper;
import com.tests.enums.platform.AssetClass;
import com.tests.helpers.platform.SearchApiHelper;
import com.tests.helpers.platform.TagsHelper;
import com.tests.pageobjects.baseobjects.BasePageObject;
import org.apache.commons.collections4.CollectionUtils;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class PlatformArticlePage extends BasePageObject
{
    private final By headlineLabel = By.cssSelector("[data-testid*='articleView-headline']");
    private final By headerBreadcrumb = By.cssSelector("span[class*='BreadcrumbActive']");
    private final By tagBox = By.cssSelector("[data-testid='tag']");
    private final By tagStatus = By.cssSelector("[data-testid='icon-wrapper']");
    private final By relatedLinks = By.cssSelector("[class*='RelatedList'] a[class*='RelatedItemLinkAction']");
    private final By spinner = By.cssSelector("[data-testid='spinner']");
    private final By downloadPdfButton = By.cssSelector("[data-testid='printArticle-printBtn']");
    private final By relateArticlesLink = By.cssSelector("[class*='RelatedItemLinkAction'] > span[class*='StoriesItemTitle']");
    private final By relateArticlesItem = By.cssSelector("[class*='RelatedItemLinkAction']");
    private final By nextArticleLink = By.cssSelector("a[data-testid='siblings-nextSibling'] div[class*='SiblingsBoxTitle']:not([class*='SiblingsBoxTitleText'])");
    private final By previousArticleLink = By.cssSelector("a[data-testid='siblings-prevSibling'] div[class*='SiblingsBoxTitle']:not([class*='SiblingsBoxTitleText'])");
    private final By contentArticle = By.cssSelector("[data-testid='articleView-content']");

    private final AssetClass selectedAssetClass;

    public PlatformArticlePage(AssetClass assetClass)
    {
        super();
        selectedAssetClass = assetClass;
        correctPage(contentArticle);
    }

    public String getTitle()
    {
        Log.story("Get article title");
        if (Find.element(Find.options().locator(headlineLabel).failOnNotFound(false)) != null) {
            return Find.getText(Find.options().locator(headlineLabel).failOnNotFound(false));
        }
        return null;
    }

    private String getTagFollowedStatus(String tagName)
    {
        return TagsHelper.getTagFollowedStatus(tagName, tagBox, tagStatus, spinner);
    }

    public List<String> getTags()
    {
        return getTags(List.of());
    }

    public List<String> getTags(List<String> exceptNavigationList)
    {
        return TagsHelper.getTags(exceptNavigationList, tagBox);
    }

    public List<String> getNotNavigationTags(AssetClass assetClass)
    {
        Log.story("Get all not navigation article tags");
        List<String> exceptNavigationList = SearchApiHelper.getListOfNavigationTags(getId(), assetClass);
        return getTags(exceptNavigationList);
    }

    public void followTag(String tagName)
    {
        TagsHelper.followUnFollowTag(true, tagName, tagBox, tagStatus, spinner);
    }

    public void unfollowTag(String tagName)
    {
        TagsHelper.followUnFollowTag(false, tagName, tagBox, tagStatus, spinner);
    }

    public void verifyThatTitleIsCorrect(String title)
    {
        String headLineLabelTitle = getTitle();
        if (headLineLabelTitle != null) {
            Verify.contains(headLineLabelTitle, title, "Article title is not as expected");
        } else if (Find.element(Find.options().locator(headerBreadcrumb).failOnNotFound(false)) != null) {
            Log.info("Article title is hidden. Verification by title in breadcrumb");
            Verify.isTrue(Find.getText(headerBreadcrumb).contains(title), "Article title is not as expected");
        } else {
            Log.info("Article title and Breadcrumb are hidden. Verification by url");
            String url = Function.getCurrentUrl();
            Verify.contains(url, StringHelper.toKebabCase(title.toLowerCase()), "Unexpected url has been opened");
        }
    }

    private void verifyThatTagBoxSelectedStatus(String tagName, boolean selected)
    {
        Log.story("Verify that tag box has or no select mark: " + tagName);
        By marker = By.cssSelector(selected ? "[data-icon='checkmark']" : "[data-icon='plus']");
        Verify.isFound(Find.options().locator(marker).parentOption(Find.options().locator(tagBox).findByText(FindByText.by().contains(tagName)).returnFirst(true)), tagName + " tag box is not correctly " + (selected ? "selected" : "unselected"));
    }

    public void verifyThatTagIsFollowed(String tagName)
    {
        Log.story("Verify that tag is not followed: " + tagName);
        Verify.isTrue(getTagFollowedStatus(tagName).equals("checkmark"), tagName + " is not followed");
        verifyThatTagBoxSelectedStatus(tagName, true);
    }

    public void verifyThatTagIsUnfollowed(String tagName)
    {
        Log.story("Verify that tag is not followed: " + tagName);
        Verify.isTrue(getTagFollowedStatus(tagName).equals("plus"), tagName + " is still followed");
        verifyThatTagBoxSelectedStatus(tagName, false);
    }

    public void clickOnTagBox(String tag)
    {
        Log.story("Click on tag box for filter: " + tag);
        Find.click(Find.options().locator(tagBox).findByText(FindByText.by().startsWith(tag)).checkForNoSpinner(true).returnFirst(true));
        Function.slowEnvironmentWait();
    }

    public String getTitleRelatedArticleLink(int index)
    {
        Log.story("Get title of related article link: " + index);
        return Find.getText(Find.elements(relatedLinks).get(index));
    }

    public void clickOnDownloadPdfButton()
    {
        Log.story("Click on download pdf button");
        Verify.verifyPrint(downloadPdfButton);
    }

    public String getTitleRelatedProfileLink(int index, String entityType)
    {
        // This method has an issue if there are more than one profile link as the type of link it selects may not be the type of link from the article
        Log.story("Get title of related profile link: " + index);
        return Find.getTexts(Find.options().locator(
                By.cssSelector(String.format("[data-testid*='sideBarItem-%sProfiles'] li[class*='RelatedList']", entityType)))).get(index).split(" \\(")[0];
    }

    public PlatformArticlePage clickOnRelatedArticleLink(int index)
    {
        Log.story("Click on related article link: " + index);
        Find.click(Find.scrollToElement(Find.elements(relatedLinks).get(index)));
        return new PlatformArticlePage(selectedAssetClass);
    }

    public ProfilePage clickOnRelatedProfileLink(int index, String entityType)
    {
        Log.story("Click on related profile link: " + index);
        Find.click(Find.scrollToElement(Find.elements((Find.options().locator(
                By.cssSelector(String.format("[data-testid*='sideBarItem-%sProfiles'] li[class*='RelatedList']", entityType))))).get(index)));
        return new ProfilePage();
    }

    public void verifyMatchingTagsFromNowPage(List<String> nowTags)
    {
        Log.story("Verify that tags are matched from now page");
        List<String> tags = this.getTags();
        List<String> normalizedNowTags = nowTags.stream().map(tag -> tag.length() > 27 ? tag.substring(0, 27) : tag).toList();
        List<String> normalizedTags = tags.stream().map(tag -> tag.length() > 27 ? tag.substring(0, 27) : tag).toList();
        Verify.isTrue(normalizedNowTags.containsAll(normalizedTags), "Tags are not matched");
    }

    public List<String> getRelatedArticles()
    {
        Log.story("Get list of related articles");
        return Find.getTexts(Find.options().locator(relateArticlesLink).failOnNotFound(false).timeoutMS(300));
    }

    public List<String> getAvailableRelatedArticles(AssetClass assetClass)
    {
        Log.story("Get list of related articles which available for selected asset class");
        List<String> availableArticles = Find.elements(relateArticlesItem)
                .stream()
                .filter(relatedArticle -> {
                    int postId = Integer.parseInt(Find.getAttribute(relatedArticle, "href").split("/?=")[1]);
                    return !SearchApiHelper.getArticle(postId, assetClass).isEmpty();
                })
                .map(Find::getText)
                .collect(Collectors.toList());
        return availableArticles;
    }

    public PlatformArticlePage clickOnRelatedArticleLink(String name)
    {
        Log.story("Click on related article link: " + name);
        Find.click(Find.options().locator(relateArticlesLink).findByText(FindByText.by().equals(name)).scrollTo(true));
        return new PlatformArticlePage(selectedAssetClass);
    }

    public String getNextArticleTitle()
    {
        Log.story("Get next article text");
        return Find.getText(nextArticleLink);
    }

    public String getPreviousArticleTitle()
    {
        Log.story("Get Previous article text");
        return Find.getText(previousArticleLink);
    }

    private PlatformArticlePage clickOnArticleLink(By articleLink)
    {
        Log.story("Click on article link");
        Find.click(Find.options().locator(articleLink).clickable(true).scrollTo(true));
        return new PlatformArticlePage(selectedAssetClass);
    }

    public PlatformArticlePage clickOnNextArticleLink()
    {
        return clickOnArticleLink(nextArticleLink);
    }

    public PlatformArticlePage clickOnPreviousArticleLink()
    {
        return clickOnArticleLink(previousArticleLink);
    }
}