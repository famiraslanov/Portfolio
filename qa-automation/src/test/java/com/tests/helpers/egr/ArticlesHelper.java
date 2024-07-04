package com.tests.helpers.egr;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.helpers.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class ArticlesHelper
{


    public static void verifyThatAllCategoryTagsContainsArticle(By categoryTagLabels, By articlesBlocks, By articleTags)
    {
        Log.story("Verify that all articles relevant selected category");
        List<WebElement> tags = Find.elements(categoryTagLabels);
        for (WebElement tag : tags) {
            String tagName = Find.getText(tag);
            Log.debug("Click tag: " + tagName);
            Find.click(tag);
            Function.slowEnvironmentWait(Duration.ofSeconds(3), "Allow to load search results");

            List<WebElement> articles = Find.elements(articlesBlocks);
            for (WebElement article : articles) {
                List<String> articleTagsList = Find.getTexts(Find.options().locator(articleTags).parent(article));
                List<String> result = articleTagsList.stream().map(String::toLowerCase).toList();
                if (!Objects.equals(tagName, "All")) {
                    Verify.isTrue((result).contains(tagName.toLowerCase()), "'" + tagName + "' not found in the article tag list: " + articleTagsList, true, "OBP-367");
                }
            }
        }
    }

    public static void verifyThatAllArticleContainsSelectedTag(By articlesBlocks, By articleTags, String expectedTag)
    {
        Log.story("Verify that all article contain selected tag");
        List<WebElement> articles = Find.elements(articlesBlocks);
        for (WebElement article : articles) {
            List<String> articleTagsList = Find.getTexts(Find.options().locator(articleTags).parent(article));
            List<String> result = articleTagsList.stream().map(String::toLowerCase).toList();
            Verify.isTrue((result).contains(expectedTag.toLowerCase()), expectedTag + " tag not found for the article: " + articleTagsList, true, "OBP-367");
        }
    }
}
