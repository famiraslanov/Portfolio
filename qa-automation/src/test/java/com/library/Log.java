package com.library;

import com.tests.classes.*;
import com.library.classes.NotRunException;
import com.library.enums.Environment;
import com.library.helpers.ApiHelper;
import com.library.helpers.Function;
import com.library.helpers.S3Helper;
import com.library.listeners.QASuiteListener;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;


public class Log
{
    public static QAToolsResponseObject startTest(TestStartResultObject testResultObject)
    {
        return logToApi(testResultObject, false);
    }

    public static QAToolsResponseObject sessionId()
    {
        return logToApi(TestSessionIdObject.builder().build(), false);
    }

    public static QAToolsResponseObject stopTest(TestStopResultObject testResultObject)
    {
        return logToApi(testResultObject, false);
    }

    public static void email(String toAddress, String uniqueContent)
    {
        Map<String, String> map = new HashMap<>();
        map.put("toAddress", toAddress);
        map.put("uniqueContent", uniqueContent);
        internalLog(Function.asJson(map), "email", false);
    }

    public static void info(String text)
    {
        info(text, false);
    }

    public static void info(String text, boolean suiteLevelOnly)
    {
        internalLog(text, String.valueOf(Level.INFO), suiteLevelOnly);
    }

    public static void notRunProd(String reason)
    {
        if (QASuiteListener.settings.getEnvironment() == Environment.prod) {
            Log.notRun(reason);
        }
    }

    public static void notRun(String reason)
    {
        internalLog("NotRunException", "notRunException", false);
        throw new NotRunException(reason);
    }

    public static void knownIssue(String text, List<StackTraceElement> stack)
    {
        knownIssue(text, stack, false);
    }

    public static void knownIssue(String text, List<StackTraceElement> stack, boolean softFail)
    {
        text = "Known Issue: " + text;
        internalLog(text, "knownIssue", true, false, null, stack);

        if (softFail) {
            softFail(text, stack);
        } else {
            Verify.fail(text);
        }
    }


    public static void story(String text)
    {
        story(text, false);
    }

    public static void story(String text, boolean suiteLevelOnly)
    {
        internalLog(text, String.valueOf(Level.INFO), suiteLevelOnly);
    }

    public static void find(FindOptions findOptions, Integer found)
    {
        find(findOptions, found, false);
    }

    public static void find(FindOptions findOptions, Integer found, boolean suiteLevelOnly)
    {
        internalLog(Function.asJson(findOptions), "find", true, suiteLevelOnly, found);
    }

    public static void quickWatch(QuickWatchObject quickWatchObject, Integer found)
    {
        quickWatch(quickWatchObject, found, false);
    }

    public static void quickWatch(QuickWatchObject quickWatchObject, Integer found, boolean suiteLevelOnly)
    {
        internalLog(Function.asJson(quickWatchObject), "quickWatch", true, suiteLevelOnly, found);
    }

    public static void debug(String text)
    {
        debug(text, false);
    }

    public static void debug(String text, boolean suiteLevelOnly)
    {
        internalLog(text, String.valueOf(Level.DEBUG), suiteLevelOnly);
    }

    public static boolean featureFlag(String flagName, FindOptions findOptions)
    {
        WebElement flagIdentifier = Find.element(findOptions.timeoutMS(2000).failOnNotFound(false));
        internalLog(flagName + ": " + (flagIdentifier == null ? "Not seen" : "Seen"), "featureFlag", false);
        return flagIdentifier != null;
    }

    public static void notRunIfFlagIfDisabled(String flagName, FindOptions findOptions)
    {
        if (!featureFlag(flagName, findOptions)) {
            Log.notRun(flagName + " is not enabled");
        }
    }

    public static void softFail(String message, List<StackTraceElement> stack)
    {
        softFail(message, stack, null);
    }

    public static void softFail(String message, List<StackTraceElement> stack, String knownIssue)
    {
        Store.addSoftFail(knownIssue);
        internalLog(message, "softFail", true, false, null, stack);
    }

    public static void warning(String text)
    {
        warning(text, false);
    }

    public static void warning(String text, boolean suiteLevelOnly)
    {
        internalLog(text, String.valueOf(Level.WARN), suiteLevelOnly);
    }

    public static void error(String text)
    {
        error(text, false);
    }

    public static void error(String text, boolean suiteLevelOnly)
    {
        internalLog(text, String.valueOf(Level.ERROR), suiteLevelOnly);
    }

    public static void file(String label, File file)
    {
        file(label, file, true);
    }

