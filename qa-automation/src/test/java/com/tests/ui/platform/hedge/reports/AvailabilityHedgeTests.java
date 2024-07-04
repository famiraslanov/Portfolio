package com.tests.ui.platform.hedge.reports;

import com.tests.enums.platform.AssetClass;
import com.tests.helpers.platform.ReportsAvailabilityHelper;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import org.testng.annotations.Test;

public class AvailabilityHedgeTests extends HedgeMain
{
    @CoverageInfo(details = "Verify reports are shown and selecting the first opens the Article page on Hedge")
    @Test
    public void availabilityHedgeTest()
    {
        ReportsAvailabilityHelper.runner(AssetClass.hedge);
    }
}
