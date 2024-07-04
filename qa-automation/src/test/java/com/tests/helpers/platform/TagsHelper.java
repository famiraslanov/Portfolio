package com.tests.helpers.platform;

import com.library.Find;
import com.library.Log;
import com.library.Verify;
import com.library.classes.FindByText;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class TagsHelper
{
    public static List<String> getTags(List<String> exceptNavigationList, By tagsLocator)
    {
        Log.story("Get all article tags");
        List<String> filteredStrings;
        filteredStrings = Find.getTexts(Find.options().locator(tagsLocator).checkForNoSpinner(true));
        return filteredStrings.stream().filter(s -> !exceptNavigationList.contains(s)).collect(Collectors.toList());
    }

    public static String getTagFollowedStatus(String tagName, By tagsLocator, By statusLocator, By spinner)
    {
        Verify.isNotFound(spinner, "Spinner still seen");

        return Find.getAttribute(Find.options().locator(statusLocator).parentOption(
                Find.options().locator(tagsLocator).clickable(true).findByText(FindByText.by().contains(tagName)).returnFirst(true)), "data-icon");
    }

    public static void followUnFollowTag(boolean follow, String tagName, By tagsLocator, By statusLocator, By spinner)
    {
        String equalsText = follow ? "plus" : "checkmark";

        Log.story("Click on tag: " + tagName + " to follow: " + follow);
        String tagFollowedStatus = getTagFollowedStatus(tagName, tagsLocator, statusLocator, spinner);

        if (!tagFollowedStatus.equals(equalsText)) {
            Log.debug("Checkbox already at state: " + equalsText);
            return;
        }
        Find.click(Find.options().locator(tagsLocator).findByText(FindByText.by().contains(tagName)).checkForNoSpinner(true).returnFirst(true));
        Verify.isNotFound(spinner, "Spinner is still displayed ");
    }
}