    public static void file(String label, File file, boolean deleteTmp)
    {
        String result = S3Helper.upload(file);
        if (result != null) {
            internalLog(result, "file", true, false, null, label, null);
            if (deleteTmp && file.delete()) {
                Log.debug("Temp file deleted: " + file.getName());
            }
        } else {
            internalLog("Unable to upload file", String.valueOf(Level.ERROR), false);
        }
    }

    public static void object(String label, Object object)
    {
        object(label, object, false);
    }

    public static void object(String label, Object object, boolean suiteLevelOnly)
    {
        internalLog(Function.asJson(object), "object", true, suiteLevelOnly, null, label, null);
    }

    public static void performance(ArrayList<PerformanceResultObject> performanceResultObjects)
    {
        internalLog(Function.asJson(performanceResultObjects), "performance", true, false, null, "performance", null);
    }

    public static void exception(Exception e)
    {
        exception(e, false);
    }

    public static void exception(Exception e, boolean suiteLevelOnly)
    {
        internalLog(e.toString() + ": " + e.getMessage(), String.valueOf(Level.ERROR), true, suiteLevelOnly, null, Arrays.stream(e.getStackTrace()).toList());
    }

    public static void delayedAction(String runActionMethod, LocalDateTime runAfterDate, Object runSetupData)
    {
        logToApi(new DelayedActionLogObject(
                        runActionMethod,
                        runAfterDate,
                        runSetupData),
                true);
    }

    public static void nonTrackLog(String text)
    {
        internalLog(text, String.valueOf(Level.DEBUG), false, false);
    }

    private static void internalLog(String text, String level, boolean suiteLevelOnly)
    {
        internalLog(text, level, true, suiteLevelOnly);
    }

    private static void internalLog(String text, String level, boolean apiLog, boolean suiteLevelOnly)
    {
        internalLog(text, level, apiLog, suiteLevelOnly, null);
    }

    private static void internalLog(String text, String level, boolean apiLog, boolean suiteLevelOnly, Integer found)
    {
        internalLog(text, level, apiLog, suiteLevelOnly, found, null, null);
    }

    private static void internalLog(String text, String level, boolean apiLog, boolean suiteLevelOnly, Integer found, List<StackTraceElement> stackTrace)
    {
        internalLog(text, level, apiLog, suiteLevelOnly, found, null, stackTrace);
    }

    private static void internalLog(String text, String level, boolean apiLog, boolean suiteLevelOnly, Integer found, String label, List<StackTraceElement> stackTrace)
    {
        Logger logger = LoggerFactory.getLogger(Log.class);
        logger.atLevel(convertLoggerLevel(level)).log(text);

        if (apiLog) {
            ResultLogObject resultLogObject = new ResultLogObject();
            resultLogObject.setLogLevel(level);
            resultLogObject.setContext(text);
            if (stackTrace == null) {
                resultLogObject.setStack(Function.getStackTrace());
            } else {
                resultLogObject.setStack(stackTrace);
            }

            resultLogObject.setFound(found);
            resultLogObject.setLabel(label);

            if (!suiteLevelOnly) {
                resultLogObject.setResultId(Store.getTestId());
                resultLogObject.setDisplayIndex(Store.getLogIndex());
            }

            logToApi(resultLogObject, QASuiteListener.settings.isUseAsyncLogging() && !suiteLevelOnly);
        }
    }

    private static Level convertLoggerLevel(String level)
    {
        if (level.equals("object")
                || level.equals("performance")
                || level.equals("find")
                || level.equals("quickWatch")
                || level.equals("file")
                || level.equals("email")
                || level.equals("knownIssue")
                || level.equals("notRunException")
                || level.equals("featureFlag")
        ) {
            return Level.DEBUG;
        }

        if (level.equals("softFail")) {
            return Level.ERROR;
        }

        return Level.valueOf(level);
    }

    private static QAToolsResponseObject logToApi(Object logObject, boolean async)
    {
        if (!QASuiteListener.settings.isUseDBLogging()) {
            return null;
        }

        QAToolsResponseObject QAToolsResponseObject = null;

        if (async) {
            QASuiteListener.asyncOperations.submitRunnable(() -> {
                ApiHelper.log(logObject);
                return null;
            });
        } else {
            QAToolsResponseObject = ApiHelper.log(logObject);
        }

        Store.addLogIndex();
        Log.writeAsyncQueue();
        return QAToolsResponseObject;
    }

    private static void writeAsyncQueue()
    {
        if (QASuiteListener.settings.isLogAsyncResultsQueueSize()) {
            System.out.println(QASuiteListener.asyncOperations.fetchQueueSize() + " on runId: " + QASuiteListener.runId);
        }
    }
}
