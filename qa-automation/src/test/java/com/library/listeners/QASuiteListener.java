package com.library.listeners;

import com.library.Log;
import com.library.Settings;
import com.library.helpers.ApiHelper;
import com.library.helpers.AsyncOperations;
import com.library.helpers.Function;
import com.tests.classes.QAToolsResponseObject;
import com.tests.classes.RunObject;
import com.tests.enums.platform.UserLogin;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class QASuiteListener implements ISuiteListener
{
    public static Queue<UserLogin> allUsersLogin = new LinkedList<>(Arrays.stream(UserLogin.values()).filter(u -> u.forRotation).toList());
    public static Integer runId;
    public static Settings settings;
    public static boolean isAppleM2 = false;
    public static AsyncOperations<Void> asyncOperations = new AsyncOperations<>(20, "asyncLogSave");

    @Override
    public void onStart(ISuite iSuite)
    {
        settings = new Settings();
        runId = settings.getExistingSuiteId();
        isAppleM2 = Function.isAppleM2();

        if (settings.isUseDBLogging()) {
            if (runId == null) {
                QAToolsResponseObject QAToolsResponseObject = ApiHelper.log(new RunObject(settings));
                if (QAToolsResponseObject != null && QAToolsResponseObject.isOk()) {
                    runId = QAToolsResponseObject.getRunId();
                } else {
                    throw new RuntimeException("Unable to start a results logging run");
                }
            }
        } else {
            runId = -999;
        }

        Log.nonTrackLog("Starting suite with id: " + runId);
    }

    @Override
    public void onFinish(ISuite iSuite)
    {
        Log.debug("Finishing SuiteId: " + runId, true);
        QASuiteListener.asyncOperations.waitForComplete(Duration.ofSeconds(30));
        System.out.println("Suite run finished. Results can be viewed https://qa-tool.global-resources-prod.with.digital/automations?runId=" + runId);
    }

    public static UserLogin takeUser()
    {
        return allUsersLogin.remove();
    }

    public static void replaceUser(UserLogin userLogin)
    {
        allUsersLogin.add(userLogin);
    }
}
