package com.library;

import com.library.annotations.CoverageInfo;
import com.library.enums.DriverType;
import com.library.enums.QuickWatchAction;
import com.library.helpers.*;
import com.library.listeners.QASuiteListener;
import com.tests.classes.QAToolsResponseObject;
import com.tests.classes.TestStartResultObject;
import com.tests.classes.TestStopResultObject;
import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Listeners({QASuiteListener.class})
public class Main
{
    private String suiteName;
    public DriverType driverType;
    private List<QuickWatchObject> baseQuickWatch = new ArrayList<>();

    public Main(String suiteName)
    {
        this.suiteName = suiteName;

        addBaseQuickWatch(QuickWatchObject.builder()
                .uniqueName("RemoveSaveMessageBox")
                .actionElementLocator(By.cssSelector("[data-testid='notification-close-button'] span"))
                .smallDelay(Duration.ofMillis(50))
                .action(QuickWatchAction.click)
                .build());
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(ITestResult inProgress)
    {
        Store.init(suiteName, driverType);
        for (QuickWatchObject quickWatchObject : baseQuickWatch) {
            Store.addQuickWatch(quickWatchObject);
        }

        Store.setMethodName(inProgress);
        Store.setFullClassName(inProgress.getMethod().getQualifiedName());

        if (QASuiteListener.settings.isUseDBLogging()) {
            QAToolsResponseObject QAToolsResponseObject = Log.startTest(
                    TestStartResultObject.builder()
                            .method(Store.getMethodName())
                            .fullClass(Store.getFullClassName())
                            .coverageInfo(fetchCoverageInfo(inProgress))
                            .build()
            );

            if (QAToolsResponseObject.isOk()) {
                Store.setTestId(QAToolsResponseObject.getTestId());
            } else {
                throw new RuntimeException("Unable to log the result. Error: " + QAToolsResponseObject.getError());
            }
        } else {
            Store.setTestId(-998);
        }

        FileHelper.createDownloadFolder();
        QADriver.start();

        if (Store.getSessionId() != null) {
            Log.sessionId();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result)
    {
        Store.replaceUserLogin();
        processTheResult(result);
        QADriver.quit();
        SelenoidVideoHelper.uploadVideo(Store.getSettings().isVideoAvailable() && (Store.getSettings().isLogAllVideo() || !result.isSuccess()));
        FileHelper.clearFolder();
        failTestIfNeededForIDE(result);
    }

    private void processTheResult(ITestResult result)
    {
        Log.debug("Test ended");

        if (Store.getSoftFailCount() > 0) {
            if (Store.getKnownIssues().size() > 0) {
                Log.knownIssue(String.join(", ", Store.getKnownIssues()), Function.getStackTrace(), true);
            }
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(new SoftFailException("There were {" + Store.getSoftFailCount() + "} SoftFails during this test"));
        }

        boolean isSuccess = result.isSuccess();
        String resultString = isSuccess ? "pass" : "fail";

        if (Store.getDriver() != null) {
            if (Store.getSettings().getFullHeightScreenShots()) {
                ScreenshotHelper.take("final full height screenshot");
            }
            ScreenshotHelper.take("final screenshot");
            HtmlSourceHelper.save("final page source");
        }

        Throwable t = result.getThrowable();
        List<StackTraceElement> stack = null;

        String reason = "";

        if (t != null) {
            reason = t.toString();
            stack = Function.getStackTrace(t.getStackTrace());
        }

        if (QASuiteListener.settings.isUseDBLogging()) {
            QAToolsResponseObject QAToolsResponseObject = Log.stopTest(
                    TestStopResultObject.builder()
                            .result(resultString)
                            .stack(stack)
                            .reason(reason)
                            .url(Function.getCurrentUrl())
                            .build());
            System.out.println("Result logged: " + QAToolsResponseObject.isOk() + (!QAToolsResponseObject.isOk() ? " Response: " + QAToolsResponseObject.getResponse() : ""));
        } else {
            System.out.println("useDBLogging is not set to true");
        }
    }

    private void failTestIfNeededForIDE(ITestResult result)
    {
        if (Store.getSoftFailCount() > 0 && !result.isSuccess()) {
            throw new SoftFailException("There were {" + Store.getSoftFailCount() + "} SoftFails during this test");
        }
    }

    private String fetchCoverageInfo(ITestResult inProgress)
    {
        String info;
        try {
            info = inProgress.getMethod().getConstructorOrMethod().getMethod().getAnnotation(CoverageInfo.class).details();
        } catch (Exception e) {
            info = "Coverage Info not recorded in " + inProgress.getMethod().getMethodName();
        }
        return info;
    }

    public void addBaseQuickWatch(QuickWatchObject quickWatchObject)
    {
        baseQuickWatch.add(quickWatchObject);
    }
}
