package com.tests.ui.platform.hedge.misc;

import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import org.testng.annotations.Test;

public class HedgeLoginTests extends HedgeMain
{
    @CoverageInfo(details = "Login and navigate to Hedge")
    @Test
    public void hedgeLoginTest()
    {
        navToStart();
    }
}
