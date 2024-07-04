package com.tests.ui.postrun;

import com.tests.classes.DelayedActionObject;
import com.tests.classes.RunSetupData;
import com.library.Log;
import com.library.Verify;
import com.library.helpers.ApiHelper;
import com.tests.pageobjects.baseobjects.MiscMain;
import org.testng.annotations.Test;

public class DelayedActionProcessorTests extends MiscMain
{
    @Test
    public void delayedActionProcessor()
    {
        DelayedActionObject delayedActionObject = ApiHelper.fetchNextDelayedAction(false);
        if (delayedActionObject.getRunId() == 0) {
            Log.story("No delayedActions test rows returned");
        } else {
            Log.object("delayedActionObject", delayedActionObject);
            switch (delayedActionObject.getRunActionMethod()) {
                case "quickTestPart2" -> quickTestPart2(delayedActionObject.getRunSetupData(RunSetupData.class));
                default -> Verify.fail("Unknown runActionMethod: " + delayedActionObject.getRunActionMethod());
            }
        }
    }

    private void quickTestPart2(RunSetupData runSetupData)
    {
        Log.object("runSetupData", runSetupData);
    }
}
