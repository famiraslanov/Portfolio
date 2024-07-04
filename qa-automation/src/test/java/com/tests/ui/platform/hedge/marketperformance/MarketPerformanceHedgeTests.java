package com.tests.ui.platform.hedge.marketperformance;

import com.tests.enums.platform.AssetClass;
import com.tests.helpers.platform.MarketPerformanceHelper;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import org.testng.annotations.Test;

public class MarketPerformanceHedgeTests extends HedgeMain
{
    @CoverageInfo(details = "Verify there are records shown for performance on markets when loaded on Hedge")
    @Test
    public void marketPerformanceHedgeTest()
    {
        MarketPerformanceHelper.runner(AssetClass.hedge);
    }
}
