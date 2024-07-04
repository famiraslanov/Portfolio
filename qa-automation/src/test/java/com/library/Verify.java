package com.library;

import com.library.helpers.DateHelper;
import com.library.helpers.FileHelper;
import com.library.helpers.Function;
import org.apache.commons.collections4.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;

public class Verify
{
    public static void greaterThan(Integer expected, Integer found, String message)
    {
        greaterThan(expected, found, message, false);
    }

    public static void greaterThan(Integer expected, Integer found, String message, boolean allowSoftFail)
    {
        greaterThan(expected, found, message, allowSoftFail, null);
    }

    public static void greaterThan(Integer expected, Integer found, String message, boolean allowSoftFail, String knownIssue)
    {
        Log.story("Verify " + found + " is greater than " + expected);
        internalIsTrue(found != null && found > expected, message, allowSoftFail, knownIssue);
    }

    public static void isTrue(boolean condition, String message)
    {
        isTrue(condition, message, false);
    }

    public static void isTrue(boolean condition, String message, boolean allowSoftFail)
    {
        isTrue(condition, message, allowSoftFail, null);
    }

    public static void isTrue(boolean condition, String message, boolean allowSoftFail, String knownIssue)
    {
        Log.story("Verify is true: " + (condition ? "true" : "false"));
        internalIsTrue(condition, message, allowSoftFail, knownIssue);
    }

    public static void fail(String message)
    {
        //noinspection ConstantConditions
        Assert.fail(message);
    }


    public static void isFound(By locator, String message)
    {
        isFound(locator, message, false);
    }

    public static void isFound(By locator, String message, boolean allowSoftFail)
    {
        isFound(Find.options().locator(locator), message, allowSoftFail);
    }

    public static void isFound(FindOptions options, String message)
    {
        isFound(options, message, false);
    }

    public static void isFound(FindOptions options, String message, boolean allowSoftFail)
    {
        isFound(options, message, allowSoftFail, null);
    }

    public static void isFound(FindOptions options, String message, boolean allowSoftFail, String knownIssue)
    {
        Log.story("Verify element is found: " + options.locator());
        // Do not fail in the lookup, fail if not found within the Verify class to get the correct message
        options.failOnNotFound(false);
        internalIsTrue(Find.element(options) != null, message + " using locator: " + options.locator(), allowSoftFail, knownIssue);
    }

    public static void isNotFound(By locator, String message)
    {
        isNotFound(Find.options().locator(locator), message);
    }

    public static void isNotFound(FindOptions options, String message)
    {
        isNotFound(options, message, options.allowSoftFail());
    }

    public static void isNotFound(FindOptions options, String message, boolean allowSoftFail)
    {
        isNotFound(options, message, allowSoftFail, null);
    }

    public static void isNotFound(FindOptions options, String message, boolean allowSoftFail, String knownIssue)
    {
        Log.story("Verify element is not found: " + options.locator());

        Function.sleep(Duration.ofSeconds(1), "Allow for initial element to be present before not found is applied");

        int originalTimeout = options.timeoutMS();
        int originalMaxTimeout = options.maxWaitMS();

        Date now = new Date();
        Date standardEnd = DateHelper.addMillis(now, originalTimeout);
        Date maxEnd = DateHelper.addMillis(now, originalMaxTimeout);

        FindOptions tempOptions = options.clone();
        tempOptions.failOnNotFound(false)// Fail only withing the Verify class
                .visible(true)  // Needs the visible check to make sure it is not there
                .timeoutMS(100) // Check and fail quickly otherwise it sits for ages when not found
                .maxWaitMS(100) // MaxWait is handled below
                .findLogging(false); // Suppress find logging every 100ms

        boolean inExtraTime = false;
        boolean stillFound = true;
        while (now.before(maxEnd) && stillFound) {
            stillFound = !CollectionUtils.isEmpty(Find.elements(tempOptions));
            inExtraTime = now.after(standardEnd);
            now = new Date();
        }

        if (inExtraTime && !stillFound) {
            Log.softFail("Element took longer to disappear that standard timeout (" + originalTimeout + "ms) but cleared before max (" + originalMaxTimeout + "ms)", Function.getStackTrace());
        }

        Log.find(options, stillFound ? 1 : 0);

        internalIsTrue(!stillFound, message, allowSoftFail, knownIssue);
    }

    public static void containsText(By locator, String expectedText, String message)
    {
        containsText(Find.options().locator(locator), expectedText, message);
    }

    public static void containsText(FindOptions options, String expectedText, String message)
    {
        containsText(Find.element(options), expectedText, message);
    }

    public static void containsText(WebElement element, String expectedText, String message)
    {
        Log.story("Verify element contains text: " + expectedText);
        isTrue(Find.getText(element).contains(expectedText), message);
    }

    public static void hasCss(By locator, String attribute, String expectedValue, String message)
    {
        hasCss(locator, attribute, expectedValue, message, false);
    }

    public static void hasCss(By locator, String attribute, String expectedValue, String message, boolean allowSoftFail)
    {
        hasCss(Find.options().locator(locator), attribute, expectedValue, message, allowSoftFail);
    }

