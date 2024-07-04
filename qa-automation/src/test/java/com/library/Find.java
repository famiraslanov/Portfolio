package com.library;

import com.library.classes.FindByText;
import com.library.enums.Comparator;
import com.library.enums.FindType;
import com.library.enums.QuickWatchAction;
import com.library.helpers.Function;
import com.library.helpers.PasswordHelper;
import com.library.listeners.QASuiteListener;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Find
{
    private static final By allSpinners = By.cssSelector("[data-testid='spinner'], [class*='_PlaceholderLine-'] :not([data-testid=\"upgrade-cta\"]), [class*='Spinner']");

    public static FindOptions options()
    {
        if (Store.getSettings().isVerboseFindLogging()) {
            Log.debug("New FindOptions started");
        }
        return new FindOptions();
    }

    public static WebElement element(By locator)
    {
        // If finding a single element by locator then use default options
        return element(Find.options().locator(locator));
    }

    public static WebElement element(FindOptions findOptions)
    {
        // First get a list of all elements matching the options
        List<WebElement> allElementsFound = elements(findOptions);
        if (!CollectionUtils.isEmpty(allElementsFound) && (allElementsFound.size() == 1 || findOptions.returnFirst() || findOptions.returnLast())) {
            // If either a single element (as expected) or a returnFirst(true)
            int returnIndex = findOptions.returnLast() ? (allElementsFound.size()-1): 0;
            scrollToElement(allElementsFound.get(returnIndex), findOptions);
            return allElementsFound.get(returnIndex);
        } else {
            if (findOptions.failOnNotFound()) {
                String message = "Unable to find single element";
                if (!CollectionUtils.isEmpty(allElementsFound)) {
                    message += ". " + allElementsFound.size() + " elements found";
                }
                message += " with options: " + findOptions.locator();

                if (findOptions.allowSoftFail()) {
                    Log.softFail(message, Function.getStackTrace());
                } else {
                    Verify.fail(message);
                }
            }
            // else the coder has said it is ok to not find the element so handling should be done elsewhere
        }
        return null;
    }

    public static List<WebElement> elements(By locator)
    {
        // If finding a list of elements by locator then use default options
        return elements(Find.options().locator(locator));
    }

    public static List<WebElement> elements(FindOptions options)
    {
        if (options.findLogging()) {
            Log.debug("Look for elements: " + options.locator() + (options.findByText() != null ? " and " + options.findByText().getComparator() + " text: " + options.findByText().getTextToLookFor() : ""));
        }

        if (options.debugThisCall()) {
            System.out.println("Add a breakpoint to this line and run in debug");
            System.out.println(Function.asJson(options));
        }

        if (options.checkForNoSpinner()) {
            Verify.isNotFound(Find.options().locator(allSpinners).timeoutMS(options.timeoutMS()).maxWaitMS(options.maxWaitMS()).failOnNotFound(false), "Spinner still seen (in Find)");
        }

        List<WebElement> returnList;

        if (QASuiteListener.isAppleM2 && options.locator().toString().contains(":has")) {
            Log.debug("Using M2 :has hack");
            Function.slowEnvironmentWait();
            try {
                String cleanSelector = options.locator().toString().replace("By.cssSelector: ", "").replace("\"", "'");
                String script = "return $(\"" + cleanSelector + "\").get();";
                Store.getDriver().executeScript(Function.readResourcesFile("jQuerify.js"));
                returnList = (List<WebElement>) Store.getDriver().executeScript(script);
            } catch (IOException e) {
                Log.exception(e);
                throw new RuntimeException(e);
            }
        } else {
            // Get the list of matching elements
            returnList = waitForList(options, options.timeoutMS());
        }

        if (CollectionUtils.isEmpty(returnList)) {
            // Nothing found within timeoutMS() so, if the maxWait is greater than timeoutMS(), try again
            int diff = options.maxWaitMS() - options.timeoutMS();
            if (diff > 0) {
                returnList = waitForList(options, diff);
                if (!CollectionUtils.isEmpty(returnList)) {
                    // Found some on the extended time... so need to log that
                    Log.softFail("Elements found between timeout and MaxWat time", Function.getStackTrace());
                }
            }
        }

        if (options.findLogging()) {
            // Log the options and number of elements found
            Log.find(options, CollectionUtils.isEmpty(returnList) ? 0 : returnList.size());
        }

        if (options.failOnNotFound() && !options.allowSoftFail()) {
            // Hard fail here
            Verify.isTrue(!CollectionUtils.isEmpty(returnList), listFailMessage(options));
        } else {
            if (CollectionUtils.isEmpty(returnList)) {
                // Either failOnNotFound(false) or allowSoftFail(true) log what has happened
                Log.debug(listFailMessage(options));
            }
        }

        // Could be NULL at this point but only if failOnNotFound(false) or allowSoftFail(true)
        return returnList;
    }

    private static List<WebElement> waitForList(FindOptions options, int timeoutMS)
    {
        List<WebElement> fullList = null;
        List<WebElement> returnList = null;
        LocalDateTime timeoutEnd = LocalDateTime.now().plusNanos(TimeUnit.MILLISECONDS.toNanos(timeoutMS));

        // Look for the first list of elements. If some elements are found quickly then the list could change, so poll if no items returned before timeout
        while (CollectionUtils.isEmpty(returnList) && LocalDateTime.now().isBefore(timeoutEnd)) {
            if (options.applyQuickWatch()) {
                // Clear any known elements that might pop up at random times. Running this here so that it is as close to the findElements as possible
                processQuickWatch(true);
            }

            fullList = Find.findList(options, Duration.between(LocalDateTime.now(), timeoutEnd));
            // Process the found list to filter for text lookups (if needed)
            returnList = filterByText(fullList, options.findByText());
            Function.sleep(Duration.ofMillis(100), "Small wait between checks");
        }

        if (CollectionUtils.isEmpty(returnList) && !CollectionUtils.isEmpty(fullList)) {
            // A list has been found but the text lookup returned empty so log what was found
            Log.debug("Found the list but no matching text");
            Log.object("FullList", fullList.stream()
                    .map(we -> {
                        try {
                            return we.getText().trim();
                        } catch (Exception e) {
                            return "Element gone away";
                        }
                    })
                    .toList()
            );
        }

        return returnList;
    }

    private static List<WebElement> findList(FindOptions options, Duration duration)
    {
        List<WebElement> returnList = null;
        try {
            switch (FindType.fromOptions(options)) {
                case nestedChildrenParentOptions:
                    returnList = getWait(duration).until(wd -> Find.element(options.parentOption()).findElements(options.locator()));
                    if (options.visible()) {
                        returnList = returnList.stream().filter(WebElement::isDisplayed).toList();
                    }
                    break;
                case nestedChildrenParentElement:
                    returnList = getWait(duration).until(wd -> options.parent().findElements(options.locator()));
                    if (options.visible()) {
                        returnList = returnList.stream().filter(WebElement::isDisplayed).toList();
                    }
                    break;
                case nestedChildrenParentLocator:
                    if (options.visible()) {
                        returnList = getWait(duration).until(wd -> ExpectedConditions.visibilityOfNestedElementsLocatedBy(options.parentLocator(), options.locator()).apply(Store.getDriver()));
                    } else {
                        returnList = getWait(duration).until(wd -> ExpectedConditions.presenceOfNestedElementsLocatedBy(options.parentLocator(), options.locator()).apply(Store.getDriver()));
                    }
                    break;
                case elements:
                default:
                    if (options.visible()) {
                        returnList = getWait(duration).until(wd -> ExpectedConditions.visibilityOfAllElementsLocatedBy(options.locator()).apply(Store.getDriver()));
                    } else {
                        returnList = getWait(duration).until(wd -> ExpectedConditions.presenceOfAllElementsLocatedBy(options.locator()).apply(Store.getDriver()));
                    }
            }

            if (options.clickable()) {
                returnList = returnList.stream().filter(we -> we.isDisplayed() && we.isEnabled()).toList();
            }

            if (options.enabled()) {
                returnList = returnList.stream().filter(WebElement::isEnabled).toList();
            }
        } catch (TimeoutException timeoutException) {
            // Ok to do nothing
        } catch (StaleElementReferenceException staleElementReferenceException) {
            // StaleRefEx are caused by the page changing. Allow one retry to see if that helps
            if (!options.staleRefRetry()) {
                options.staleRefRetry(true);
                returnList = findList(options, duration);
            }
        }
        return returnList;
    }

    private static String listFailMessage(FindOptions findOptions)
    {
        String failMessage = "Elements not found, Locator: " + findOptions.locator();
        if (findOptions.findByText() != null) {
            failMessage += " Looking for text: " + findOptions.findByText().getComparator() + " " + findOptions.findByText().getTextToLookFor();
        }
        return failMessage;
    }

    private static List<WebElement> filterByText(List<WebElement> fullList, FindByText findByText)
    {
        if (findByText == null || CollectionUtils.isEmpty(fullList)) {
            return fullList;
        }

        List<WebElement> textFoundElements = null;
        try {
            switch (findByText.getComparator()) {
                case equals ->
                        textFoundElements = fullList.stream().filter(el -> el.getText().trim().toLowerCase().equals(findByText.getTextToLookFor().trim().toLowerCase())).toList();
                case contains ->
                        textFoundElements = fullList.stream().filter(el -> el.getText().trim().toLowerCase().contains(findByText.getTextToLookFor().trim().toLowerCase())).toList();
                case startsWith ->
                        textFoundElements = fullList.stream().filter(el -> el.getText().trim().startsWith(findByText.getTextToLookFor().trim())).toList();
                case endsWith ->
                        textFoundElements = fullList.stream().filter(el -> el.getText().trim().endsWith(findByText.getTextToLookFor().trim())).toList();
                case regex, regexCaseInsensitive -> textFoundElements = fullList.stream()
                        .filter(el -> Pattern.compile(findByText.getTextToLookFor(), (findByText.getComparator() == Comparator.regexCaseInsensitive ? Pattern.CASE_INSENSITIVE : Pattern.LITERAL)).matcher(el.getText()).find())
                        .collect(Collectors.toList());

                default -> Verify.fail("Unhandled FindByText comparator");
            }
        } catch (StaleElementReferenceException e) {
            // Ok to do nothing as this will be picked up in a retry (as it needs to re-fetch the list anyway)
        }

        return textFoundElements;
    }

    private static WebDriverWait getWait(Duration duration)
    {
        // Using the duration passed in and polling every {xxx}Ms
        return new WebDriverWait(Store.getDriver(), duration, Duration.ofMillis(100));
    }

    public static WebElement insert(By locator, CharSequence... text)
    {
        return insert(Find.options().locator(locator), text);
    }

    public static WebElement insert(FindOptions findOptions, CharSequence... text)
    {
        String textAsString = convertCharToKeys(text);
        WebElement el = element(findOptions);
        if (findOptions.clearFirst()) {
            Log.debug("Clear any existing text");
            el.clear();
        }

        if (findOptions.decryptText()) {
            Log.story("Enter the decrypted version of text: " + textAsString);
            el.sendKeys(PasswordHelper.decryptPassword(QASuiteListener.settings.getPasswordKey(), textAsString));
        } else {
            Log.story("Enter the text: " + textAsString);
            el.sendKeys(text);
        }

        return el;
    }

    private static String convertCharToKeys(CharSequence... text)
    {
        String arrayAsText = Arrays.toString(text);
        List<String> keyNames = new ArrayList<>();
        for (Keys name : Keys.values()) {
            if (arrayAsText.contains(name.toString())) {
                keyNames.add(name.name());
            }
        }
        if (!keyNames.isEmpty()) {
            return "Keys: " + StringUtils.join(keyNames);
        }

        return arrayAsText;
    }

    public static void click(By locator)
    {
        click(Find.options().locator(locator));
    }

    public static void click(FindOptions findOptions)
    {
        click(element(findOptions));
    }

    public static void click(WebElement element)
    {
        Log.story("Click the element");
        element.click();
    }

    public static void openLinkInNewTab(String linkUrl)
    {
        Log.story("Open link in new tab");
        JavascriptExecutor jsExecutor = (JavascriptExecutor) Store.getDriver();
        jsExecutor.executeScript("window.open(arguments[0],'_blank');", linkUrl);
    }

    public static WebElement scrollToElement(WebElement element)
    {
        return scrollToElement(element, Find.options().scrollTo(true));
    }

    public static WebElement scrollToElement(WebElement element, FindOptions options)
    {
        if (options.scrollTo()) {
            try {
                if (options.scrollOnElement()) {
                    // scrollIntoView scrolls on an element so doesn't allow for offsets
                    Store.getDriver().executeScript("arguments[0].scrollIntoView(true);", element);
                } else {
                    String scrollElementIntoMiddle =
                            "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                                    + "var elementTop = arguments[0].getBoundingClientRect().top;"
                                    + "var y=(elementTop-(viewPortHeight/2));"
                                    + "window.scrollBy({left: 0,top: y, behavior: 'instant'});";

                    Store.getDriver().executeScript(scrollElementIntoMiddle, element);
                }
            } catch (Exception e) {
                Log.debug("Failure to scrollTo(): " + e.getMessage());
                Log.exception(e);
            }
        }
        return element;
    }

    private static void processQuickWatch(boolean firstTime)
    {
        for (QuickWatchObject quickWatchObject : Store.getQuickWatchList()) {
            if (quickWatchObject.getSmallDelay() != null) {
                Function.sleep(quickWatchObject.getSmallDelay(), "Allow quickwatch items to show if they are going to");
            }

            try {
                WebElement element = null;
                if (quickWatchObject.getHolderLocator() != null) {
                    WebElement parent = Store.getDriver().findElement(quickWatchObject.getHolderLocator());
                    if (quickWatchObject.getAction() == QuickWatchAction.remove) {
                        element = parent;
                    } else {
                        element = parent.findElement(quickWatchObject.getActionElementLocator());
                    }
                } else {
                    element = Store.getDriver().findElement(quickWatchObject.getActionElementLocator());
                }

                if (element != null) {
                    if (quickWatchObject.getAction() == QuickWatchAction.click) {
                        if (element.isEnabled()) {
                            element.click();
                        } else {
                            Log.debug("Quickwatch " + quickWatchObject.getUniqueName() + " found but was not clickable");
                        }
                    } else {
                        Store.getDriver().executeScript("try{arguments[0].parentElement.removeChild(arguments[0]);}catch(err){};", element);
                        Function.sleep(Duration.ofMillis(200), "Allow time for the JS to remove the element");
                    }
                    Log.quickWatch(quickWatchObject, 1);
                } else {
                    if (Store.getSettings().isQuickWatchLogging()) {
                        Log.quickWatch(quickWatchObject, 0);
                    }
                }
            } catch (NoSuchElementException | ElementNotInteractableException | StaleElementReferenceException e) {
                if (Store.getSettings().isQuickWatchLogging()) {
                    Log.quickWatch(quickWatchObject, 0);
                }
                if (e instanceof StaleElementReferenceException && firstTime) {
                    // Retry all quickWatch if staleRef as the page changed but element might have been there
                    processQuickWatch(false);
                }
            } catch (Exception e) {
                Log.exception(e);
            }
        }
    }

    public static String getText(By locator)
    {
        return getText(Find.options().locator(locator));
    }

    public static String getText(FindOptions options)
    {
        return getText(element(options));
    }

    public static String getText(WebElement element)
    {
        Log.debug("Retrieve the element text");
        return element.getText();
    }

    public static List<String> getTexts(By locator)
    {
        return getTexts(elements(Find.options().locator(locator)));
    }

    public static List<String> getTexts(FindOptions options)
    {
        return getTexts(elements(options));
    }

    public static List<String> getTexts(List<WebElement> elements)
    {
        Log.debug("Retrieve the element text from a list of elements");
        if (CollectionUtils.isEmpty(elements)) {
            return null;
        }
        return elements.stream().map(we -> we.getText().trim()).toList();
    }

    /**
     * Please use sparingly as this method uses Actions which are notoriously flakey
     *
     * @param options Normal Find.options()
     */
    public static void hoverClick(FindOptions options)
    {
        Actions action = new Actions(Store.getDriver());
        WebElement element = element(options);
        action.moveToElement(element).perform();
        action.click(element).perform();
    }

    /**
     * Please use sparingly as this method uses Actions which are notoriously flakey
     *
     * @param options Normal Find.options()
     */
    public static void hover(FindOptions options)
    {
        Actions action = new Actions(Store.getDriver());
        WebElement element = element(options);
        action.moveToElement(element).perform();
    }

    public static void select(By locator, String text)
    {
        select(Find.options().locator(locator), text);
    }

    public static void select(FindOptions options, String text)
    {
        Select select = new Select(element(options));
        select.selectByVisibleText(text);
    }

    public static String getSelectValue(By locator)
    {
        return getSelectValue(Find.options().locator(locator));
    }

    public static String getSelectValue(FindOptions options)
    {
        return new Select(Find.element(options)).getFirstSelectedOption().getText();
    }

    public static String getAttribute(FindOptions options, String attributeName)
    {
        WebElement element = Find.element(options);
        return getAttribute(element, attributeName);
    }

    public static String getAttribute(WebElement element, String attributeName)
    {
        return element.getAttribute(attributeName);
    }

    public static List<String> getAttributes(FindOptions options, String attributeName)
    {
        Log.debug("Fetch attributes: " + attributeName);
        List<WebElement> list = Find.elements(options);

        if (list != null) {
            return list.stream().map(we -> Find.getAttribute(we, attributeName)).toList();
        }

        if (options.failOnNotFound()) {
            Verify.fail("No elements found");
        }

        return null;
    }

    public static boolean hasCssValue(FindOptions findOptions, String cssAttribute, String expectedValue)
    {
        boolean found;
        LocalDateTime end = LocalDateTime.now().plusNanos(TimeUnit.MILLISECONDS.toNanos(findOptions.timeoutMS()));
        // Need to loop to poll as the initial Find.element will only wait for the element, not the css attribute
        while (LocalDateTime.now().isBefore(end)) {
            try {
                found = getWait(Duration.ofMillis(500)).until(arg0 -> Find.getCSSValue(findOptions.failOnNotFound(false), cssAttribute).contains(expectedValue));
                if (found) {
                    return true;
                }
            } catch (Exception e) {
                // Do nothing
            }
        }

        return false;
    }

    public static String getCSSValue(FindOptions options, String propertyName)
    {
        return getCSSValue(element(options), propertyName);
    }

    public static String getCSSValue(WebElement element, String propertyName)
    {
        return element.getCssValue(propertyName);
    }

    public static boolean attributeHasValue(FindOptions findOptions, String attribute, String value)
    {
        if (FindType.fromOptions(findOptions).isNested) {
            Verify.fail("TEST ISSUE: This method does not support NestedElements");
        }

        boolean found = false;
        try {
            found = getWait(Duration.ofMillis(findOptions.timeoutMS())).until(ExpectedConditions.attributeToBe(findOptions.locator(), attribute, value));
        } catch (TimeoutException e) {
            // Ok to do nothing
        }

        if (!found) {
            int additionalTime = findOptions.maxWaitMS() - findOptions.timeoutMS();
            try {
                found = getWait(Duration.ofMillis(additionalTime)).until(ExpectedConditions.attributeToBe(findOptions.locator(), attribute, value));
            } catch (TimeoutException e) {
                // Ok to do nothing
            }
            if (found) {
                Log.softFail("Attribute found during maxWait() time", Function.getStackTrace());
            }
        }

        if (!found) {
            WebElement element = Find.element(findOptions.failOnNotFound(false));
            if (element != null) {
                Log.debug("Expected attribute value not found but element found. Actual attribute value Found: " + element.getAttribute(attribute) + " Looking for: " + value);
            }
        }

        return found;
    }

    public static boolean alertShown()
    {
        return alertShown(Find.options());
    }

    public static boolean alertShown(FindOptions findOptions)
    {
        return getWait(Duration.ofMillis(findOptions.timeoutMS())).until(ExpectedConditions.alertIsPresent()) != null;
    }

    public static void dismissAlert()
    {
        Store.getDriver().switchTo().alert().dismiss();
        Store.getDriver().switchTo().defaultContent();
    }

    public static void dragAndDrop(WebElement sourceLocator, WebElement destinationLocator)
    {
        Actions action = new Actions(Store.getDriver());
        action.dragAndDrop(sourceLocator,destinationLocator).build().perform();
    }
}
