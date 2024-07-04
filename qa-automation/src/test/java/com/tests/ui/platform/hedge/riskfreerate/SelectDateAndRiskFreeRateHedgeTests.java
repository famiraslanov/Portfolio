package com.tests.ui.platform.hedge.riskfreerate;

import com.tests.enums.platform.AssetClass;
import com.tests.helpers.platform.IndicesHelper;
import com.library.annotations.CoverageInfo;
import com.tests.pageobjects.baseobjects.HedgeMain;
import com.tests.pageobjects.platform.IndexPage;
import com.tests.pageobjects.platform.forms.components.MonthlyReturnsIndexTable;
import org.testng.annotations.Test;

import java.util.Map;

public class SelectDateAndRiskFreeRateHedgeTests extends HedgeMain
{
    @CoverageInfo(details = "Verify changing index statistic data on Hedge")
    @Test
    public void selectDateAndRiskFreeRateHedgeTest()
    {
        IndexPage indexPage = IndicesHelper.selectDateAndRiskFreeRate(AssetClass.hedge);

        MonthlyReturnsIndexTable monthlyReturns = new MonthlyReturnsIndexTable();
        String ytd = monthlyReturns.getColumnValues( "YTD").get(0);
        indexPage.verifyOverviewTableValueByKey("Year to date", ytd);

        Map.Entry<String, String> lastYtd = monthlyReturns.getYTDForLastFullYear();
        indexPage.verifyOverviewTableValueByKey(lastYtd.getKey(), lastYtd.getValue());
    }
}