    public static void hasCss(FindOptions options, String attribute, String expectedValue, String message, boolean allowSoftFail)
    {
        Log.story("Verify element has css style: " + attribute + " = " + expectedValue);
        isTrue(Find.hasCssValue(options, attribute, expectedValue), message, allowSoftFail);
    }

    public static void hasNotCss(FindOptions options, String attribute, String expectedValue, String message)
    {
        hasNotCss(options, attribute, expectedValue, message, false);
    }

    public static void hasNotCss(FindOptions options, String attribute, String expectedValue, String message, boolean allowSoftFail)
    {
        hasNotCss(options, attribute, expectedValue, message, allowSoftFail, null);
    }

    public static void hasNotCss(FindOptions options, String attribute, String expectedValue, String message, boolean allowSoftFail, String knownIssue)
    {
        Log.story("Verify element has css style: " + attribute + " = " + expectedValue);
        internalIsTrue(!Objects.equals(Find.element(options).getCssValue(attribute), expectedValue), message, allowSoftFail, knownIssue);
    }

    public static void hasAttribute(By locator, String attribute, String value)
    {
        hasAttribute(Find.options().locator(locator), attribute, value);
    }

    public static void hasAttribute(FindOptions findOptions, String attribute, String value)
    {
        Log.story("Wait until the element has an attribute. Locator: " + findOptions.locator() + " Looking for attribute: " + attribute + " = " + value);
        internalIsTrue(Find.attributeHasValue(findOptions, attribute, value), "Element attribute not found", findOptions.allowSoftFail(), null);
    }

    public static void isWithin(int expected, int found, int tolerance, String message)
    {
        isWithin(expected, found, tolerance, message, false);
    }

    public static void isWithin(int expected, int found, int tolerance, String message, boolean allowSoftFail)
    {
        int diff = Math.abs(expected - found);
        boolean passing = diff < tolerance;
        Log.debug("Verify within. Expected: " + expected + " Found: " + found + " Within tolerance: " + tolerance + " Result: " + passing);
        isTrue(passing, message, allowSoftFail);
    }

    public static void isEqual(double expected, double found, String message)
    {
        isEqual(expected, found, message, false, null);
    }

    public static void isEqual(double expected, double found, String message, boolean allowSoftFail, String knownIssue)
    {
        isEqual(String.valueOf(expected), String.valueOf(found), message, allowSoftFail, knownIssue, true);
    }

    public static void isEqual(int expected, int found, String message)
    {
        isEqual(expected, found, message, false, null);
    }

    public static void isEqual(String expected, String found, String message)
    {
        isEqual(expected, found, message, false, null, true);
    }

    public static void isEqual(int expected, int found, String message, boolean allowSoftFail, String knownIssue)
    {
        isEqual(String.valueOf(expected), String.valueOf(found), message, allowSoftFail, knownIssue, true);
    }

    public static void isEqual(String expected, String found, String message, boolean allowSoftFail, String knownIssue, boolean logStory)
    {
        if (logStory) {
            Log.story("Verify [" + expected + "] is equal to [" + found + "]");
        }

        if (allowSoftFail) {
            if (!found.equals(expected)) {
                Log.softFail(message, Function.getStackTrace(), knownIssue);
            }
        } else {
            Assert.assertEquals(found, expected, message);
        }
    }

    public static void contains(String haystack, String needle)
    {
        contains(haystack, needle, "Expected: " + needle + " Found: " + haystack);
    }

    public static void contains(String haystack, String needle, String message)
    {
        Log.story("Verify text: " + haystack + " contains: " + needle);
        if (!haystack.contains(needle)) {
            fail(message);
        }
    }

    public static void isFileDownloaded(String fileName, String extension)
    {
        isFileDownloaded(fileName, extension, Duration.ofMillis(Store.getSettings().getDefaultTimeoutMS()));
    }

    public static void isFileDownloaded(String fileName, String extension, Duration timeout)
    {
        Log.story("Verify file downloaded: " + fileName + "." + extension);
        Verify.isTrue(FileHelper.isFileDownloaded(fileName, extension, timeout), "[" + fileName + "] file is not downloaded");
    }

    public static boolean correctAlertFound(String expectedText)
    {
        return correctAlertFound(expectedText, false, null);
    }

    public static boolean correctAlertFound(String expectedText, boolean allowSoftFail, String knownIssue)
    {
        String alertMessage = null;
        if (Find.alertShown()) {
            alertMessage = Store.getDriver().switchTo().alert().getText();
        }

        Verify.isEqual(expectedText, alertMessage, "Alert did not contain the correct text", allowSoftFail, knownIssue, true);
        return alertMessage != null && alertMessage.equals(expectedText);
    }


    private static void internalIsTrue(boolean condition, String message, boolean allowSoftFail, String knownIssue)
    {
        if (allowSoftFail) {
            if (!condition) {
                Log.softFail(message, Function.getStackTrace(), knownIssue);
            }
        } else {
            Assert.assertTrue(condition, message);
        }
    }

    public static void verifyPrint(By printButton)
    {
        // First override the browser window.print() function to return an alert instead
        String alertDialogText = "Print clicked";
        Store.getDriver().executeScript("window.print = function(){alert('" + alertDialogText + "')};");
        Function.switchDefaultFrame();

        Find.click(printButton);
        Verify.correctAlertFound(alertDialogText);
        Find.dismissAlert();
    }
}
