package com.library;

import com.tests.enums.platform.UserLogin;
import com.library.enums.DriverType;
import com.library.enums.Environment;
import com.library.listeners.QASuiteListener;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.List;

public class Store
{
    public static ThreadLocal<Settings> settings = new ThreadLocal<>();
    public static ThreadLocal<String> suiteName = new ThreadLocal<>();
    private static ThreadLocal<Integer> testId = new ThreadLocal<>();
    private static ThreadLocal<Integer> logIndex = new ThreadLocal<>();
    private static ThreadLocal<String> methodName = new ThreadLocal<>();
    private static ThreadLocal<String> fullClassName = new ThreadLocal<>();
    private static ThreadLocal<Environment> environment = new ThreadLocal<>();
    private static ThreadLocal<List<QuickWatchObject>> quickWatchList = new ThreadLocal<>();

    private static final ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<String> sessionId = new ThreadLocal<>();

    private static final ThreadLocal<Integer> softFailCount = new ThreadLocal<>();
    private static final ThreadLocal<List<String>> knownIssueStore = new ThreadLocal<>();
    private static final ThreadLocal<UserLogin> userLogin = new ThreadLocal<>();

    public static void init(String suite, DriverType driverType)
    {
        Settings iSettings = new Settings();
        iSettings.setDriveType(driverType);
        settings.set(iSettings);
        suiteName.set(suite);
        environment.set(iSettings.getEnvironment());
        driver.set(null);
        logIndex.set(0);
        quickWatchList.set(new ArrayList<>());
        softFailCount.set(0);
        knownIssueStore.set(new ArrayList<>());
        sessionId.set(null);
        userLogin.set(null);
    }

    public static RemoteWebDriver getDriver()
    {
        return driver.get();
    }

    public static void setDriver(RemoteWebDriver driver)
    {
        Store.driver.set(driver);
        Log.debug("Driver set as part of SuiteId: " + QASuiteListener.runId);
    }

    public static String getSuiteName()
    {
        return suiteName.get();
    }

    public static String getMethodName()
    {
        return methodName.get();
    }

    public static void setMethodName(ITestResult test)
    {
        String fullMethodName = test.getMethod().getMethodName();
        if (test.getMethod().getDataProviderMethod() != null) {
            fullMethodName += ": " + test.getParameters()[0].toString();
        }
        methodName.set(fullMethodName);
    }

    public static String getFullClassName()
    {
        return fullClassName.get();
    }

    public static void setFullClassName(String name)
    {
        fullClassName.set(name);
    }

    public static Environment getEnvironment()
    {
        return environment.get();
    }

    public static boolean isBrowserOpen()
    {
        try {
            driver.get().getWindowHandles();
            return true;
        } catch (Exception e) {
            // Do nothing
        }
        return false;
    }

    public static Settings getSettings()
    {
        return settings.get();
    }

    public static Integer getTestId()
    {
        return testId.get();
    }

    public static Integer getLogIndex()
    {
        return logIndex.get();
    }

    public static void setTestId(int newTestId)
    {
        testId.set(newTestId);
    }

    public static void addLogIndex()
    {
        int currentLogIndex = 0;
        try {
            currentLogIndex = getLogIndex();
        } catch (Exception e) {
            // Ok to do nothing
        }
        logIndex.set(currentLogIndex + 1);
    }

    public static void addQuickWatch(QuickWatchObject quickWatchObject)
    {
        if (quickWatchList.get().stream().filter(qw -> qw.getUniqueName().equals(quickWatchObject.getUniqueName())).findFirst().orElse(null) == null) {
            quickWatchList.get().add(quickWatchObject);
        }
    }

    public static List<QuickWatchObject> getQuickWatchList()
    {
        return quickWatchList.get();
    }

    public static Integer getSoftFailCount()
    {
        return softFailCount.get();
    }

    public static List<String> getKnownIssues()
    {
        return knownIssueStore.get();
    }

    public static void addSoftFail(String knownIssue)
    {
        softFailCount.set(softFailCount.get() + 1);

        if (knownIssue != null) {
            List<String> currentKnownIssues = knownIssueStore.get();
            if (!currentKnownIssues.contains(knownIssue)) {
                currentKnownIssues.add(knownIssue);
            }
            knownIssueStore.set(currentKnownIssues);
        }
    }

    public static void setSessionId(String sessId)
    {
        sessionId.set(sessId);
    }

    public static String getSessionId()
    {
        return sessionId.get();
    }

    public static UserLogin getUserLogin()
    {
        UserLogin forced = Store.getSettings().getForcedUserLoginEnum();
        if (forced != null) {
            return forced;
        }

        if (userLogin.get() == null) {
            userLogin.set(QASuiteListener.takeUser());
            Log.debug("Using user account: " + userLogin.get().emailAddress);
        }
        return userLogin.get();
    }

    public static void replaceUserLogin()
    {
        if (userLogin.get() != null) {
            Log.debug("Returning user account: " + userLogin.get().emailAddress);
            QASuiteListener.replaceUser(userLogin.get());
        }
    }
}
